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

import com.google.common.base.MoreObjects;
import org.dromara.thingsbrain.kernel.commons.definition.domain.shadow.AbstractShadow;
import org.dromara.thingsbrain.kernel.commons.definition.domain.shadow.State;

import java.util.function.Consumer;

/**
 * <p>Description: 设备影子结构定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/28 21:33
 */
public class Shadow extends AbstractShadow {

    private Long timestamp;

    public Shadow() {
        super();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    private void process(State state, Long version, Consumer<State> consumer) {
        consumer.accept(state);
        this.setVersion(version);
        this.setTimestamp(System.currentTimeMillis());
    }

    public void update(State state, Long version) {
        process(state, version, this::update);
    }

    public void delete(State state, Long version) {
        process(state, version, this::delete);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .addValue(super.toString())
                .toString();
    }
}
