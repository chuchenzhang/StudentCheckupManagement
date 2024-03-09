package com.tphy.tsykxstj.student.dao.mapper;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: StudentMapper
 * @ClassDesc:
 * @author: 张忠孝
 * @date: 2024/2/27  22:11
 * @version: 1.0
 */
@Mapper
public interface StudentMapper {

    /**
     * 保存学生信息
     * @param student   学生实体类
     * @return          新增结果
     */
    Integer save(Student student);

    /**
     * 分页查询
     */
    List<Student> getStudentListByPage(@Param("school") String school,
                                       @Param("grade") String grade,
                                       @Param("classes") String classes,
                                       @Param("name") String name,
                                       @Param("studentid") String studentid,
                                       @Param("status") Integer[] status,
                                       RowBounds rowBounds);

    Integer update(Student student);

    Integer getTotalCount(@Param("school") String school,
                          @Param("grade") String grade,
                          @Param("classes") String classes,
                          @Param("name") String name,
                          @Param("studentid") String studentid,
                          @Param("status") Integer[] status);

    List<Student> getBarCodeData(@Param("ids") Integer[] ids);

    /**
     * 新增学生信息同时新增状态关联表
     * @param stuId     主键ID
     * @param status    状态
     * @return          新增结果
     */
    Integer saveStatus(@Param("stuId") Integer stuId,@Param("status") Integer status);

    int isRepeatIdCard(@Param("idCard") String idCard);

    List<String> getSchool();

    List<String> getGrade(String school);

    List<String> getClasses(String grade);
}
