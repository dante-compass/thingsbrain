/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsMesh.
 *
 * Herodotus ThingsMesh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsMesh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.thingsbrain.persistence.commons.domain;

import cn.herodotus.dante.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 物联网物模型功能参数参数统一实体定义 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/20 13:09
 */
@Schema(name = "物联网物模型功能参数参数统一实体定义")
public class TslFunctionArgument implements BaseEntity {

    @Schema(name = "Property 对应参数")
    private TslArgument property;

    @Schema(name = "Event 输出数据对应参数")
    private List<TslArgument> eventOutputData = new ArrayList<>();

    @Schema(name = "Service 输出数据对应参数")
    private List<TslArgument> serviceOutputData = new ArrayList<>();

    @Schema(name = "参数类别")
    private List<TslArgument> serviceInputData = new ArrayList<>();

    public TslArgument getProperty() {
        return property;
    }

    public void setProperty(TslArgument property) {
        this.property = property;
    }

    public List<TslArgument> getEventOutputData() {
        return eventOutputData;
    }

    public void setEventOutputData(List<TslArgument> eventOutputData) {
        this.eventOutputData = eventOutputData;
    }

    public List<TslArgument> getServiceOutputData() {
        return serviceOutputData;
    }

    public void setServiceOutputData(List<TslArgument> serviceOutputData) {
        this.serviceOutputData = serviceOutputData;
    }

    public List<TslArgument> getServiceInputData() {
        return serviceInputData;
    }

    public void setServiceInputData(List<TslArgument> serviceInputData) {
        this.serviceInputData = serviceInputData;
    }

    public void appendEventOutputData(TslArgument tslArgument) {
        this.eventOutputData.add(tslArgument);
    }

    public void appendServiceOutputData(TslArgument tslArgument) {
        this.serviceOutputData.add(tslArgument);
    }

    public void appendServiceInputData(TslArgument tslArgument) {
        this.serviceInputData.add(tslArgument);
    }
}
