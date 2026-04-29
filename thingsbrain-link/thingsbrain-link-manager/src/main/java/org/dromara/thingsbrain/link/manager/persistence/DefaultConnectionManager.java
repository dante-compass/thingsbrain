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

import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.manager.ConnectionManager;
import org.dromara.thingsbrain.persistence.commons.service.DeviceConnectionService;

import java.time.LocalDateTime;

/**
 * <p>Description: 物联网设备上下线管理器默认实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 17:31
 */
public class DefaultConnectionManager implements ConnectionManager {

    private final DeviceConnectionService deviceConnectionService;

    public DefaultConnectionManager(DeviceConnectionService deviceConnectionService) {
        this.deviceConnectionService = deviceConnectionService;
    }

    @Override
    public void connected(String clientId, boolean isSignature, DeviceConnection deviceConnection) {
        deviceConnectionService.connected(clientId, isSignature, deviceConnection);
    }

    @Override
    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        deviceConnectionService.disconnected(clientId, reason, disconnectedAt);
    }
}
