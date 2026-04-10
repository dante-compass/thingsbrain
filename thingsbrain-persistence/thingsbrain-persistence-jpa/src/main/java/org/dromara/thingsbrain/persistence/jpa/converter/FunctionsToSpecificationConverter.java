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

import org.dromara.thingsbrain.kernel.tsl.DimensionFactory;
import org.dromara.thingsbrain.kernel.tsl.Specification;
import org.dromara.thingsbrain.kernel.tsl.definition.AbstractToSpecificationConverter;
import org.dromara.thingsbrain.kernel.tsl.domain.Argument;
import org.dromara.thingsbrain.kernel.tsl.domain.EventDimension;
import org.dromara.thingsbrain.kernel.tsl.domain.ServiceDimension;
import org.dromara.thingsbrain.kernel.tsl.enums.Dimension;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 物模型功能列表转模型定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/2 22:57
 */
public class FunctionsToSpecificationConverter extends AbstractToSpecificationConverter<HerodotusTslFunction> {

    public FunctionsToSpecificationConverter(String productKey) {
        super(productKey);
    }

    @Override
    protected Map<Dimension, List<HerodotusTslFunction>> createFunctionGroup(List<HerodotusTslFunction> functions) {
        return functions.stream().collect(Collectors.groupingBy(HerodotusTslFunction::getDimension));
    }

    @Override
    protected void convertEvent(Specification specification, HerodotusTslFunction function) {
        List<Argument> arguments = toArguments(function.getArguments());
        EventDimension eventDimension = DimensionFactory.event(function.getName(), function.getIdentifier(), function.getRequired(), function.getEventType(), function.getDescription(), arguments);
        specification.add(eventDimension);
    }

    @Override
    protected void convertService(Specification specification, HerodotusTslFunction function) {
        ServiceDimension serviceDimension = DimensionFactory.service(function.getName(), function.getIdentifier(), function.getRequired(), function.getCallType(), function.getDescription(), null, null);
        toArgumentsWithGroup(function.getArguments(), serviceDimension);
        specification.add(serviceDimension);
    }

    private List<Argument> toArguments(Set<HerodotusTslArgument> arguments) {
        return Optional.ofNullable(arguments)
                .map(item -> item.stream().map(this::toArgument).toList())
                .orElse(new LinkedList<>());
    }


    private void toArgumentsWithGroup(Set<HerodotusTslArgument> arguments, ServiceDimension dimension) {
        if (CollectionUtils.isNotEmpty(arguments)) {
            Map<String, List<Argument>> maps = arguments.stream().collect(
                    Collectors.groupingBy(this::grouping,
                            Collectors.mapping(this::toArgument, Collectors.toList())));

            dimension.setInputData(maps.get(KEY_INPUT));
            dimension.setOutputData(maps.get(KEY_OUTPUT));
        }
    }

    private String grouping(HerodotusTslArgument argument) {
        return argument.getOutput() ? KEY_OUTPUT : KEY_INPUT;
    }

    private Argument toArgument(HerodotusTslArgument herodotusTslArgument) {
        return DimensionFactory.argument(herodotusTslArgument.getSpecs());
    }
}
