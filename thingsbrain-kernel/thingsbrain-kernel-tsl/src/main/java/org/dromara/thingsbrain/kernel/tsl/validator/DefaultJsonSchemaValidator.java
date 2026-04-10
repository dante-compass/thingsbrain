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
import org.dromara.thingsbrain.kernel.commons.domain.JsonSchemaError;
import org.dromara.thingsbrain.kernel.commons.domain.ValidationResult;
import org.dromara.thingsbrain.kernel.tsl.converter.ValidationMessageToErrorConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.networknt.schema.Error;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.dialect.Dialect;
import com.networknt.schema.dialect.Dialects;
import com.networknt.schema.keyword.NonValidationKeyword;
import org.springframework.core.convert.converter.Converter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Json Schema 校验器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/5 15:55
 */
public class DefaultJsonSchemaValidator implements JsonSchemaValidator {

    private final ObjectMapper objectMapper;
    private final SchemaRegistry schemaRegistry;
    private final Converter<List<Error>, List<JsonSchemaError>> toError;

    public DefaultJsonSchemaValidator() {
        this.objectMapper = initObjectMapper();
        this.schemaRegistry = initSchemaRegistry();
        this.toError = new ValidationMessageToErrorConverter();
    }

    private ObjectMapper initObjectMapper() {
        // Schema 校验过程生成的数据以及 Schema 不需要存在空值属性
        // 所以不用系统统一的 ObjectMapper，单独生成一个去除空值属性。
        return JsonMapper.builder()
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
                .build();
    }

    private SchemaRegistry initSchemaRegistry() {
        // 数字型属性，例如: "0"，不符合 Schema 规范，校验时会出现告警。
        // 增加自定义 Keyword，避免生成警告。
        List<NonValidationKeyword> keywords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            keywords.add(new NonValidationKeyword(String.valueOf(i)));
        }

        Dialect dialect = Dialect.builder(Dialects.getDraft202012())
                .keywords(keywords)
                .build();

        return SchemaRegistry.withDialect(dialect);
    }

    @Override
    public ValidationResult validate(JsonNode data, JsonNode schema) {
//        Schema jsonSchema = schemaRegistry.getSchema(schema);
//        List<Error> messages = jsonSchema.validate(data);

        ValidationResult result = new ValidationResult();
//        if (CollectionUtils.isEmpty(messages)) {
//            result.setValid(true);
//        } else {
//            List<JsonSchemaError> errors = toError.convert(messages);
//            result.setValid(false);
//            result.setErrors(errors);
//        }

        return result;
    }

    @Override
    public ValidationResult validate(Map<String, Object> data, JsonNode schema) {
        JsonNode jsonNode = objectMapper.valueToTree(data);
        return validate(jsonNode, schema);
    }

    @Override
    public ValidationResult validate(Map<String, Object> data, String schema) {
        JsonNode jsonNode = objectMapper.valueToTree(schema);
        return validate(data, jsonNode);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
