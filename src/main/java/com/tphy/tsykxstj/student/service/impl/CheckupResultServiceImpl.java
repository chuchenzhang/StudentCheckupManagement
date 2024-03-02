package com.tphy.tsykxstj.student.service.impl;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dao.mapper.CheckupResultMapper;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import com.tphy.tsykxstj.student.service.CheckupResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: CheckupResultServiceImpl
 * @ClassDesc: TODO
 * @author: 张忠孝
 * @date: 2024/2/28  15:26
 * @version: 1.0
 */
@Service
public class CheckupResultServiceImpl implements CheckupResultService {

    @Autowired
    private CheckupResultMapper checkupMapper;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AppResponse<Integer> save(CheckupResult checkupResult) {

        AppResponse<Integer> res = new AppResponse<>();

        if(checkupResult.getStuId() == null){
            return res.error("学生ID为空，请选择学生",0);
        }
        Integer rowAffect = 0;
        if(checkupResult.getId() == null){
            rowAffect = checkupMapper.save(checkupResult);
            System.out.println("checkupResult.getId() = " + checkupResult.getId());
            System.out.println("checkupResult.getStuId() = " + checkupResult.getStuId());
            Integer num = checkupMapper.updateStatus(checkupResult.getStuId(),1);
            if(rowAffect > 0 && num > 0){
                return res.success("保存成功",checkupResult.getId());
            }else{
                return res.error("保存失败",rowAffect);
            }
        }else{
            rowAffect = checkupMapper.update(checkupResult);
            if(rowAffect > 0){
                return res.success("更新成功",checkupResult.getId());
            }else{
                return res.error("更新失败",checkupResult.getId());
            }
        }
    }

    @Override
    public AppResponse<CheckupResult> findByStuId(Integer stuId) {

        AppResponse<CheckupResult> res = new AppResponse<>();

        CheckupResult data = checkupMapper.findByStuId(stuId);

        if(ObjectUtils.isEmpty(data)){
            return res.empty(data);
        }

        String idCard = data.getStudent().getIdCard();

        data.getStudent().setBirth(getBirthdayFromIdCard(idCard));

        return res.success("查询成功",data);
    }

    public static String getBirthdayFromIdCard(String idCardNumber) {
        String birthday = null;
        if (idCardNumber.length() == 18) {
            // 从18位身份证号码中获取出生日期
            String birthdayPart = idCardNumber.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = sdf.parse(birthdayPart);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                birthday = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (idCardNumber.length() == 15) {
            // 从15位身份证号码中获取出生日期
            String birthdayPart = idCardNumber.substring(6, 12);
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            try {
                Date date = sdf.parse(birthdayPart);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                birthday = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return birthday;
    }
}
