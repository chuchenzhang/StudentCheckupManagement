package com.tphy.tsykxstj.common.utils;

import lombok.Data;

/**
 * Copyright (c) 2022 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (c) 2022 TPHY.Co.,Ltd.  All rights reserved
 *
 * @ClassName: MsgModel
 * @ClassDesc: 消息模型
 * @author: 张忠孝
 * @date: 2024/2/29  15:04
 * @version: 1.0
 */
@Data
public class MsgModel {

    public String BizId;

    public String Code;

    public String Message;

    public String RequestId;
}
