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

package org.dromara.thingsbrain.kernel.commons.definition.domain;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: Herodotus Link 协议通用属性定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/28 21:49
 */
public abstract class AbstractProtocolEntity<T> extends AbstractEntity {

    /**
     * 版本号。对应关系
     * 对于"设备属性、事件、服务": 为 String 类型，表示为协议版本号，目前协议版本号唯一取值为1.0
     * 对于"设备影子数据流": 为 Long 类型，如果version设置为-1时，表示清空设备影子数据，设备影子会接收设备端的请求，并将设备影子版本更新为0
     */
    private T version;

    public T getVersion() {
        return version;
    }

    public void setVersion(T version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .addValue(super.toString())
                .toString();
    }
}
