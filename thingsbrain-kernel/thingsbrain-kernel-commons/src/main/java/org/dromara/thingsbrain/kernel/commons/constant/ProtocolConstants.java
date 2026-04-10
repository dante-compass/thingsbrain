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

import org.dromara.dante.core.constant.BaseConstants;
import org.dromara.dante.core.constant.SymbolConstants;

/**
 * <p>Description: Herodotus Link 通用基础常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/29 23:05
 */
public interface ProtocolConstants extends BaseConstants {

    String TOPIC_WILDCARDS_SINGLE = "/+";

    String PREFIX__EVENT = "thing.event";
    String PREFIX__SERVICE = "thing.service";

    String VARIABLE__IDENTIFIER = "identifier";
    String VARIABLE__PROPERTY = "property";
    String VARIABLE__EVENT_IDENTIFIER = "tsl.event.identifier";
    String VARIABLE__SERVICE_IDENTIFIER = "tsl.service.identifier";

    String ACTION__POST = "post";
    String ACTION__SET = "set";
    String ACTION__GET = "get";
    String ACTION__REPLY = "_reply";

    String PARAMETER__VALUE = "value";

    String PARAMETER__REPORTED = "reported";
    String PARAMETER__DESIRED = "desired";

    /**
     * 格式：${productKey}
     */
    String FORMAT_PLACEHOLDER__PRODUCT_KEY = PLACEHOLDER_PREFIX + KernelConstants.KEY__PRODUCT_KEY + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${deviceName}
     */
    String FORMAT_PLACEHOLDER__DEVICE_NAME = PLACEHOLDER_PREFIX + KernelConstants.KEY__DEVICE_NAME + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${tsl.event.identifier}
     */
    String FORMAT_PLACEHOLDER__IDENTIFIER_EVENT = PLACEHOLDER_PREFIX + VARIABLE__EVENT_IDENTIFIER + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${tsl.service.identifier}
     */
    String FORMAT_PLACEHOLDER__IDENTIFIER_SERVICE = PLACEHOLDER_PREFIX + VARIABLE__SERVICE_IDENTIFIER + PLACEHOLDER_SUFFIX;
    /**
     * 格式：/${productKey}/${deviceName}
     */
    String FORMAT_LEVEL__COMMON = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__PRODUCT_KEY + SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__DEVICE_NAME;
    /**
     * 格式：/${tsl.event.identifier}
     */
    String FORMAT_LEVEL__EVENT = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__IDENTIFIER_EVENT;
    /**
     * 格式：/${tsl.service.identifier}
     */
    String FORMAT_LEVEL__SERVICE = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__IDENTIFIER_SERVICE;
    /**
     * 格式：/post
     */
    String FORMAT_LEVEL__POST = SymbolConstants.SLASH + ProtocolConstants.ACTION__POST;
}
