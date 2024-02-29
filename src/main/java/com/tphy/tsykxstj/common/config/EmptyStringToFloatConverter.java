package com.tphy.tsykxstj.common.config;

import org.springframework.core.convert.converter.Converter;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: EmptyStringToFloatConverter
 * @ClassDesc: 解决空字符串转float类型报错
 * @author: 张忠孝
 * @date: 2024/2/29  13:24
 * @version: 1.0
 */
public class EmptyStringToFloatConverter implements Converter<String,Float> {
    @Override
    public Float convert(String source) {
        if (source == null || source.isEmpty()) {
            // 或者返回适当的默认值
            return null;
        }
        return Float.parseFloat(source);
    }
}
