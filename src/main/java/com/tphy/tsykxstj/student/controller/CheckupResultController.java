package com.tphy.tsykxstj.student.controller;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/save")
    public AppResponse<Integer> save(CheckupResult checkupResult){

        return null;
    }

}
