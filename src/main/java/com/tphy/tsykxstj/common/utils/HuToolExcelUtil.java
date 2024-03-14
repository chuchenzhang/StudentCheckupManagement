package com.tphy.tsykxstj.common.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.tphy.tsykxstj.common.utils.ExcelAlias;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hsh
 * @DateTime 2023/09/09 14:56
 **/
public class HuToolExcelUtil {

    /**
     * 读取Excel并转化为list
     * 需要指定返回时元素的类
     *
     * @param io     需要读取的Excel 的路径aa
     * @param beanType 指定的类，
     * @return list
     */
    public static <T> List<T> redaExcel(InputStream io, Class<T> beanType) {
        // File file = new File(path);
        ExcelReader reader = ExcelUtil.getReader(io);
        reader.setHeaderAlias(getHeaderAlias(beanType, true));
        List<T> list = reader.readAll(beanType);
        reader.close();
        return list;
    }

    /**
     * 将list写入Excel中
     *
     * @param path 需要写入的Excel 的路径
     * @param list 写入的内容
     * @return     void
     */
    public static <T> void writeExcel(String path, List<T> list, Class<T> beanType) {
        Map<String,String> headerAlias = getHeaderAlias(beanType, false);
        try (OutputStream out = new FileOutputStream(path); ExcelWriter writer = ExcelUtil.getWriter()) {
            writer.setHeaderAlias(headerAlias);
            // 写入并刷盘
            writer.write(list)
                    .flush(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得一个类所有 有别名的 字段 和 别名
     *
     * @param beanType 类
     * @param isRead   每个键值对格式为 ("已有的","想改的")
     *                 true 读取时 每个键值对格式为:(“别名”, “字段名”), 从excel中读取到“已有的(别名)”, 改为“想改的(字段名)”
     *                 false 写入时 每个键值对格式为:(“字段名”, “别名”), 每个字段对应的“已有的(字段名)”, 改为“想改的(别名)”
     * @param <T>      泛型
     * @return         Map<String,String>
     */
    static <T> Map<String, String> getHeaderAlias(Class<T> beanType, boolean isRead) {
        Map<String,String> headerAlias = new HashMap<String, String>();
        // Hutool 获取字段集合的方法，无需try/catch
        List<Field> fields = Arrays.asList(ReflectUtil.getFields(beanType));
        if (fields.isEmpty()) {
            return headerAlias;
        }
        for (Field field : fields) {
            // 获得每个字段的 @ExcelAlias注解
            ExcelAlias anno = field.getAnnotation(ExcelAlias.class);
            if (anno == null || "".equals(anno.value())) {
                continue;
            }
            if (isRead) {
                // 读取时 每个键值对格式为:(“别名”, “字段名”)
                headerAlias.put(anno.value(), field.getName());
            } else {
                // 写入时 每个键值对格式为:(“字段名”, “别名”)
                headerAlias.put(field.getName(), anno.value());
            }
        }
        return headerAlias;
    }

}

