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

package org.dromara.thingsbrain.persistence.commons.service;

import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * <p>Description: 物联网设备分类服务定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/3 19:11
 */
public interface DeviceService extends BaseWriteAndPageService<Device, String> {

    /**
     * 分页条件查询。根据 ProductKey 进行分页查询
     *
     * @param pageNumber 当前页数
     * @param pageSize   分页大小
     * @param productKey 物联网 ProductKey
     * @return 查询结果 {@link Page<Device>}
     */
    Page<Device> findByCondition(int pageNumber, int pageSize, String productKey);

    /**
     * 根据 clientId 查询设备。ClientId 默认格式为：ProductKey.DeviceName
     *
     * @param clientId 设备 clientId
     * @return 设备 {@link Device}
     */
    Optional<Device> findByClientId(String clientId);

    /**
     * 根据 deviceName 查询设备。
     *
     * @param deviceName 设备名称
     * @return 设备 {@link Device}
     */
    Optional<Device> findByDeviceName(String deviceName);

    /**
     * 设备注册
     *
     * @param device 设备信息
     */
    void registration(Device device);

    /**
     * 设备激活
     *
     * @param clientId 设备 ClientId
     */
    void activation(String clientId);
}
