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

import cn.hutool.v7.core.io.file.FileUtil;
import org.apache.commons.lang3.Strings;
import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.commons.domain.ValidationResult;
import cn.herodotus.thingsbrain.kernel.tsl.definition.Argument;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.kernel.tsl.entity.TslServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 物模型参数校验测试 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/16 16:37
 */
public class ArgumentValidatorTest {

    ArgumentValidator argumentValidator;
    TslServices services;

    @BeforeEach
    void setup() throws Exception {
        argumentValidator = new ArgumentValidator();

        File file = ResourceUtils.getFile("classpath:json/service-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "读取 JSON service-with-struct.json 错误");
        services = JacksonUtils.toObject(json, TslServices.class);
    }

    @Test
    void singleArgumentValidate() throws Exception {

        Optional<ServiceDimension> dimension = services.getServices().stream()
                .filter(item -> Strings.CS.equals(item.getIdentifier(), "upgrade"))
                .findFirst();
        Assertions.assertTrue(dimension.isPresent(), "无法从物模型中找到标识符为 upgrade 的 Service 定义");

        Argument argument = dimension.map(ServiceDimension::getInputData)
                .flatMap(inputs -> inputs.stream().filter(i -> Strings.CS.equals(i.getIdentifier(), "model")).findFirst())
                .orElse(null);
        Assertions.assertNotNull(argument, "无法从物模型中找到标识符为 model 的 Argument 定义");

        ValidationResult result = argumentValidator.validate("modell", 150, argument);
        Assertions.assertFalse(result.getValid(), "物模型属性校验错误");
    }

    @Test
    void singleStructArgumentValidate() throws Exception {
        Optional<ServiceDimension> dimension = services.getServices().stream()
                .filter(item -> Strings.CS.equals(item.getIdentifier(), "get"))
                .findFirst();
        Assertions.assertTrue(dimension.isPresent(), "无法从物模型中找到标识符为 get 的 Service 定义");

        Argument argument = dimension.map(ServiceDimension::getOutputData)
                .flatMap(inputs -> inputs.stream().filter(i -> Strings.CS.equals(i.getIdentifier(), "fan_struct_property")).findFirst())
                .orElse(null);
        Assertions.assertNotNull(argument, "无法从物模型中找到标识符为 fan_struct_property 的 Argument 定义");

        Map<String, Object> child = new HashMap<>();
        child.put("fan_struct_property_double_child", 21.0);
        child.put("fan_struct_property_float_child", 5.0);
        child.put("fan_struct_property_int_child", 20);
        child.put("fan_struct_property_text_child", "bbbb");
        child.put("fan_struct_property_date_child", "222222");
        child.put("fan_struct_property_enum_child", Map.of("0", "未启动", "1", "已启动"));
        child.put("fan_struct_property_boolean_child", Map.of("0", "关闭"));

        Map<String, Object> root = new HashMap<>();
        root.put("fan_struct_property", child);

        ValidationResult result = argumentValidator.validate(root, argument);
        Assertions.assertNull(result.getErrors(), "JSON Schema 校验出错");
    }

    @Test
    void fullArgumentValidate() throws Exception {

        ServiceDimension dimension = services.getServices().stream()
                .filter(item -> Strings.CS.equals(item.getIdentifier(), "get"))
                .findFirst().get();

        Assertions.assertNotNull(dimension, "无法从物模型中找到标识符为 get 的 Service 定义");

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

        ValidationResult result = argumentValidator.validate(root, dimension.getOutputData());
        result.getErrors().forEach(System.out::println);
        Assertions.assertEquals(2, result.getErrors().size(), "JSON Schema 校验出错");
    }
}
