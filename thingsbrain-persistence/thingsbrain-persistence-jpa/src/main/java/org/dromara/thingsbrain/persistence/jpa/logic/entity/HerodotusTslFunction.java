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

package org.dromara.thingsbrain.persistence.jpa.logic.entity;

import org.dromara.thingsbrain.kernel.tsl.definition.Metadata;
import org.dromara.thingsbrain.kernel.tsl.enums.AccessMode;
import org.dromara.thingsbrain.kernel.tsl.enums.CallType;
import org.dromara.thingsbrain.kernel.tsl.enums.Dimension;
import org.dromara.thingsbrain.kernel.tsl.enums.EventType;
import org.dromara.thingsbrain.persistence.commons.constant.PersistenceConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
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
