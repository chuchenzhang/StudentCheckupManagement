package com.tphy.tsykxstj.student.dto;

import com.tphy.tsykxstj.common.utils.ExcelAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: Template
 * @ClassDesc: TODO
 * @author: 张忠孝
 * @date: 2024/3/12  13:15
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Template implements Serializable {

    public static final long serialVersionUID = 1L;

    @ExcelAlias(value = "学校")
    public String school;

    @ExcelAlias(value = "年级")
    public String grade;

    @ExcelAlias(value = "班级")
    public String classes;

    @ExcelAlias(value = "姓名")
    public String name;

    @ExcelAlias(value = "性别")
    public String gender;

    @ExcelAlias(value = "年龄")
    public String age;

    @ExcelAlias(value = "学号")
    public String studentid;

    @ExcelAlias(value = "裸眼右")
    public Float nakedVisionR;

    @ExcelAlias(value = "裸眼左")
    public Float nakedVisionL;

    @ExcelAlias(value = "右球镜")
    public Float coSphereR;

    @ExcelAlias(value = "右柱镜")
    public Float coCylinderR;

    @ExcelAlias(value = "右轴位")
    public Float coAxisPositionR;

    @ExcelAlias(value = "左球镜")
    public Float coSphereL;

    @ExcelAlias(value = "左柱镜")
    public Float coCylinderL;

    @ExcelAlias(value = "左轴位")
    public Float coAxisPositionL;
}
