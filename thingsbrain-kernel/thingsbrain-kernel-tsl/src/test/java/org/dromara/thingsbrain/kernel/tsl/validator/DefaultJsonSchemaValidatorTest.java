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

import cn.hutool.v7.core.io.file.FileUtil;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.thingsbrain.kernel.commons.domain.ValidationResult;
import org.dromara.thingsbrain.kernel.tsl.Schema;
import org.dromara.thingsbrain.kernel.tsl.domain.PropertyDimension;
import org.dromara.thingsbrain.kernel.tsl.entity.TslProperties;
import org.junit.jupiter.api.*;
import org.springframework.util.ResourceUtils;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Json Schema 校验测试 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/5 0:37
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DefaultJsonSchemaValidatorTest {

    DefaultJsonSchemaValidator defaultJsonSchemaValidator;
    Schema<PropertyDimension> describe;
    JsonNode schemaNode;

    @BeforeEach
    void setup() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/property-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        TslProperties properties = JacksonUtils.toObject(json, TslProperties.class);
        this.describe = new Schema<>(properties.getProperties().stream());

        this.defaultJsonSchemaValidator = new DefaultJsonSchemaValidator();
        ObjectMapper objectMapper = defaultJsonSchemaValidator.getObjectMapper();
        this.schemaNode = objectMapper.valueToTree(this.describe);
    }

    @Test
    @Order(1)
    void testCreateJsonSchema() throws Exception {
        System.out.println(schemaNode.toPrettyString());
        Assertions.assertNotNull(schemaNode, "生成 JSON Schema 错误");
    }

    @Test
    @Order(2)
    void testJsonSchemaValidateSuccess() throws Exception {

        Map<String, Object> child = new HashMap<>();
        child.put("fan_struct_property_double_child", 21.0);
        child.put("fan_struct_property_float_child", 5.0);
        child.put("fan_struct_property_int_child", 20);
        child.put("fan_struct_property_text_child", "bbbb");
        child.put("fan_struct_property_date_child", "222222");
        child.put("fan_struct_property_enum_child", Map.of("0", "未启动", "1", "已启动"));
        child.put("fan_struct_property_boolean_child", Map.of("0", "关闭"));

        Map<String, Object> root = new HashMap<>();
        root.put("batch_boolean_attr_id", Map.of("0", "关闭"));
        root.put("batch_enum_attr_id", Map.of("1", "启用"));
        root.put("fan_date_property", "2132424234");
        root.put("fan_doule_property", 50.0);
        root.put("fan_float_property", 40.0);
        root.put("fan_int_property", 10);
        root.put("fan_struct_property", child);
        root.put("fan_text_property", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidationResult result = defaultJsonSchemaValidator.validate(root, schemaNode);
        Assertions.assertTrue(result.getValid(), "JSON Schema 校验出错");
    }

    @Test
    @Order(3)
    void testJsonSchemaValidateFailed() throws Exception {
        Map<String, Object> child = new HashMap<>();
        child.put("fan_struct_property_double_child", 21.0);
        child.put("fan_struct_property_float_child", 5.0);
        child.put("fan_struct_property_int_child", 20);
//        child.put("fan_struct_property_text_child", "bbbb");
//        child.put("fan_struct_property_date_child", "222222");
        child.put("fan_struct_property_enum_child", Map.of("0", "未启动", "1", "已启动"));
        child.put("fan_struct_property_boolean_child", Map.of("0", "关闭"));

        Map<String, Object> root = new HashMap<>();
        root.put("batch_boolean_attr_id", Map.of("0", "关闭"));
        root.put("batch_enum_attr_id", Map.of("1", "启用"));
        root.put("fan_date_property", "2132424234");
        root.put("fan_doule_property", 50.0);
        root.put("fan_float_property", 40.0);
        root.put("fan_int_property", 10);
        root.put("fan_struct_property", child);
        root.put("fan_text_property", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ValidationResult result = defaultJsonSchemaValidator.validate(root, schemaNode);

        Assertions.assertEquals(2, result.getErrors().size(), "JSON Schema 校验出错");
    }
}
