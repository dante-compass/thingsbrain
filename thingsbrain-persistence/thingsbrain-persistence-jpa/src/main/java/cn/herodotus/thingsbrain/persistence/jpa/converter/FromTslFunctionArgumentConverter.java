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

import cn.herodotus.thingsbrain.persistence.commons.domain.TslArgument;
import cn.herodotus.thingsbrain.persistence.commons.domain.TslFunctionArgument;
import cn.herodotus.thingsbrain.persistence.commons.enums.TslArgumentCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: {@link TslFunctionArgument} 转 {@link HerodotusTslFunctionArgument} 转换器  </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/20 13:16
 */
public class FromTslFunctionArgumentConverter implements Converter<TslFunctionArgument, Set<HerodotusTslFunctionArgument>> {

    private final Converter<TslArgument, HerodotusTslArgument> fromArgument;
    private final HerodotusTslFunction function;

    public FromTslFunctionArgumentConverter(HerodotusTslFunction function) {
        this.function = function;
        this.fromArgument = new FromTslArgumentConverter(function.getProductId());
    }

    private Set<HerodotusTslFunctionArgument> toFunctionArguments(HerodotusTslFunction function, List<TslArgument> source, TslArgumentCategory category) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream()
                    .map(fromArgument::convert)
                    .map(argument -> new HerodotusTslFunctionArgument(function, argument, category))
                    .collect(Collectors.toSet());
        } else {
            return Set.of();
        }
    }

    private Set<HerodotusTslFunctionArgument> toFunctionArguments(HerodotusTslFunction function, TslArgument source) {
        if (ObjectUtils.isNotEmpty(source)) {
            HerodotusTslArgument target = fromArgument.convert(source);
            return Set.of(new HerodotusTslFunctionArgument(function, target, TslArgumentCategory.PROPERTIES));
        } else {
            return Set.of();
        }
    }

    @Override
    public Set<HerodotusTslFunctionArgument> convert(TslFunctionArgument source) {
        switch (function.getDimension()) {
            case SERVICE -> {
                Set<HerodotusTslFunctionArgument> output = toFunctionArguments(function, source.getServiceOutputData(), TslArgumentCategory.SERVICES_OUTPUT_DATA);
                Set<HerodotusTslFunctionArgument> input = toFunctionArguments(function, source.getServiceInputData(), TslArgumentCategory.SERVICES_INPUT_DATA);
                return SetUtils.union(output, input);
            }
            case EVENT -> {
                return toFunctionArguments(function, source.getEventOutputData(), TslArgumentCategory.EVENTS_OUTPUT_DATA);
            }
            default -> {
                return toFunctionArguments(function, source.getProperty());
            }
        }
    }
}
