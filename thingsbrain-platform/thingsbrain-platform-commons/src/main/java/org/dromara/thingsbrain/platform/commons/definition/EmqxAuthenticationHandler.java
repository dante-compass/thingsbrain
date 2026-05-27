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
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.platform.commons.definition;

import org.dromara.thingsbrain.platform.commons.domain.EmqxAuthenticationStatus;

/**
 * <p>Description: Emqx 认证逻辑定义 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/27 13:10
 */
public interface EmqxAuthenticationHandler {

    /**
     * 客户端注册密码签名验证
     * <p>
     * 如果返回的 HTTP 状态码为 200，认证结果通过 Body 中的 result 标示，Emqx Http 授权响应支持三种状态，可选值为：
     * · allow：允许发布或订阅
     * · deny：禁止发布或订阅
     * · ignore：忽略请求，移交下一个认证器以继续执行认证链。ignore 为 默认值
     *
     * @param mqttClientId Mqtt ClientId
     * @param mqttUsername Mqtt 用户名
     * @param mqttPassword Mqtt 密码
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    EmqxAuthenticationStatus process(String mqttClientId, String mqttUsername, String mqttPassword);
}
