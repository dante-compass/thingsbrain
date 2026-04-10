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

package org.dromara.thingsbrain.kernel.tsl.domain;

import org.dromara.thingsbrain.kernel.tsl.enums.AccessMode;
import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 物模型 Property </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:27
 */
public class PropertyDimension extends Argument {
    /**
     * "属性读写类型：只读（r）或读写（rw）
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private AccessMode accessMode;
    /**
     * 是否是标准功能的必选服务：是（true），否（false）
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private Boolean required;

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accessMode", accessMode)
                .add("required", required)
                .addValue(super.toString())
                .toString();
    }
}
