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

package org.dromara.thingsbrain.platform.authentication.mqtt;

import org.dromara.dante.core.utils.SignatureUtils;
import org.dromara.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.platform.authentication.utils.MqttSignatureContentUtils;
import org.dromara.thingsbrain.platform.commons.definition.MqttSignatureGenerator;
import org.dromara.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import org.dromara.thingsbrain.platform.commons.domain.SignatureGenerationResult;

import java.util.Map;

/**
 * <p>Description: Mqtt 签名内容生成器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/27 21:54
 */
public class DefaultMqttSignatureGenerator implements MqttSignatureGenerator {

    @Override
    public SignatureGenerationResult process(String productKey, String deviceName, String key, MqttClientIdFactory factory) {
        Map<String, String> contents = MqttSignatureContentUtils.content(productKey, deviceName, factory);

        SignatureGenerationResult result = new SignatureGenerationResult();
        result.setMqttClientId(factory.getClientId());
        result.setMqttUsername(DataFormatUtils.toMqttUsername(productKey, deviceName));
        result.setMqttPassword(SignatureUtils.generate(key, factory.getSignMethod(), contents));

        return result;
    }
}
