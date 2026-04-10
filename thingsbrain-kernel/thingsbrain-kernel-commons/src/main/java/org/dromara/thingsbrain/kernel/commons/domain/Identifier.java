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

import org.dromara.dante.core.domain.BaseModel;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 物联网核心标识符 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/1 22:45
 */
public class Identifier implements BaseModel {

    /**
     * 物联网 ProductKey
     */
    private String productKey;
    /**
     * 物联网 DeviceName
     */
    private String deviceName;

    public Identifier() {
    }

    public Identifier(String productKey, String deviceName) {
        this.productKey = productKey;
        this.deviceName = deviceName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .toString();
    }
}
