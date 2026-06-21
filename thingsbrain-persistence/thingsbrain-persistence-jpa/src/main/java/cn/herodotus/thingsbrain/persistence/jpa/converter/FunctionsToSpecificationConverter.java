/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * ThingsBrain licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ThingsBrain 是 Dante Cloud 系统生态产品，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 ThingsBrain 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.thingsbrain.persistence.jpa.converter;

import cn.herodotus.thingsbrain.kernel.tsl.DimensionFactory;
import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import cn.herodotus.thingsbrain.kernel.tsl.definition.AbstractToSpecificationConverter;
import cn.herodotus.thingsbrain.kernel.tsl.domain.Argument;
import cn.herodotus.thingsbrain.kernel.tsl.domain.EventDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
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
