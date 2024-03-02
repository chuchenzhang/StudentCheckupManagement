package com.tphy.tsykxstj.student.controller;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.Student;
import com.tphy.tsykxstj.student.service.impl.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: StudentController
 * @ClassDesc: 学生信息录入、查询
 * @author: 张忠孝
 * @date: 2024/2/27  20:26
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    /**
     * 获取学生信息列表
     * @return AppResponse<List<Student>>
     */
    @GetMapping("/list")
    public AppResponse<HashMap<String,Object>> getStudentListByPage(@RequestParam(value = "name",required = false) String name,
                                                                    @RequestParam(value = "phone",required = false) String phone,
                                                                    @RequestParam(value = "idCard",required = false) String idCard,
                                                                    @RequestParam(value = "status[]",required = false) Integer[] status,
                                                                    @RequestParam("pageNum") Integer pageNum,
                                                                    @RequestParam("pageSize") Integer pageSize){

        System.out.println("status = " + Arrays.toString(status));

        return studentService.getStudentListByPage(name,phone,idCard,pageNum,pageSize,status);
    }

    @PostMapping("/saveStudentInfo")
    public AppResponse<Integer> saveStudentInfo(@RequestBody Student student){

        return studentService.saveStudentInfo(student);
    }

    /**
     * 批量导入excel数据
     *
     * @param file excel文件
     * @return AppResponse<Integer>
     */
    @PostMapping("/import")
    public AppResponse<Integer> importExcel(MultipartFile file){

        return studentService.importExcel(file);
    }

    /**
     * 打印条形码
     */
    @PostMapping("/printBarCode")
    public AppResponse<List<Student>> printBarCode(@RequestParam("id") Integer[] ids){

        System.out.println("id = " + Arrays.toString(ids));

        return studentService.printBarCode(ids);
    }

}
