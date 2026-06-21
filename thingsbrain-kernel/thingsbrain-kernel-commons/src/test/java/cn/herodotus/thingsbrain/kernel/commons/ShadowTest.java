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

package cn.herodotus.thingsbrain.kernel.commons;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.commons.domain.Shadow;
import cn.hutool.v7.core.io.file.FileUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: {@link Shadow} 测试类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 16:30
 */
public class ShadowTest {

    @Test
    void shadowDeserialization() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/default-shadow.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "测试代码无法读取 default-shadow.json 文件");

        Shadow shadow = JacksonUtils.toObject(json, Shadow.class);
        Assertions.assertTrue(ObjectUtils.isNotEmpty(shadow), "Shadow 反序列化出错");
    }
}
