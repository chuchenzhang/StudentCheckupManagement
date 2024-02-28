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

    Integer save(Student student);

    List<Student> getStudentListByPage(@Param("name") String name,
                                       @Param("phone") String phone,
                                       @Param("idCard") String idCard,
                                       RowBounds rowBounds);

    Integer update(Student student);

    Integer getTotalCount();

    List<Student> getBarCodeData(@Param("ids") Integer[] ids);
}
