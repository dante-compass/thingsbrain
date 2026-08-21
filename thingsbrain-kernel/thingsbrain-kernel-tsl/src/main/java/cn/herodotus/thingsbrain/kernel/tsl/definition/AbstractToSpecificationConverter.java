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

package cn.herodotus.thingsbrain.kernel.tsl.definition;

import cn.herodotus.thingsbrain.kernel.tsl.DimensionFactory;
import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import cn.herodotus.thingsbrain.kernel.tsl.domain.EventDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.Profile;
import cn.herodotus.thingsbrain.kernel.tsl.domain.PropertyDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 物模型功能转物模型定义转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/3 18:13
 */
public abstract class AbstractToSpecificationConverter<S extends SpecificationMetadata> implements Converter<List<S>, Specification> {

    private final String productKey;

    protected AbstractToSpecificationConverter(String productKey) {
        this.productKey = productKey;
    }

    /**
     * 将物模型功能按照物模型维度 {@link Dimension} 进行分组
     *
     * @param functions 物模型功能列表
     * @return 物模型功能分组 {@link Map}
     */
    private Map<Dimension, List<S>> createFunctionGroup(List<S> functions) {
        return functions.stream().collect(Collectors.groupingBy(S::getDimension));
    }

    /**
     * 转换物模型属性，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型属性功能列表
     */
    private void convertProperties(Specification specification, List<S> functions) {
        functions.forEach(function -> {
            // 一个 function 对应一个 Event。构造一个 PropertyDimension
            PropertyDimension dimension = DimensionFactory.property(function.getName(), function.getIdentifier(), function.getRequired(), function.getAccessMode());

            // 从 function 中提取对应的 Argument，并设置到 PropertyDimension
            appendArgumentToProperty(dimension, function);

            // 将该 Property Function 转成的 PropertyDimension 添加至 specification
            specification.add(dimension);
        });
    }

    /**
     * 转换物模型属性，并将其设置到物模性定义，并将其设置到 {@link PropertyDimension} 中
     *
     * @param dimension 物模型 Property {@link PropertyDimension}
     * @param function  物模型功能定义数据
     */
    protected abstract void appendArgumentToProperty(PropertyDimension dimension, S function);

    /**
     * 转换物模型事件，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型事件功能列表
     */
    private void convertEvents(Specification specification, List<S> functions) {
        functions.forEach(function -> {
            // 一个 function 对应一个 Event。先构造一个 EventDimension
            EventDimension dimension = DimensionFactory.event(function.getName(), function.getIdentifier(), function.getRequired(), function.getEventType(), function.getDescription(), function.getMethod());

            // 从 function 中提取对应的 Argument，并设置到 EventDimension
            appendArgumentToEvent(dimension, function);

            // 将该 Event Function 转成的 EventDimension 添加至 specification
            specification.add(dimension);
        });
    }

    /**
     * 转换物模型事件，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param dimension 物模型 Property {@link EventDimension}
     * @param function  物模型功能定义数据
     */
    protected abstract void appendArgumentToEvent(EventDimension dimension, S function);

    /**
     * 转换物模型服务，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型属性功能列表
     */
    private void convertServices(Specification specification, List<S> functions) {
        functions.forEach(function -> {
            // 一个 function 对应一个 Service。先构造一个 ServiceDimension
            ServiceDimension dimension = DimensionFactory.service(function.getName(), function.getIdentifier(), function.getRequired(), function.getCallType(), function.getDescription(), function.getMethod());

            // 一个 Service 对应多个 Input 或者 Output 参数。先构造一个 ServiceDimension
            appendArgumentToService(dimension, function);

            // 将该 Service Function 转成的 ServiceDimension 添加至 specification
            specification.add(dimension);
        });
    }

    /**
     * 转换物模型服务，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param dimension 物模型 Property {@link ServiceDimension}
     * @param function  物模型功能定义数据
     */
    protected abstract void appendArgumentToService(ServiceDimension dimension, S function);

    @Override
    public Specification convert(List<S> source) {

        Map<Dimension, List<S>> functionGroup = createFunctionGroup(source);

        Specification target = new Specification();

        Profile profile = new Profile();
        profile.setProductKey(this.productKey);
        target.setProfile(profile);

        convertProperties(target, functionGroup.get(Dimension.PROPERTY));
        convertEvents(target, functionGroup.get(Dimension.EVENT));
        convertServices(target, functionGroup.get(Dimension.SERVICE));

        return target;
    }
}
