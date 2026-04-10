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

package org.dromara.thingsbrain.kernel.tsl.definition;

import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 物模型参数通用属性定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 17:43
 */
public abstract class AbstractArgument implements Serializable {

    /**
     * 参数唯一标识符
     * <p>
     * Property: 属性唯一标识符（物模型模块下唯一）
     * Event: 事件唯一标识符（物模型模块下唯一，其中post是默认生成的属性上报事件
     * Service: 服务唯一标识符（物模型模块下唯一，其中set/get是根据属性的accessMode默认生成的服务）
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private String identifier;
    /**
     * 参数名称
     * <p>
     * Property: 属性名称
     * Event: 事件名称
     * Service: 服务名称
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private String name;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("identifier", identifier)
                .add("name", name)
                .toString();
    }
}
