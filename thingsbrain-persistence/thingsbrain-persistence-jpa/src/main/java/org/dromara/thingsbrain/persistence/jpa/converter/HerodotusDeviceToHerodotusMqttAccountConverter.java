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

package org.dromara.thingsbrain.persistence.jpa.converter;

import org.dromara.dante.security.utils.SecurityUtils;
import org.dromara.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDevice;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttAccount;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: {@link HerodotusDevice} 转 {@link HerodotusMqttAccount} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/8 16:09
 */
public class HerodotusDeviceToHerodotusMqttAccountConverter implements Converter<HerodotusDevice, HerodotusMqttAccount> {

    @Override
    public HerodotusMqttAccount convert(HerodotusDevice source) {

        HerodotusMqttAccount target = new HerodotusMqttAccount();
        target.setAccountId(source.getDeviceId());

        String productKey = source.getProduct().getProductKey();
        String deviceName = source.getDeviceName();

        target.setClientId(source.getClientId());
        target.setUsername(DataFormatUtils.toMqttUsername(productKey, deviceName));
        target.setPassword(SecurityUtils.encrypt(source.getDeviceSecret()));

        return target;
    }
}
