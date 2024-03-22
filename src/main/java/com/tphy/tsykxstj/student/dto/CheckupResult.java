package com.tphy.tsykxstj.student.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message="裸眼视力左OS不能为空")
    public Float nakedVisionL;

    /**
     * 裸眼视力-右OD
     */
    public Float nakedVisionR;

    /**
     * 旧镜
     */
    public Float preGlassesDegree;

    /**
     * 病史
     */
    public String medicalHistory;

    /**
     * 电脑验光-球镜-左OS
     */
    public Float coSphereL;

    /**
     * 电脑验光-球镜-右OD
     */
    public Float coSphereR;

    /**
     * 电脑验光-柱镜-左OS
     */
    public Float coCylinderL;

    /**
     * 电脑验光-柱镜-右OD
     */
    public Float coCylinderR;

    /**
     * 电脑验光-轴位-左OS
     */
    public Integer coAxisPositionL;

    /**
     * 电脑验光-轴位-右OD
     */
    public Integer coAxisPositionR;

    /**
     * 电脑验光-瞳距-左OS
     */
    public Float coPdL;

    /**
     * 电脑验光-瞳距-右OD
     */
    public Float coPdR;

    /**
     * 眼压-左OS
     */
    public Float eyePressureL;

    /**
     * 眼压-右OD
     */
    public Float eyePressureR;

    /**
     * 眼轴-左OS
     */
    public Float ocularAxisL;

    /**
     * 眼轴-右OD
     */
    public Float ocularAxisR;

    /**
     * 眼位 1-正位 2-斜
     */
    public Integer ocularPosition;

    /**
     * 远视储备
     */
    public String hyperopiaReserve;

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

    public Student student;

    public boolean empty(){
        return  nakedVisionL == null &&
                nakedVisionR == null &&
                coSphereL == null &&
                coSphereR == null &&
                coCylinderL == null &&
                coCylinderR == null &&
                coAxisPositionL == null
                && coAxisPositionR == null;
    }

}
