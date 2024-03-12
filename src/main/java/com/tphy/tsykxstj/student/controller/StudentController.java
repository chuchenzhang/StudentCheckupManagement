package com.tphy.tsykxstj.student.controller;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.Student;
import com.tphy.tsykxstj.student.service.impl.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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

    private final ResourceLoader resourceLoader;

    @Autowired
    public StudentController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取学生信息列表
     * @return AppResponse<List<Student>>
     */
    @GetMapping("/list")
    public AppResponse<HashMap<String,Object>> getStudentListByPage(@RequestParam(value = "name",required = false) String name,
                                                                    @RequestParam(value = "studentid", required = false) String studentid,
                                                                    @RequestParam(value = "school",required = false) String school,
                                                                    @RequestParam(value = "classes",required = false) String classes,
                                                                    @RequestParam(value = "grade",required = false) String grade,
                                                                    @RequestParam(value = "status[]",required = false) Integer[] status,
                                                                    @RequestParam("pageNum") Integer pageNum,
                                                                    @RequestParam("pageSize") Integer pageSize){

        System.out.println("status = " + Arrays.toString(status));

        return studentService.getStudentListByPage(school,grade,classes,name,studentid,pageNum,pageSize,status);
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
    public AppResponse<List<Student>> printBarCode(@RequestParam("ids[]") Integer[] ids){

        System.out.println("id = " + Arrays.toString(ids));

        return studentService.printBarCode(ids);
    }

    /**
     * 打印条形码成功后更新体检状态
     * @param ids id数组
     * @return
     */
    @PostMapping("/updateStatusAfterPrint")
    public AppResponse<Integer> updateStatusAfterPrint(@RequestParam("ids[]") Integer[] ids){

        return studentService.updateStatusAfterPrint(ids);
    }

    @RequestMapping("/download")
    public ResponseEntity<FileSystemResource> downloadTempldate(){

        ResponseEntity<FileSystemResource> res = downloadFile();

        if(res == null){
            log.error("模板文件下载失败");
            return null;
        }
        log.info("模板文件下载成功");
        return res;
    }

    public ResponseEntity<FileSystemResource> downloadFile(){

        try{
            String projectPath = System.getProperty("user.dir");

            String filePath = projectPath + "/template/唐山市眼科医院学生体检信息模板.xlsx";

            // 创建文件资源
            FileSystemResource fileResource = new FileSystemResource(new File(filePath));
            System.out.println("fileResource = " + fileResource);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "唐山市眼科医院学生体检信息模板.xlsx");

            // 返回响应实体
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(fileResource);

            // // 使用ResourceLoader加载资源
            // Resource resource = resourceLoader.getResource("classpath:" + filePath);
            //
            // if(resource.exists()){
            //     // 设置下载响应头
            //     HttpHeaders headers = new HttpHeaders();
            //     String fileName = "唐山市眼科医院学生体检信息模板.xlsx";
            //     headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            //     headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //
            //     // 返回文件响应
            //     return ResponseEntity.ok()
            //             .headers(headers)
            //             .body(resource);
            // }
        }catch (Exception e){
            log.error("模板文件下载失败");
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getSelectData")
    public AppResponse<HashMap<String,List<String>>> getSchoolGradeClass(@RequestParam(value = "school", required = false) String school,
                                                                         @RequestParam(value = "grade", required = false) String grade){

        return studentService.getSchoolGradeClass(school,grade);
    }

}
