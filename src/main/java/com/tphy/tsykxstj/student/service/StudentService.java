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

    AppResponse<Integer> importExcel(MultipartFile file);

    AppResponse<HashMap<String,Object>> getStudentListByPage(String name,
                                                             String phone,
                                                             String idCard,
                                                             Integer pageNum,
                                                             Integer pageSize);

    AppResponse<Integer> saveStudentInfo(Student student);

    AppResponse<List<Student>> printBarCode(Integer[] ids);
}
