package com.tphy.tsykxstj.student.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dao.mapper.StudentMapper;
import com.tphy.tsykxstj.student.dto.Student;
import com.tphy.tsykxstj.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: StudentServiceImpl
 * @ClassDesc: 学生管理服务层实现
 * @author: 张忠孝
 * @date: 2024/2/27  21:48
 * @version: 1.0
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public AppResponse<HashMap<String,Object>> getStudentListByPage(String name,
                                                           String phone,
                                                           String idCard,
                                                           Integer pageNum,
                                                           Integer pageSize) {
        AppResponse<HashMap<String,Object>> res = new AppResponse<>();

        int offset = (pageNum - 1) * pageSize;

        RowBounds rowBounds = new RowBounds(offset,pageSize);

        List<Student> studentList = studentMapper.getStudentListByPage(name,phone,idCard,rowBounds);

        Integer total = studentMapper.getTotalCount();

        HashMap<String,Object> data = new HashMap<>();

        data.put("pageNum",pageNum);
        data.put("pageSize",pageSize);
        data.put("total",total);
        data.put("data",studentList);

        if(studentList.isEmpty()){
            return res.empty(data);
        }

        return res.success("请求成功",data);
    }

    /**
     * 单个录入学生信息
     * @param student 学生实体类
     * @return        保存结果 1-成功 0-失败
     */
    @Override
    public AppResponse<Integer> saveStudentInfo(Student student) {

        AppResponse<Integer> res = new AppResponse<>();

        if(student.getName().isEmpty()){
            return res.error("请填写姓名");
        }
        if(student.getIdCard().isEmpty()){
            return res.error("请填写身份证号");
        }
        if(student.getPhone().isEmpty()){
            return res.error("请填写手机号");
        }
        if(student.getSchool().isEmpty()){
            return res.error("请填写学校");
        }
        if(student.getClasses().isEmpty()){
            return res .error("请填写班级");
        }

        Integer rowAffect = 0;
        if(student.getId() == null){
            // 新增
            rowAffect = studentMapper.save(student);
            if(rowAffect > 0){
                return res.success("保存成功",rowAffect);
            }else{
                return res.error("保存失败",rowAffect);
            }
        }else{
            // 更新
            rowAffect = studentMapper.update(student);
            if(rowAffect > 0){
                return res.success("更新成功",rowAffect);
            }else{
                return res.error("更新失败",rowAffect);
            }
        }

    }

    /**
     * 批量导入学生数据
     * @param file excel文件
     * @return      导入的数据总数
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AppResponse<Integer> importExcel(MultipartFile file) {

        AppResponse<Integer> res = new AppResponse<>();

        if(!file.isEmpty()){
            int beginInx = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
            int endInx = file.getOriginalFilename().length();
            String suffix = file.getOriginalFilename().substring(beginInx,endInx);
            System.out.println("suffix = " + suffix);
            if(!(suffix.endsWith(".xlsx") || suffix.endsWith(".xls"))){
                return new AppResponse<Integer>().error("文件类型不正确");
            }
        }else{
            return new AppResponse<Integer>().error("文件不能为空");
        }

        try{

            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            // 忽略第0行表头，从第1行开始读数据
            List<List<Object>> list =reader.read(1);

            List<Student> studentList = new ArrayList<>();
            // 准备数据
            for(List<Object> row : list){
                Student student = new Student();

                student.setName(row.get(0).toString());
                student.setGender(row.get(1).toString());
                student.setIdCard(row.get(2).toString());
                student.setPhone(row.get(3).toString());
                student.setSchool(row.get(4).toString());
                student.setClasses(row.get(5).toString());
                student.setAddress(row.get(6).toString());

                studentList.add(student);
            }
            int rowAffect = 0;
            int i = 0;
            for(Student stu : studentList){
                Integer num = studentMapper.save(stu);
                if(num > 0){
                    rowAffect += num;
                }else{
                    log.info(stu.toString());
                    throw new Exception("第" + i + "条数据导入失败");
                }
                i ++;
            }

            if(rowAffect > 0){
                return res.success("成功导入" + rowAffect + "条数据",rowAffect);
            }else{
                return res.error("数据导入失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return res.error("数据导入失败");
    }

    /**
     * 打印条形码
     * @param ids    ID数组
     * @return      身份证号，学号，手机号，姓名
     */
    @Override
    public AppResponse<List<Student>> printBarCode(Integer[] ids) {

        AppResponse<List<Student>> res = new AppResponse<>();

        List<Student> studentList = studentMapper.getBarCodeData(ids);

        if(studentList.isEmpty()){
            return res.empty(studentList);
        }

        return res.success(studentList);
    }
}
