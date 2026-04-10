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

package org.dromara.thingsbrain.kernel.tsl.definition;

import org.dromara.thingsbrain.kernel.tsl.DimensionFactory;
import org.dromara.thingsbrain.kernel.tsl.Specification;
import org.dromara.thingsbrain.kernel.tsl.domain.*;
import org.dromara.thingsbrain.kernel.tsl.enums.AccessMode;
import org.dromara.thingsbrain.kernel.tsl.enums.Dimension;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物模型功能转物模型定义转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/3 18:13
 */
public abstract class AbstractToSpecificationConverter<S extends Metadata> implements Converter<List<S>, Specification> {

    protected static final String KEY_INPUT = "input";
    protected static final String KEY_OUTPUT = "output";

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
    protected abstract Map<Dimension, List<S>> createFunctionGroup(List<S> functions);

    /**
     * 转换物模型属性，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型属性功能
     */
    protected void convertProperties(Specification specification, List<S> functions) {
        PropertyMetadata<S> metadata = new PropertyMetadata<>();
        metadata.add(functions);

        specification.setProperties(metadata.getDimensions());

        EventDimension postEventDimension = DimensionFactory.post(metadata.getReadOnlyItems());
        ServiceDimension setServiceDimension = DimensionFactory.set(metadata.getReadWriteItems());
        ServiceDimension getServiceDimension = DimensionFactory.get(metadata.getReadOnlyItems(), metadata.getReadOnlyItems());

        specification.add(postEventDimension);
        specification.add(setServiceDimension);
        specification.add(getServiceDimension);
    }

    /**
     * 转换物模型事件，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型事件功能
     */
    private void convertEvents(Specification specification, List<S> functions) {
        functions.forEach(item -> convertEvent(specification, item));
    }

    /**
     * 转换物模型事件，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param function      物模型事件功能
     */
    protected abstract void convertEvent(Specification specification, S function);

    /**
     * 转换物模型服务，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param functions     物模型属性功能
     */
    private void convertServices(Specification specification, List<S> functions) {
        functions.forEach(item -> convertService(specification, item));
    }

    /**
     * 转换物模型服务，并将其设置到物模性定义 {@link Specification} 中
     *
     * @param specification 物模性定义 {@link Specification}
     * @param function      物模型服务功能
     */
    protected abstract void convertService(Specification specification, S function);

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

    static class PropertyMetadata<S extends Metadata> {
        private final List<PropertyDimension> dimensions;
        private final List<Argument> readOnlyItems;
        private final List<Argument> readWriteItems;
        private final List<String> identifiers;

        public PropertyMetadata() {
            this.dimensions = new ArrayList<>();
            this.readOnlyItems = new ArrayList<>();
            this.readWriteItems = new ArrayList<>();
            this.identifiers = new ArrayList<>();
        }

        public void add(List<S> functions) {
            functions.forEach(function -> add(function.getSpecs(), function.getRequired(), function.getAccessMode()));
        }

        private void add(String specs, Boolean required, AccessMode accessMode) {
            // 将 Specs 转换成 Argument 对象
            Argument argument = DimensionFactory.argument(specs);
            // 根据 Argument 和相关信息生成 PropertyDimension 并添加到属性列表中，作为物模型属性维度的主体
            PropertyDimension dimension = DimensionFactory.property(argument.getName(), argument.getIdentifier(), required, accessMode, argument.getDataType());
            dimensions.add(dimension);
            // 默认 Post Event OutputData 和 Get Service OutputData 需要包含所有的属性参数
            readOnlyItems.add(argument);
            // 默认 Set Service InputData 仅包含 AccessMode 为 ReadWrite 的属性参数
            if (accessMode == AccessMode.READ_WRITE) {
                readWriteItems.add(argument);
            }
            // Get Service InputData 需要包含所有的属性参数 Identifier
            identifiers.add(argument.getIdentifier());
        }

        public List<PropertyDimension> getDimensions() {
            return dimensions;
        }

        public List<Argument> getReadOnlyItems() {
            return readOnlyItems;
        }

        public List<Argument> getReadWriteItems() {
            return readWriteItems;
        }

        public List<String> getIdentifiers() {
            return identifiers;
        }
    }
}
