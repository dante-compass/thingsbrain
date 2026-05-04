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

package org.dromara.thingsbrain.link.manager.device;

import org.dromara.thingsbrain.kernel.link.definition.config.FileConfig;
import org.dromara.thingsbrain.kernel.link.domain.config.ConfigDomain;
import org.dromara.thingsbrain.link.commons.definition.DeviceConfigManager;

/**
 * <p>Description: 默认配置管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 12:40
 */
public class DefaultDeviceConfigManager implements DeviceConfigManager {

    @Override
    public ConfigDomain get(String productKey, String deviceName, FileConfig param) {
        return null;
    }
}
