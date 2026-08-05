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

package cn.herodotus.thingsbrain.kernel.tsl.validator;

import cn.herodotus.thingsbrain.kernel.commons.definition.JsonSchemaValidator;
import cn.herodotus.thingsbrain.kernel.commons.domain.SchemaValidationResult;
import cn.herodotus.thingsbrain.kernel.tsl.Schema;
import cn.herodotus.thingsbrain.kernel.tsl.definition.Argument;
import cn.herodotus.thingsbrain.kernel.tsl.enums.ArgumentType;
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
public class SchemaValidator {

    private static final Logger log = LoggerFactory.getLogger(SchemaValidator.class);

    private final JsonSchemaValidator jsonSchemaValidator;
    private final ObjectMapper objectMapper;

    public SchemaValidator() {
        this.jsonSchemaValidator = new DefaultJsonSchemaValidator();
        this.objectMapper = this.jsonSchemaValidator.getObjectMapper();
    }

    private JsonNode createSchemaNode(Schema<Argument> describe) {
        JsonNode jsonNode = objectMapper.valueToTree(describe);
        log.debug("[ThingsMesh] |- Schema is : [{}]", jsonNode.toString());
        return jsonNode;
    }

    /**
     * 根据多个物模型参数定义，使用 Json Schema 方式同时校验多条数据。
     *
     * @param arguments 物模型定义参数列表
     * @param data      待校验数据
     * @return Schema 校验结果 {@link SchemaValidationResult}
     */
    public SchemaValidationResult validate(List<Argument> arguments, Map<String, Object> data) {
        // 根据物模型中定义的参数，将其转换为 JsonSchema
        Schema<Argument> describe = new Schema<>(arguments.stream());
        JsonNode jsonNode = createSchemaNode(describe);
        return jsonSchemaValidator.validate(data, jsonNode);
    }

    /**
     * 根据单个物模型参数定义，使用 Json Schema 方式同时校验多条数据。支持 Struct 结构。
     *
     * @param argument 物模型定义参数
     * @param data     待校验数据
     * @return Schema 校验结果 {@link SchemaValidationResult}
     */
    public SchemaValidationResult validate(Argument argument, Map<String, Object> data) {
        return validate(Collections.singletonList(argument), data);
    }

    /**
     * 根据单个物模型参数定义，使用 Json Schema 方式同时校验单条数据。不支持 Struct 结构。
     *
     * @param argument   物模型定义参数
     * @param identifier 物模型参数标识符
     * @param value      待校验值
     * @return Schema 校验结果 {@link SchemaValidationResult}
     */
    public SchemaValidationResult validate(Argument argument, String identifier, Object value) {
        Assert.isTrue(argument.getDataType().getType() != ArgumentType.STRUCT, "Argument type must be struct for single property");
        return validate(argument, Map.of(identifier, value));
    }
}
