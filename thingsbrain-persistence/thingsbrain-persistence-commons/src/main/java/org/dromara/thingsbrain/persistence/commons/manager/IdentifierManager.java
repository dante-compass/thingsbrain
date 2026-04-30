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

package org.dromara.thingsbrain.persistence.commons.manager;

import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.Product;

import java.util.Optional;

/**
 * <p>Description: 物联网设备标识管理器 </p>
 * <p>
 * 物联网 Product 和 Device 是关键的标识信息，在很多地方会使用。大量注入 ProductService 和 DeviceService，模块间耦合性高，代码不够清爽。
 * {@link IdentifierManager} 将 ProductService 和 DeviceService 相关操作包装成 Manager 层，用 {@link IdentifierManager} 进行统一的调用。后续如果有需要，也可以自定义进行扩展。
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 14:54
 */
public interface IdentifierManager {

    /**
     * 通过 productKey 查询物联网产品
     *
     * @param productKey 物联网产品 ProductKey
     * @return 物联网产品 {@link Product}
     */
    Optional<Product> findProductByProductKey(String productKey);

    /**
     * 通过 clientId 查询物联网设备
     *
     * @param clientId 物联网设备 ClientId
     * @return 物联网设备 {@link Device}
     */
    Optional<Device> findDeviceByClientId(String clientId);

    /**
     * 通过 deviceName 查询物联网设备
     *
     * @param deviceName 物联网设备 DeviceName
     * @return 物联网设备 {@link Device}
     */
    Optional<Device> findDeviceByDeviceName(String deviceName);

    /**
     * 物联网设备激活。设备首次上线，连接成功后设置激活状态。
     *
     * @param clientId 物联网设备 ClientId
     */
    void activation(String clientId);

    /**
     * 物联网设备注册
     *
     * @param device 物联网设备 {@link Device}
     */
    void registration(Device device);
}
