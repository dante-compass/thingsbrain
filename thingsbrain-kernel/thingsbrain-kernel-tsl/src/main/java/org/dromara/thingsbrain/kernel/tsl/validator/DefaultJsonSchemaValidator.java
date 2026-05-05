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

package org.dromara.thingsbrain.kernel.tsl.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.networknt.schema.Error;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.dialect.Dialect;
import com.networknt.schema.dialect.Dialects;
import com.networknt.schema.keyword.NonValidationKeyword;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.thingsbrain.kernel.commons.definition.JsonSchemaValidator;
import org.dromara.thingsbrain.kernel.commons.domain.JsonSchemaError;
import org.dromara.thingsbrain.kernel.commons.domain.ValidationResult;
import org.dromara.thingsbrain.kernel.tsl.converter.ValidationMessageToErrorConverter;
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
        Schema jsonSchema = schemaRegistry.getSchema(schema);
        List<Error> messages = jsonSchema.validate(data);

        ValidationResult result = new ValidationResult();
        if (CollectionUtils.isEmpty(messages)) {
            result.setValid(true);
        } else {
            List<JsonSchemaError> errors = toError.convert(messages);
            result.setValid(false);
            result.setErrors(errors);
        }

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
