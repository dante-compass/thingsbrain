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

package org.dromara.thingsbrain.kernel.link.domain.specification;

import cn.hutool.v7.core.io.file.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.thingsbrain.kernel.link.definition.LinkSysRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import tools.jackson.core.type.TypeReference;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: 网关批量上报数据请求实体测试 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/1 22:21
 */
public class EventPropertyPackPostParamTest {

    @BeforeEach
    public void setup() throws Exception {

    }

    @Test
    void testDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/specification/thing-event-property-pack-post.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "测试代码无法读取 thing-event-property-pack-post.json 文件");

        LinkSysRequest<EventPropertyPackPost> request = JacksonUtils.toObject(json, new TypeReference<>() {
        });

        Assertions.assertTrue(ObjectUtils.isNotEmpty(request) && ObjectUtils.isNotEmpty(request.getParams()) && CollectionUtils.isNotEmpty(request.getParams().getSubDevices()), "PostPackPropertyRequest 反序列化出错");
    }
}
