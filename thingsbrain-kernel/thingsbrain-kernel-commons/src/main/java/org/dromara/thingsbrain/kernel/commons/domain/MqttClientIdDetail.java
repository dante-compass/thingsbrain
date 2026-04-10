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

package org.dromara.thingsbrain.kernel.commons.domain;

import org.dromara.dante.core.domain.BaseEntity;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: Mqtt 客户端 ID 详情 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/29 13:19
 */
public class MqttClientIdDetail implements BaseEntity {

    /**
     * 物联网设备 ClientId
     */
    private final String clientId;
    /**
     * 物联网客户端ID扩展参数字符串
     */
    private final String parameters;

    /**
     * 是否 MqttClientId 中包含参数
     */
    private final Boolean hasParameters;

    public MqttClientIdDetail(String clientId) {
        this(clientId, null);
    }

    public MqttClientIdDetail(String clientId, String parameters) {
        this.clientId = clientId;
        this.parameters = parameters;
        this.hasParameters = StringUtils.isNotBlank(this.parameters);
    }

    public String getClientId() {
        return clientId;
    }

    public String getParameters() {
        return parameters;
    }

    public Boolean getHasParameters() {
        return hasParameters;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("parameters", parameters)
                .add("hasParameters", hasParameters)
                .toString();
    }
}
