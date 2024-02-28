package com.tphy.tsykxstj.student.service;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.CheckupResult;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: CheckupResultService
 * @ClassDesc: 检查结果表单服务层
 * @author: 张忠孝
 * @date: 2024/2/28  15:12
 * @version: 1.0
 */
public interface CheckupResultService {

    AppResponse<Integer> save(CheckupResult checkupResult);

    AppResponse<CheckupResult> findByStuId(Integer stuId);
}
