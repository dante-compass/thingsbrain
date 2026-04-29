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

package org.dromara.thingsbrain.link.manager.persistence;

import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.persistence.commons.service.DeviceService;
import org.dromara.thingsbrain.persistence.commons.service.ProductService;

import java.util.Optional;

/**
 * <p>Description: 物联网设备标识管理器默认实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 15:26
 */
public class DefaultIdentifierManager implements IdentifierManager {

    private final ProductService productService;
    private final DeviceService deviceService;

    public DefaultIdentifierManager(ProductService productService, DeviceService deviceService) {
        this.productService = productService;
        this.deviceService = deviceService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findProductByProductKey(String productKey) {
        return productService.findByProductKey(productKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Device> findDeviceByClientId(String clientId) {
        return deviceService.findByClientId(clientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Device> findDeviceByDeviceName(String deviceName) {
        return deviceService.findByDeviceName(deviceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activation(String clientId) {
        deviceService.activation(clientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registration(Device device) {
        deviceService.registration(device);
    }
}
