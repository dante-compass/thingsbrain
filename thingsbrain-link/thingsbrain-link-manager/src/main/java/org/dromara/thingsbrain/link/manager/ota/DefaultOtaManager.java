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

package org.dromara.thingsbrain.link.manager.ota;

import org.dromara.thingsbrain.kernel.protocol.definition.DeviceModule;
import org.dromara.thingsbrain.kernel.protocol.domain.ota.DeviceInformParam;
import org.dromara.thingsbrain.kernel.protocol.domain.ota.DeviceProgressParam;
import org.dromara.thingsbrain.kernel.protocol.domain.ota.HttpProtocolDomain;
import org.dromara.thingsbrain.link.commons.definition.OtaManager;

/**
 * <p>Description: Ota 管理器默认实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 11:34
 */
public class DefaultOtaManager implements OtaManager {

    @Override
    public HttpProtocolDomain getFirmware(String productKey, String deviceName, DeviceModule param) {
        return null;
    }

    @Override
    public void inform(String productKey, String deviceName, DeviceInformParam param) {

    }

    @Override
    public void progress(String productKey, String deviceName, DeviceProgressParam param) {

    }
}
