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

package org.dromara.thingsbrain.kernel.tsl.domain;

import org.dromara.thingsbrain.kernel.tsl.definition.AbstractDimension;
import org.dromara.thingsbrain.kernel.tsl.enums.CallType;
import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: 物模型 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:35
 */
public class ServiceDimension extends AbstractDimension {
    /**
     * async（异步调用）或sync（同步调用）
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private CallType callType;

    @JsonView(SpecificationViews.CompleteView.class)
    private List<Argument> inputData = new LinkedList<>();

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public List<Argument> getInputData() {
        return inputData;
    }

    public void setInputData(List<Argument> inputData) {
        this.inputData = inputData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("callType", callType)
                .toString();
    }
}
