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

package org.dromara.thingsbrain.kernel.tsl.describe;

import org.dromara.thingsbrain.kernel.commons.constant.SchemaConstants;
import org.dromara.thingsbrain.kernel.tsl.definition.AbstractDescribe;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * <p>Description: Bool 类型 JSON Schema 描述 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/4 21:00
 */
public class BooleanDescribe extends AbstractDescribe {

    public BooleanDescribe() {
        setType(SchemaConstants.SCHEMA_TYPE_OBJECT);
    }

    @JsonProperty("0")
    private Map<String, String> falseValue = SchemaConstants.SCHEMA_DESCRIBE_STRING;

    @JsonProperty("1")
    private Map<String, String> trueValue = SchemaConstants.SCHEMA_DESCRIBE_STRING;

    public Map<String, String> getFalseValue() {
        return falseValue;
    }

    public void setFalseValue(Map<String, String> falseValue) {
        this.falseValue = falseValue;
    }

    public Map<String, String> getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(Map<String, String> trueValue) {
        this.trueValue = trueValue;
    }
}
