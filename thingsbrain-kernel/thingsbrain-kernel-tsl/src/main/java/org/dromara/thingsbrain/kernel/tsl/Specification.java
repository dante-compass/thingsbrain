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

package org.dromara.thingsbrain.kernel.tsl;

import org.dromara.thingsbrain.kernel.tsl.domain.EventDimension;
import org.dromara.thingsbrain.kernel.tsl.domain.Profile;
import org.dromara.thingsbrain.kernel.tsl.domain.PropertyDimension;
import org.dromara.thingsbrain.kernel.tsl.domain.ServiceDimension;
import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: TSL 模型定义 </p>
 * <p>
 * 物模型TSL（Thing Specification Language）是一个JSON格式的文件，它是物理空间中的实体，
 * 如传感器、车载装置、楼宇、工厂等在云端的数字化表示，从属性、服务和事件三个维度，分别描述了该实体是什么、能做什么、可以对外提供哪些信息
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:41
 */
public class Specification {

    @JsonView(SpecificationViews.CompleteView.class)
    private String schema = "https://iotx-tsl.oss-ap-southeast-1.aliyuncs.com/schema.json";
    @JsonView(SpecificationViews.CompleteView.class)
    private Profile profile;
    @JsonView(SpecificationViews.SimpleView.class)
    private List<PropertyDimension> properties = new LinkedList<>();
    @JsonView(SpecificationViews.SimpleView.class)
    private List<EventDimension> events = new LinkedList<>();
    @JsonView(SpecificationViews.SimpleView.class)
    private List<ServiceDimension> services = new LinkedList<>();

    public List<EventDimension> getEvents() {
        return events;
    }

    public void setEvents(List<EventDimension> events) {
        this.events = events;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<PropertyDimension> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDimension> properties) {
        this.properties = properties;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<ServiceDimension> getServices() {
        return services;
    }

    public void setServices(List<ServiceDimension> services) {
        this.services = services;
    }

    public void add(PropertyDimension dimension) {
        this.properties.add(dimension);
    }

    public void add(EventDimension dimension) {
        this.events.add(dimension);
    }

    public void add(ServiceDimension dimension) {
        this.services.add(dimension);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schema", schema)
                .add("profile", profile)
                .toString();
    }
}
