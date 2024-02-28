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
 * @ClassName: CheckupResult
 * @ClassDesc: 体检结果表单实体类
 * @author: 张忠孝
 * @date: 2024/2/28  11:34
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckupResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer id;

    public Integer stuId;

    /**
     * 裸眼视力-左OS
     */
    public float nakedVisionL;

    /**
     * 裸眼视力-右OD
     */
    public float nakedVisionR;

    /**
     * 旧镜
     */
    public String preGlassesDegree;

    /**
     * 病史
     */
    public String medicalHistory;

    /**
     * 电脑验光左OS
     */
    public String computerOptometryL;

    /**
     * 电脑验光右OD
     */
    public String computerOptometryR;

    /**
     * 眼压-左OS
     */
    public String eyePressureL;

    /**
     * 眼压-右OD
     */
    public String eyePressureR;

    /**
     * 眼轴-左OS
     */
    public String ocularAxisL;

    /**
     * 眼轴-右OD
     */
    public String ocularAxisR;

    /**
     * 眼位 1-正位 2-斜
     */
    public Integer ocularPosition;

    /**
     * 结膜是否正常 1-正常 0-不勾选
     */
    public Integer conjunctiva;

    /**
     * 角膜是否正常 1-正常 0-不勾选
     */
    public Integer cornea;

    /**
     * 晶体是否正常 1-正常 0-不勾选
     */
    public Integer lens;

    /**
     * 眼底检查 1-正常 2-异常
     */
    public Integer eyeGround;

    /**
     * 检查结果 1-近视 2-远视 3-弱视 4-斜视 5-其他
     */
    public Integer result;

    /**
     * 医生处理意见
     */
    public String doctorSuggestion;

    public String doctorId;

    public String doctorName;

    public String createTime;

}
