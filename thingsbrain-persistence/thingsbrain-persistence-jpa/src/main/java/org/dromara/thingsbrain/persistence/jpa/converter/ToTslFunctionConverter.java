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

import org.dromara.dante.data.jpa.converter.AbstractToAuditEntityConverter;
import org.dromara.thingsbrain.persistence.commons.domain.TslArgument;
import org.dromara.thingsbrain.persistence.commons.domain.TslFunction;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: {@link HerodotusTslFunction} 转 {@link TslFunction} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/17 16:07
 */
public class ToTslFunctionConverter extends AbstractToAuditEntityConverter<HerodotusTslFunction, TslFunction> {

    private final Converter<HerodotusTslArgument, TslArgument> toArgument;

    public ToTslFunctionConverter() {
        this.toArgument = new ToTslArgumentConverter();
    }

    @Override
    public TslFunction getInstance() {
        return new TslFunction();
    }

    @Override
    public void prepare(HerodotusTslFunction source, TslFunction target) {
        target.setId(source.getFunctionId());
        target.setProductId(source.getProductId());
        target.setProductKey(source.getProductKey());
        target.setDimension(source.getDimension());
        target.setAccessMode(source.getAccessMode());
        target.setEventType(source.getEventType());
        target.setCallType(source.getCallType());
        target.setRequired(source.getRequired());
        target.setMethod(source.getMethod());
        target.setDescription(source.getDescription());
        target.setArguments(toArguments(source.getArguments()));
        target.setIdentifier(source.getIdentifier());
        target.setName(source.getName());
        target.setType(source.getType());
        target.setSpecs(source.getSpecs());
    }

    private Set<TslArgument> toArguments(Set<HerodotusTslArgument> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(toArgument::convert).collect(Collectors.toSet());
        } else {
            return null;
        }
    }
}
