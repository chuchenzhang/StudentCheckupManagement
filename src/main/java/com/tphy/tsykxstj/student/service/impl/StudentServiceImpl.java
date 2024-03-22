package com.tphy.tsykxstj.student.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.common.utils.HuToolExcelUtil;
import com.tphy.tsykxstj.student.dao.mapper.CheckupResultMapper;
import com.tphy.tsykxstj.student.dao.mapper.StudentMapper;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import com.tphy.tsykxstj.student.dto.Student;
import com.tphy.tsykxstj.student.dto.Template;
import com.tphy.tsykxstj.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DecimalFormat;
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

            // 读取excel数据
            List<Template> templateList = HuToolExcelUtil.redaExcel(file.getInputStream(),Template.class);
            System.out.println("templateList = " + templateList);
            // 创建DecimalFormat对象，保留两位小数
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            int rowAffect = 0;
            int checkupNumber = 0;
            int updateResCount = 0;
            int i = 0;
            // 插入学生信息
            for(Template row : templateList){
                // System.out.println("decimalFormat.format(row.getNakeVisionR()) = " + decimalFormat.format(row.getNakedVisionR()));
                // 准备体检结果信息
                CheckupResult checkup = new CheckupResult();
                // 裸眼右 保留两位小数
                if (row.getNakedVisionR() == null) {
                    // checkup.setNakedVisionR(row.getNakedVisionR());
                } else {
                    checkup.setNakedVisionR(Float.parseFloat(decimalFormat.format(row.getNakedVisionR())));
                }
                // 裸眼左 保留两位小数
                if(row.getNakedVisionL() == null){
                    // checkup.setNakedVisionL(row.getNakedVisionL());
                }else{
                    checkup.setNakedVisionL(Float.parseFloat(decimalFormat.format(row.getNakedVisionL())));
                }
                // 右球镜 保留两位小数
                if(row.getCoSphereR() == null){
                    // checkup.setCoSphereR(row.getCoSphereR());
                }else{
                    checkup.setCoSphereR(Float.parseFloat(decimalFormat.format(row.getCoSphereR())));
                }
                // 右柱镜 保留两位小数
                if(row.getCoCylinderR() == null){
                    // checkup.setCoCylinderR(row.getCoCylinderR());
                }else{
                    checkup.setCoCylinderR(Float.parseFloat(decimalFormat.format(row.getCoCylinderR())));
                }
                // 右轴位 保留至整数
                if(row.getCoAxisPositionR() == null){

                }else{
                    checkup.setCoAxisPositionR(row.getCoAxisPositionR());
                }
                // 左球镜 保留两位小数
                if(row.getCoSphereL() == null){
                    // checkup.setCoSphereL(row.getCoSphereL());
                }else{
                    checkup.setCoSphereL(Float.parseFloat(decimalFormat.format(row.getCoSphereL())));
                }
                // 左柱镜 保留两位小数
                if(row.getCoCylinderL() == null){
                    // checkup.setCoCylinderL(row.getCoCylinderL());
                }else{
                    checkup.setCoCylinderL(Float.parseFloat(decimalFormat.format(row.getCoCylinderL())));
                }
                // 左轴位 保留至整数
                if(row.getCoAxisPositionL() == null){

                }else{
                    checkup.setCoAxisPositionL(row.getCoAxisPositionL());
                }

                // 准备学生信息
                Student stu = new Student();
                stu.setSchool(row.getSchool());
                stu.setGrade(row.getGrade());
                stu.setClasses(row.getClasses());
                stu.setName(row.getName());
                stu.setGender(row.getGender());
                stu.setAge(row.getAge());
                stu.setStudentid(row.getStudentid());

                // 查询数据库是否有该学生信息
                Integer isRepeatStu = studentMapper.isRepeat(stu);
                // 新增学生信息
                if(isRepeatStu == null) {
                    Integer stuId = studentMapper.save(stu);
                    // 新增状态
                    Integer stuNum = studentMapper.saveStatus(stu.getId(),-2);
                    if(stuId > 0 && stuNum > 0){
                        rowAffect += stuNum;
                    }else{
                        log.info("学生信息录入失败：" + stu.toString());
                        throw new Exception("第" + i + "条学生数据导入失败");
                    }
                    // 体检结果插入学生ID
                    if(!ObjectUtils.isEmpty(checkup)){
                        checkup.setStuId(stu.getId());
                    }
                }else{
                    // 体检结果插入学生ID
                    if(!ObjectUtils.isEmpty(checkup)) {
                        checkup.setStuId(isRepeatStu);
                    }
                }

                // 查询该学生是否录入体检结果
                Integer stuId = isRepeatStu == null ? 0 : isRepeatStu;
                System.out.println("stuId = " + stuId);
                CheckupResult isRepeatCheckup = checkupMapper.findByStuId(stuId);
                System.out.println("isRepeatCheckup = " + isRepeatCheckup);
                Integer rowCount = 0;
                Integer upCount = 0;
                if(isRepeatCheckup != null && !ObjectUtils.isEmpty(checkup)){
                    // 若体检结果存在，则更新体检结果
                    log.info("更新体检结果");
                    checkup.setId(isRepeatCheckup.getId());
                    upCount = checkupMapper.update(checkup);
                }else{
                    // 新增体检结果
                    System.out.println("checkup = " + checkup);
                    log.info("新增体检结果");
                    if(!checkup.empty()){
                        rowCount = checkupMapper.save(checkup);
                        // 更新状态
                        Integer num = checkupMapper.updateStatus(checkup.getStuId(),1);
                    }
                }

                checkupNumber += rowCount;
                updateResCount += upCount;
            }
            String message = "";
            message += "成功导入" + rowAffect + "条学生数据，" + checkupNumber + "条体检信息，更新" + updateResCount + "条体检信息";
            return res.success(message);

        }catch (Exception e){
            log.info("体检数据导入失败");
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

    @Override
    public AppResponse<HashMap<String,List<String>>> getSchoolGradeClass(String school,String grade) {
        AppResponse<HashMap<String,List<String>>> res = new AppResponse<>();

        HashMap<String,List<String>> data = new HashMap<>();

        List<String> schoolList = studentMapper.getSchool();
        data.put("school",schoolList);

        if(!"".equals(school)){
            List<String> gradeList = studentMapper.getGrade(school);
            data.put("grade",gradeList);
        }
        if(!"".equals(grade)){
            List<String> classList = studentMapper.getClasses(grade);
            data.put("classList",classList);
        }


        return res.success("请求成功",data);
    }
}
