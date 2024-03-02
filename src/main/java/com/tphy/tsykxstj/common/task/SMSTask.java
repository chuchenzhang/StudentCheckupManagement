package com.tphy.tsykxstj.common.task;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.common.utils.SMSAPI;
import com.tphy.tsykxstj.student.dao.mapper.CheckupResultMapper;
import com.tphy.tsykxstj.student.dao.mapper.StudentMapper;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import com.tphy.tsykxstj.student.dto.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: SMSTask
 * @ClassDesc: 推送定时任务
 * @author: 张忠孝
 * @date: 2024/2/29  14:52
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableScheduling
public class SMSTask {

    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CheckupResultMapper checkupMapper;

    // @Scheduled(cron = "0/30 * * * * ?")//每秒钟执行一次，以空格分隔
    @Scheduled(cron="0 44 13 * * *")//每天11:25:00执行一次，以空格分隔
    public void cron() {

        AppResponse<String> res = new AppResponse<>();

        LocalDateTime now = LocalDateTime.now();

        List<CheckupResult> studentList = getPushData();

        for(CheckupResult stu : studentList){
            String phone = stu.getStudent().getPhone();
            String name = stu.getStudent().getName();
            String lxmc = stu.getCreateTime();
            String lysl = "裸眼视力左OS：" + stu.getNakedVisionL() + "," +
                    "裸眼视力右OD：" + stu.getNakedVisionR() + ",";
            // String qgd = "球镜左OS：" + stu.getCoSphereL() + "DS," +
            //         "球镜右OD：" + stu.getCoSphereR() + "DS," +
            //         "柱镜左OS：" + stu.getCoCylinderL() + "DC," +
            //         "柱镜右OD：" + stu.getCoCylinderR() + "DC," +
            //         "轴位左OS：" + stu.getCoAxisPositionL() + "DA," +
            //         "轴位右OD：" + stu.getCoAxisPositionR() + "DA," +
            //         "瞳距左OS：" + stu.getCoPdL() + "," +
            //         "瞳距右OD：" + stu.getCoPdR();
            String qgd = "球镜左OS：" + stu.getCoSphereL() + "DS,";
            System.out.println("qgd = " + qgd);


            String verifyCodeJsonFormat = "{\"name\":\""+name+"\",\"lxmc\":\""+lxmc+"\",\"lysl\":\""+lysl+"\",\"qgd\":\""+qgd+"\"}";
            String templateCode = "SMS_465315828";
            // 发送短信
            res = SMSAPI.SendMessage(templateCode,phone,verifyCodeJsonFormat);
            System.out.println("res = " + res);
            if(res.getCode() == 200){
                Integer affect = checkupMapper.updateStatus(stu.getStudent().getId(),2);
                if(affect > 0){
                    log.info("用户ID：" + stu.getStudent().getId() + "短信发送成功,状态更新成功");
                }else{
                    log.error("用户ID：" + stu.getStudent().getId() + "短信发送成功,状态更新失败");
                }
            }
        }

        System.out.println("spring task 这是定时任务，时间是：" + PATTERN.format(now));
    }

    public List<CheckupResult> getPushData(){

        List<CheckupResult> studentList = checkupMapper.getPushData();

        System.out.println("studentList = " + studentList);

        return studentList;
    }
}
