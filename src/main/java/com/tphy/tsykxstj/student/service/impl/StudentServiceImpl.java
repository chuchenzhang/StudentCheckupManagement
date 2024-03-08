package com.tphy.tsykxstj.student.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dao.mapper.CheckupResultMapper;
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

import java.util.*;
import java.util.regex.Pattern;

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

    @Autowired
    private CheckupResultMapper checkupMapper;

    @Override
    public AppResponse<HashMap<String,Object>> getStudentListByPage(String school,
                                                                    String grade,
                                                                    String classes,
                                                                    String name,
                                                                    String studentid,
                                                                    Integer pageNum,
                                                                    Integer pageSize,
                                                                    Integer[] status) {
        AppResponse<HashMap<String,Object>> res = new AppResponse<>();

        int offset = (pageNum - 1) * pageSize;

        RowBounds rowBounds = new RowBounds(offset,pageSize);
        if(status == null || status.length == 0) {
            // 0-未体检 1-已体检 2-已推送
            status = new Integer[]{-2,-1,0,1,2};
        }
        System.out.println("status = " + Arrays.toString(status));

        List<Student> studentList = studentMapper.getStudentListByPage(school,grade,classes,name,studentid,status,rowBounds);

        Integer total = studentMapper.getTotalCount(school,grade,classes,name,studentid,status);

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
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AppResponse<Integer> saveStudentInfo(Student student) {

        AppResponse<Integer> res = new AppResponse<>();

        if(student.getName().isEmpty()){
            return res.error("请填写姓名");
        }
        // if(student.getStudentid().isEmpty()){
        //     return res.error("请填写学号");
        // }

        Integer rowAffect = 0;
        if(student.getId() == null){
            // 新增
            Integer stuId = studentMapper.save(student);
            System.out.println("stuId = " + student.getId());
            rowAffect = studentMapper.saveStatus(student.getId(),-2);
            if(rowAffect > 0){
                return res.success("保存成功",rowAffect);
            }else{
                log.error("保存失败,数据库回滚");
                return res.error("保存失败",rowAffect);
            }
        }
        // else{
        //     // 更新
        //     rowAffect = studentMapper.update(student);
        //     if(rowAffect > 0){
        //         return res.success("更新成功",rowAffect);
        //     }else{
        //         return res.error("更新失败",rowAffect);
        //     }
        // }
        return res.error("保存失败",0);
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

                student.setSchool(row.get(0).toString());
                student.setGrade(row.get(1).toString());
                student.setClasses(row.get(2).toString());
                student.setName(row.get(3).toString());
                student.setGender(row.get(4).toString());
                student.setAge(row.get(5).toString());
                student.setStudentid(row.get(6).toString());
                //student.setIdCard(row.get(2).toString());
                //student.setPhone(row.get(3).toString());

                //student.setAddress(row.get(6).toString());

                studentList.add(student);
            }
            int rowAffect = 0;
            int i = 0;
            for(Student stu : studentList){
                Integer stuId = studentMapper.save(stu);
                System.out.println("stu.getId() = " + stu.getId());
                // 新增状态
                Integer num = studentMapper.saveStatus(stu.getId(),-2);
                if(num > 0){
                    rowAffect += num;
                }else{
                    log.info("数据导入失败：" + stu.toString());
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
     * 获取打印条形码参数
     * @param ids    ID数组
     * @return      身份证号，id，手机号，姓名
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

    @Override
    public AppResponse<Integer> updateStatusAfterPrint(Integer[] ids) {

        AppResponse<Integer> res = new AppResponse<>();

        if(ids.length == 0){
            return res.error();
        }

        List<Student> studentList = studentMapper.getBarCodeData(ids);

        Integer count = 0;
        for(Student stu : studentList){
            // 未打印状态更新至-1（已打印）
            if(stu.getStatus() == -2){
                Integer rowAffect = checkupMapper.updateStatus(stu.getId(),-1);
                count += rowAffect;
            }
        }
        log.info("共打印"+count+"名学生信息,状态更新成功");
        return res.success("共打印"+count+"名学生信息",count);
    }
}
