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
import cn.herodotus.thingsbrain.kernel.tsl.definition.AbstractToSpecificationConverter;
import cn.herodotus.thingsbrain.kernel.tsl.definition.Argument;
import cn.herodotus.thingsbrain.kernel.tsl.domain.EventDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.PropertyDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.persistence.commons.enums.TslArgumentCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

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

    private Argument toArgument(HerodotusTslArgument herodotusTslArgument) {
        return DimensionFactory.argument(herodotusTslArgument.getSpecs());
    }

    /**
     * 从 {@link Set<HerodotusTslFunctionArgument>} 提取对应的 Arguments
     *
     * @param functionArguments Function 与 Arguments 关联关系 {@link HerodotusTslFunctionArgument}
     * @return Argument 列表 {@link List<HerodotusTslArgument>}
     */
    private List<Argument> toArguments(Set<HerodotusTslFunctionArgument> functionArguments) {
        return Optional.ofNullable(functionArguments)
                .map(items -> items.stream()
                        .map(HerodotusTslFunctionArgument::getArgument)
                        .map(this::toArgument)
                        .toList())
                .orElse(new LinkedList<>());
    }

    @Override
    protected void appendArgumentToProperty(PropertyDimension dimension, HerodotusTslFunction function) {
        // 一个 Property 对应一个 Argument，先取到 Argument
        Argument argument = toArgument(function.getFirstArgument());

        // 将 Argument 设置到 PropertyDimension 中
        if (ObjectUtils.isNotEmpty(argument)) {
            dimension.setDataType(argument.getDataType());
        }
    }

    @Override
    protected void appendArgumentToEvent(EventDimension dimension, HerodotusTslFunction function) {
        // 一个 Event 对应多个 Output Argument，先取到 Argument
        List<Argument> arguments = toArguments(function.getArguments());

        if (CollectionUtils.isNotEmpty(arguments)) {
            dimension.setOutputData(arguments);
        }
    }

    private TslArgumentCategory groupingServiceArguments(HerodotusTslFunctionArgument functionArgument) {
        return functionArgument.getCategory() == TslArgumentCategory.SERVICES_OUTPUT_DATA ? TslArgumentCategory.SERVICES_OUTPUT_DATA : TslArgumentCategory.SERVICES_INPUT_DATA;
    }

    private void getServiceArguments(ServiceDimension dimension, Set<HerodotusTslFunctionArgument> functionArguments) {
        if (CollectionUtils.isNotEmpty(functionArguments)) {
            Map<TslArgumentCategory, List<Argument>> maps = functionArguments.stream().collect(
                    Collectors.groupingBy(this::groupingServiceArguments,
                            Collectors.mapping(item -> toArgument(item.getArgument()), Collectors.toList())));

            dimension.setInputData(maps.get(TslArgumentCategory.SERVICES_INPUT_DATA));
            dimension.setOutputData(maps.get(TslArgumentCategory.SERVICES_OUTPUT_DATA));
        }
    }

    @Override
    protected void appendArgumentToService(ServiceDimension dimension, HerodotusTslFunction function) {
        // 一个 Service 对应多个 Input 或者 Output 参数。解析并设置到 ServiceDimension
        getServiceArguments(dimension, function.getArguments());
    }
}
