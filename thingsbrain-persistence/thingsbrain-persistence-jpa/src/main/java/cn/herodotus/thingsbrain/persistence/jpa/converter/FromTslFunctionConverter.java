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

import cn.herodotus.dante.core.utils.StringTemplateUtils;
import cn.herodotus.dante.data.jpa.converter.AbstractFromAuditEntityConverter;
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.persistence.commons.domain.TslFunction;
import cn.herodotus.thingsbrain.persistence.commons.domain.TslFunctionArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.Set;

/**
 * <p>Description: {@link TslFunction} 转  {@link HerodotusTslFunction} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/17 16:07
 */
public class FromTslFunctionConverter extends AbstractFromAuditEntityConverter<TslFunction, HerodotusTslFunction> {

    @Override
    public HerodotusTslFunction getInstance() {
        return new HerodotusTslFunction();
    }

    @Override
    public void prepare(TslFunction source, HerodotusTslFunction target) {
        target.setFunctionId(source.getId());

        target.setProductId(source.getProductId());
        target.setIdentifier(source.getIdentifier());
        target.setName(source.getName());

        target.setProductKey(source.getProductKey());
        target.setDimension(source.getDimension());
        target.setAccessMode(source.getAccessMode());
        target.setEventType(source.getEventType());
        target.setCallType(source.getCallType());
        target.setMethod(source.getMethod());
        target.setDescription(source.getDescription());

        target.setArguments(toArguments(source.getArguments(), target));
    }

    private Set<HerodotusTslFunctionArgument> toArguments(TslFunctionArgument source, HerodotusTslFunction target) {
        if (ObjectUtils.isNotEmpty(source)) {
            Converter<TslFunctionArgument, Set<HerodotusTslFunctionArgument>> toFunctionArguments = new FromTslFunctionArgumentConverter(target);
            return toFunctionArguments.convert(source);
        }

        return Set.of();
    }

    /**
     * 手动设置 Method 值。
     * <p>
     * 如果前端没有传递 Method，同时 Function 为 Event 或者 Service 类型，则手动设置 Method
     *
     * @param source 物模型功能 {@link TslFunction}
     * @return method
     */
    private String getMethod(TslFunction source) {

        // 如果是 Event 或者 Service
        if (ObjectUtils.isNotEmpty(source.getDimension()) && source.getDimension() != Dimension.PROPERTY) {
            // 如果 Method 为空，则处理否则直接返回
            if (StringUtils.isBlank(source.getMethod())) {
                // 因为是根据 identifier 生成，如果 identifier 不为空则生成，否则则返回 null
                if (StringUtils.isNotBlank(source.getIdentifier())) {
                    if (source.getDimension() == Dimension.EVENT) {
                        return StringTemplateUtils.replace(MethodConstants.METHOD_FORMAT__EVENT, Map.of(ProtocolConstants.VARIABLE__EVENT_IDENTIFIER, source.getIdentifier()));
                    } else {
                        return StringTemplateUtils.replace(MethodConstants.METHOD_FORMAT__SERVICE, Map.of(ProtocolConstants.VARIABLE__SERVICE_IDENTIFIER, source.getIdentifier()));
                    }
                } else {
                    return null;
                }
            } else {
                return source.getMethod();
            }
        } else {
            // 只要是 Property 类型，不管 method 有没有值，都返回 null
            return null;
        }
    }
}
