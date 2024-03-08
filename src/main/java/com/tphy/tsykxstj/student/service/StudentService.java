package com.tphy.tsykxstj.student.service;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: StudentService
 * @ClassDesc: 学生管理服务层
 * @author: 张忠孝
 * @date: 2024/2/27  21:47
 * @version: 1.0
 */
public interface StudentService {

    /**
     * 导入Excel数据
     *
     * @param file excel文件
     * @return      导入的数据总数
     */
    AppResponse<Integer> importExcel(MultipartFile file);


    /**
     * 分页查询学生信息
     * @param school    学校
     * @param grade     年级
     * @param classes   班级
     * @param name      姓名
     * @param studentid 学号
     * @param pageNum   页数
     * @param pageSize  每页条数
     * @param status    状态
     * @return          学生信息
     */
    AppResponse<HashMap<String,Object>> getStudentListByPage(String school,
                                                             String grade,
                                                             String classes,
                                                             String name,
                                                             String studentid,
                                                             Integer pageNum,
                                                             Integer pageSize,
                                                             Integer[] status);

    /**
     * 保存学生信息
     * @param student 学生实体对象
     * @return        保存结果
     */
    AppResponse<Integer> saveStudentInfo(Student student);

    /**
     * 打印条形码
     * @param ids   学生ID数组
     * @return      id,name,idCard,phone
     */
    AppResponse<List<Student>> printBarCode(Integer[] ids);

    /**
     * 打印成功后更新体检状态
     * @param ids id数组
     * @return      更新结果
     */
    AppResponse<Integer> updateStatusAfterPrint(Integer[] ids);
}
