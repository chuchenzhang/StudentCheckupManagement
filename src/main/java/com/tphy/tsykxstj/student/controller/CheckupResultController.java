package com.tphy.tsykxstj.student.controller;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import com.tphy.tsykxstj.student.service.impl.CheckupResultServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: CheckupResultController
 * @ClassDesc: 体检结果表单
 * @author: 张忠孝
 * @date: 2024/2/28  11:32
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkup")
public class CheckupResultController {

    @Autowired
    private CheckupResultServiceImpl checkupResultService;

    /**
     * // TODO 参数选填 、 左OS -> 左眼
     * // TODO 短信屈光度 球镜左右 柱镜左右
     * // TODO 检查填写表: 体检结果仅供参考,不作诊断依据 在短信模板加到最后一行
     * // TODO 去掉地址
     * // TODO 添加学校班级字典
     * // TODO 除了姓名,学生和检查结果信息都可以为空
     * @param checkupResult
     * @return
     */
    @PostMapping("/save")
    public AppResponse<Integer> save(@RequestBody CheckupResult checkupResult){

        System.out.println("checkupResult = " + checkupResult);

        return checkupResultService.save(checkupResult);
    }

    @GetMapping("/findByStuId")
    public AppResponse<CheckupResult> findByStuId(@RequestParam Integer stuId){

        return checkupResultService.findByStuId(stuId);
    }

}
