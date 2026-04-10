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

package org.dromara.thingsbrain.kernel.tsl.validator;

import org.dromara.thingsbrain.kernel.commons.definition.JsonSchemaValidator;
import org.dromara.thingsbrain.kernel.commons.domain.ValidationResult;
import org.dromara.thingsbrain.kernel.tsl.Schema;
import org.dromara.thingsbrain.kernel.tsl.domain.Argument;
import org.dromara.thingsbrain.kernel.tsl.enums.ArgumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物模型参数校验 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/16 15:48
 */
public class ArgumentValidator {

    private static final Logger log = LoggerFactory.getLogger(ArgumentValidator.class);

    private final JsonSchemaValidator jsonSchemaValidator;
    private final ObjectMapper objectMapper;

    public ArgumentValidator() {
        this.jsonSchemaValidator = new DefaultJsonSchemaValidator();
        this.objectMapper = this.jsonSchemaValidator.getObjectMapper();
    }

    private JsonNode createSchemaNode(Schema<Argument> describe) {
        JsonNode jsonNode = objectMapper.valueToTree(describe);
        log.debug("[ThingsBrain] |- Schema is : [{}]", jsonNode.toString());
        return jsonNode;
    }

    public ValidationResult validate(Map<String, Object> data, List<Argument> arguments) {
        Schema<Argument> describe = new Schema<>(arguments.stream());
        JsonNode jsonNode = createSchemaNode(describe);
        return jsonSchemaValidator.validate(data, jsonNode);
    }

    public ValidationResult validate(Map<String, Object> data, Argument argument) {
        return validate(data, Collections.singletonList(argument));
    }

    public ValidationResult validate(String identifier, Object value, Argument argument) {
        Assert.isTrue(argument.getDataType().getType() != ArgumentType.STRUCT, "Argument type must be struct for single property");
        return validate(Map.of(identifier, value), argument);
    }
}
