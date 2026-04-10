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

import org.dromara.thingsbrain.kernel.tsl.definition.AbstractDimension;
import org.dromara.thingsbrain.kernel.tsl.enums.EventType;
import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 物模型 Event </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:36
 */
public class EventDimension extends AbstractDimension {

    /**
     * 事件类型（info、alert、error）
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private EventType type;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("type", type)
                .toString();
    }
}
