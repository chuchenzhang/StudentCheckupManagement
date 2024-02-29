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
