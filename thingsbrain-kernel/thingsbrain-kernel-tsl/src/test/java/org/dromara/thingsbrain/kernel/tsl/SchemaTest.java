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

package org.dromara.thingsbrain.kernel.tsl;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.core.io.file.FileUtil;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.thingsbrain.kernel.tsl.entity.TslEvents;
import org.dromara.thingsbrain.kernel.tsl.entity.TslProperties;
import org.dromara.thingsbrain.kernel.tsl.entity.TslServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Scheme 数据反序列化测试 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/3 15:24
 */
public class SchemaTest {

    @BeforeEach
    public void setup() throws Exception {

    }

    @Test
    void tslPropertyDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/property-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "读取 JSON property-with-struct.json 错误");

        TslProperties property = JacksonUtils.toObject(json, TslProperties.class);
        Assertions.assertTrue(BeanUtil.isNotEmpty(property), "反序列化 TSL Property 数据错误");
    }

    @Test
    void tslEventDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/event-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "读取 JSON event-with-struct.json 错误");

        TslEvents event = JacksonUtils.toObject(json, TslEvents.class);
        Assertions.assertTrue(BeanUtil.isNotEmpty(event), "反序列化 TSL Event 数据错误");
    }

    @Test
    void tslServiceDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/service-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "读取 JSON service-with-struct.json 错误");

        TslServices services = JacksonUtils.toObject(json, TslServices.class);
        Assertions.assertTrue(BeanUtil.isNotEmpty(services), "反序列化 TSL Service 数据错误");
    }

    @Test
    void tslSchemaDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/full-with-struct.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "读取 JSON full-with-struct.json 错误");

        Specification services = JacksonUtils.toObject(json, Specification.class);
        Assertions.assertTrue(BeanUtil.isNotEmpty(services), "反序列化 TSL Schema 数据错误");
    }
}
