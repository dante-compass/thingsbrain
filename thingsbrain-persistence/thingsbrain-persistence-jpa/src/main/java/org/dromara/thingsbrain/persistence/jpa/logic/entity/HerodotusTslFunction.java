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

package org.dromara.thingsbrain.persistence.jpa.logic.entity;

import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.dromara.thingsbrain.kernel.tsl.definition.Metadata;
import org.dromara.thingsbrain.kernel.tsl.enums.AccessMode;
import org.dromara.thingsbrain.kernel.tsl.enums.CallType;
import org.dromara.thingsbrain.kernel.tsl.enums.Dimension;
import org.dromara.thingsbrain.kernel.tsl.enums.EventType;
import org.dromara.thingsbrain.persistence.commons.constant.PersistenceConstants;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

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
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_FUNCTION)
public class HerodotusTslFunction extends AbstractTslArgument implements Metadata {

    @Id
    @UuidGenerator
    @Column(name = "function_id", length = 64)
    private String functionId;

    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "product_key", length = 32)
    private String productKey;

    @Column(name = "dimension")
    @Enumerated(EnumType.STRING)
    private Dimension dimension;

    @Column(name = "access_mode")
    @Enumerated(EnumType.STRING)
    private AccessMode accessMode;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "call_type")
    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Column(name = "is_required")
    private Boolean required = Boolean.FALSE;

    @Column(name = "method", length = 100)
    private String method;

    @Column(name = "description", length = 512)
    private String description;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_ARGUMENT)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "iot_tsl_function_argument",
            joinColumns = {@JoinColumn(name = "function_id")},
            inverseJoinColumns = {@JoinColumn(name = "argument_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"function_id", "argument_id"})},
            indexes = {@Index(name = "iot_tsl_function_argument_fid_idx", columnList = "function_id"), @Index(name = "iot_tsl_function_argument_aid_idx", columnList = "argument_id")})
    private Set<HerodotusTslArgument> arguments = new HashSet<>();

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

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

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<HerodotusTslArgument> getArguments() {
        return arguments;
    }

    public void setArguments(Set<HerodotusTslArgument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("functionId", functionId)
                .add("productId", productId)
                .add("productKey", productKey)
                .add("dimension", dimension)
                .add("accessMode", accessMode)
                .add("eventType", eventType)
                .add("callType", callType)
                .add("required", required)
                .add("method", method)
                .add("description", description)
                .addValue(super.toString())
                .toString();
    }
}
