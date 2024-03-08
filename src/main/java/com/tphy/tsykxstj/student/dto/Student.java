package com.tphy.tsykxstj.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: Student
 * @ClassDesc: student实体类
 * @author: 张忠孝
 * @date: 2024/2/27  21:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer id;

    public String name;

    public String gender;

    public String idCard;

    public String phone;

    public String school;

    public String grade;

    public String classes;

    public String address;

    public String age;

    public String studentid;

    public String birth;

    public Integer status;

    public String createTime;
}
