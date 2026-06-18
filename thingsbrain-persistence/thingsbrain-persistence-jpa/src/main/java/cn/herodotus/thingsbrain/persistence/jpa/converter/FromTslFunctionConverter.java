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
import cn.herodotus.thingsbrain.persistence.commons.domain.TslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.dante.data.jpa.converter.AbstractFromAuditEntityConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: {@link TslFunction} 转  {@link HerodotusTslFunction} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/17 16:07
 */
public class FromTslFunctionConverter extends AbstractFromAuditEntityConverter<TslFunction, HerodotusTslFunction> {

    private final Converter<TslArgument, HerodotusTslArgument> fromArgument;

    public FromTslFunctionConverter() {
        this.fromArgument = new FromTslArgumentConverter();
    }

    @Override
    public HerodotusTslFunction getInstance() {
        return new HerodotusTslFunction();
    }

    @Override
    public void prepare(TslFunction source, HerodotusTslFunction target) {
        target.setFunctionId(source.getId());
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

    private Set<HerodotusTslArgument> toArguments(Set<TslArgument> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(fromArgument::convert).collect(Collectors.toSet());
        } else {
            return Set.of();
        }
    }
}
