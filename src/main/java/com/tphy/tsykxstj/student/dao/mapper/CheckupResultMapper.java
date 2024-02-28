package com.tphy.tsykxstj.student.dao.mapper;

import com.tphy.tsykxstj.common.utils.AppResponse;
import com.tphy.tsykxstj.student.dto.CheckupResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: CheckupResultMapper
 * @ClassDesc: 检查结果表单Mapper
 * @author: 张忠孝
 * @date: 2024/2/28  15:27
 * @version: 1.0
 */
@Mapper
public interface CheckupResultMapper {

    Integer save(CheckupResult checkupResult);

    Integer update(CheckupResult checkupResult);

    CheckupResult findByStuId(Integer stuId);
}
