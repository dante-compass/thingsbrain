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

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.thingsbrain.kernel.tsl.definition.SpecificationMetadata;
import cn.herodotus.thingsbrain.kernel.tsl.enums.AccessMode;
import cn.herodotus.thingsbrain.kernel.tsl.enums.CallType;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.EventType;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 物联网物模型功能 Jpa 存储实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/4 16:46
 */
@Entity
@Table(name = "iot_tsl_function", indexes = {
        @Index(name = "iot_tsl_function_id_idx", columnList = "function_id"),
        @Index(name = "iot_tsl_function_pid_idx", columnList = "product_id"),
        @Index(name = "iot_tsl_function_pk_idx", columnList = "product_key")
})
@NamedEntityGraph(
        name = PersistenceConstants.ENTITY_GRAPH_TSL_FUNCTION_WITH_ARGUMENTS,
        attributeNodes = {
                @NamedAttributeNode(value = "arguments", subgraph = "function-argument-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "function-argument-subgraph",
                        attributeNodes = @NamedAttributeNode(value = "argument")
                )
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_FUNCTION)
public class HerodotusTslFunction extends AbstractTslCharacteristic implements SpecificationMetadata {

    @Id
    @UuidGenerator
    @Column(name = "function_id", length = 64)
    private String functionId;

    @Column(name = "product_key", length = 32)
    private String productKey;

    @Column(name = "dimension", length = 50)
    @Enumerated(EnumType.STRING)
    private Dimension dimension;

    @Column(name = "access_mode", length = 50)
    @Enumerated(EnumType.STRING)
    private AccessMode accessMode;

    @Column(name = "event_type", length = 50)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "call_type", length = 50)
    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Column(name = "is_required")
    private Boolean required = Boolean.FALSE;

    @Column(name = "method", length = 100)
    private String method;

    @Column(name = "description", length = 512)
    private String description;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_FUNCTION_ARGUMENT)
    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<HerodotusTslFunctionArgument> arguments = new HashSet<>();

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<HerodotusTslFunctionArgument> getArguments() {
        return arguments;
    }

    public void setArguments(Set<HerodotusTslFunctionArgument> arguments) {
        this.arguments = arguments;
    }

    public HerodotusTslFunction remove(Set<HerodotusTslFunctionArgument> source) {
        if (CollectionUtils.isNotEmpty(this.arguments) && CollectionUtils.isNotEmpty(source)) {

            Set<HerodotusTslArgument> tslArguments = source.stream()
                    .map(HerodotusTslFunctionArgument::getArgument)
                    .collect(Collectors.toSet());

            this.arguments.removeIf(arg -> tslArguments.contains(arg.getArgument()));
        }

        return this;
    }

    public HerodotusTslArgument getFirstArgument() {
        if (CollectionUtils.isNotEmpty(this.arguments)) {
            return arguments.stream()
                    .findFirst()
                    .map(HerodotusTslFunctionArgument::getArgument)
                    .orElse(null);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusTslFunction function = (HerodotusTslFunction) o;
        return Objects.equals(functionId, function.functionId) && Objects.equals(getProductId(), function.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, getProductId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("functionId", functionId)
                .add("productKey", productKey)
                .add("dimension", dimension)
                .add("accessMode", accessMode)
                .add("eventType", eventType)
                .add("callType", callType)
                .add("required", required)
                .add("method", method)
                .add("description", description)
                .toString();
    }
}
