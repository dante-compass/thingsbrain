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

package org.dromara.thingsbrain.kernel.commons.utils;

import org.dromara.dante.core.constant.SymbolConstants;
import org.dromara.thingsbrain.kernel.commons.domain.AddressTuple;
import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import org.dromara.thingsbrain.kernel.commons.domain.MqttClientIdDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Description: 物联网数据格式工具类 </p>
 * <p>
 * 涉及很多字符串类型数据解析与组合。与其说是工具类本质更是本系统规则的定义。
 *
 * @author : gengwei.zheng
 * @date : 2025/4/7 11:22
 */
public class DataFormatUtils {

    /**
     * 从 Mqtt Username 中解析 ProductKey 和 DeviceName 将其组合成 {@link Identifier} 方便使用。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，Mqtt username 格式定义为 ${deviceName}&${productKey}
     *
     * @param username mqtt 连接用户名
     * @return 设备标识符 {@link Identifier}
     */
    public static Optional<Identifier> fromMqttUsername(String username) {
        if (StringUtils.isNotBlank(username) && Strings.CS.contains(username, SymbolConstants.AMPERSAND)) {
            String[] split = StringUtils.split(username, SymbolConstants.AMPERSAND);
            return Optional.of(new Identifier(split[1], split[0]));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 根据 ProductKey 和 DeviceName 生成 Mqtt Username。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，Mqtt username 格式定义为 ${deviceName}&${productKey}
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @return Mqtt Username
     */
    public static String toMqttUsername(String productKey, String deviceName) {
        return deviceName + SymbolConstants.AMPERSAND + productKey;
    }

    /**
     * 根据 ProductKey 和 DeviceName 生成 Mqtt Username。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，Mqtt username 格式定义为 ${deviceName}&${productKey}
     *
     * @param identifier 设备标识符
     * @return Mqtt Username
     */
    public static String toMqttUsername(Identifier identifier) {
        return toMqttUsername(identifier.getProductKey(), identifier.getDeviceName());
    }

    /**
     * 从设备 clientId 中解析 ProductKey 和 DeviceName 将其组合成 {@link Identifier} 方便使用。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，如果是由系统默认生成而非用户指定的设备 clientId 格式定义为 ${productKey}.${deviceName}
     *
     * @param clientId 设备 clientId
     * @return 设备二元组 {@link Identifier}
     */
    public static Optional<Identifier> fromDeviceClientId(String clientId) {
        if (StringUtils.isNotBlank(clientId) && Strings.CS.contains(clientId, SymbolConstants.PERIOD)) {
            String[] split = StringUtils.split(clientId, SymbolConstants.PERIOD);
            return Optional.of(new Identifier(split[0], split[1]));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 根据 ProductKey 和 DeviceName 生成设备 ClientId。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，设备 ClientId 格式定义为 ${productKey}.${deviceName}
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @return Mqtt Username
     */
    public static String toDeviceClientId(String productKey, String deviceName) {
        return productKey + SymbolConstants.PERIOD + deviceName;
    }

    /**
     * 根据 ProductKey 和 DeviceName 生成设备 ClientId。
     * <p>
     * 参照阿里云物联网规则以及本系统设计，设备 ClientId 格式定义为 ${productKey}.${deviceName}
     *
     * @param identifier 设备标识符 {@link Identifier}
     * @return Mqtt Username
     */
    public static String toDeviceClientId(Identifier identifier) {
        return toDeviceClientId(identifier.getProductKey(), identifier.getDeviceName());
    }

    /**
     * 从 Mqtt clientId 中解析 clientId 和相关参数，将其组合成 {@link MqttClientIdDetail} 方便使用。
     * 其中解析后的 clientId 为字符串，解析后的参数被二次解析为 {@link Map}
     * <p>
     * 参照阿里云物联网规则以及本系统设计，Mqtt Client Id 格式为：clientId|xxx=x,yyy=y|
     *
     * @param mqttClientId Mqtt ClientId
     * @return Mqtt ClientId二元组 {@link MqttClientIdDetail}
     */
    public static Optional<MqttClientIdDetail> fromMqttClientId(String mqttClientId) {
        if (StringUtils.isNotBlank(mqttClientId) && Strings.CS.contains(mqttClientId, SymbolConstants.PIPE)) {
            String[] split = StringUtils.split(mqttClientId, SymbolConstants.PIPE);
            return Optional.of(new MqttClientIdDetail(split[0], split[1]));
        } else {
            return Optional.of(new MqttClientIdDetail(mqttClientId));
        }
    }

    /**
     * 从 Mqtt SockName 中解析 Ip 地址和端口，将其组合成 {@link AddressTuple} 方便使用。
     *
     * @param sockName Mqtt SockName
     * @return Mqtt SockName 二元组 {@link AddressTuple}
     */
    public static Optional<AddressTuple> fromMqttSockName(String sockName) {
        if (StringUtils.isNotBlank(sockName) && Strings.CS.contains(sockName, SymbolConstants.COLON)) {
            String[] split = StringUtils.split(sockName, SymbolConstants.COLON);
            return Optional.of(new AddressTuple(split[0], split[1]));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Mqtt Client Id 参数解析。将参数解析为 {@link Map}
     *
     * @param params Mqtt Client Id 参数
     * @return 参数 {@link Map}
     */
    public static Map<String, String> parseMqttParams(String params) {
        String[] split = StringUtils.split(params, SymbolConstants.COMMA);
        return Arrays.stream(split)
                .map(item -> item.split(SymbolConstants.EQUAL))
                .collect(Collectors.toMap(item -> item[0], item -> item[1]));
    }
}
