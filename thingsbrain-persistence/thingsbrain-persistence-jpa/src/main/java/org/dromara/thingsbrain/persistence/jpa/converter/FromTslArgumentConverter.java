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

package org.dromara.thingsbrain.persistence.jpa.converter;

import org.dromara.dante.data.jpa.converter.AbstractFromAuditEntityConverter;
import org.dromara.thingsbrain.persistence.commons.domain.TslArgument;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;

/**
 * <p>Description: {@link TslArgument} 转 {@link HerodotusTslArgument} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/8 16:09
 */
public class FromTslArgumentConverter extends AbstractFromAuditEntityConverter<TslArgument, HerodotusTslArgument> {

    @Override
    public HerodotusTslArgument getInstance() {
        return new HerodotusTslArgument();
    }

    @Override
    public void prepare(TslArgument source, HerodotusTslArgument target) {
        target.setArgumentId(source.getId());
        target.setOutput(source.getOutput());
        target.setIdentifier(source.getIdentifier());
        target.setName(source.getName());
        target.setType(source.getType());
        target.setSpecs(source.getSpecs());
    }
}
