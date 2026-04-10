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

import java.util.Map;

/**
 * <p>Description: Enum 类型 JSON Schema 描述 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/4 21:01
 */
public class EnumDescribe extends AbstractDescribe {

    public EnumDescribe() {
        setType(SchemaConstants.SCHEMA_TYPE_OBJECT);
    }

    private Map<String, Map<String, String>> patternProperties = SchemaConstants.SCHEMA_DESCRIBE_ENUM;

    private Boolean additionalProperties = false;

    public Map<String, Map<String, String>> getPatternProperties() {
        return patternProperties;
    }

    public void setPatternProperties(Map<String, Map<String, String>> patternProperties) {
        this.patternProperties = patternProperties;
    }

    public Boolean getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
