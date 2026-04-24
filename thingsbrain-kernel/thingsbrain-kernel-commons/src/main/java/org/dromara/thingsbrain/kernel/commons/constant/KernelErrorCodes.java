/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package org.dromara.thingsbrain.kernel.commons.constant;

import org.dromara.dante.core.feedback.InternalServerErrorFeedback;
import org.dromara.dante.core.feedback.PreconditionFailedFeedback;

/**
 * <p>Description: 系统核心错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/23 23:53
 */
public interface KernelErrorCodes {

    PreconditionFailedFeedback JSON_SCHEMA_VALIDATE_ERROR_EXCEPTION = new PreconditionFailedFeedback("JsonSchema 校验输入参数错误");
    InternalServerErrorFeedback INBOUND_MESSAGE_PROCESSING_EXCEPTION = new InternalServerErrorFeedback("Mqtt 入站消息时序存储异常，无法找到指定处理器");
}
