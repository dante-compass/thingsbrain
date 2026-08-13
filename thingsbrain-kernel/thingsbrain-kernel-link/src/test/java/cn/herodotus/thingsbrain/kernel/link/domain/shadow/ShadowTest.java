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

package cn.herodotus.thingsbrain.kernel.link.domain.shadow;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.hutool.v7.core.io.file.FileUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>Description: {@link Shadow} 测试类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 16:30
 */
public class ShadowTest {

    Shadow shadow;

    @BeforeEach
    public void setup() throws Exception {
        File file = ResourceUtils.getFile("classpath:json/shadow/default-shadow.json");
        String json = FileUtil.readString(file, StandardCharsets.UTF_8);

        Assertions.assertNotNull(json, "测试代码无法读取 default-shadow.json 文件");
        shadow = JacksonUtils.toObject(json, Shadow.class);

        Assertions.assertTrue(ObjectUtils.isNotEmpty(shadow), "Shadow 反序列化出错");
    }

    @Test
    void testUpdateWhenVersionLessThanShadowVersionThenNotUpdate() throws Exception {
        Integer version = 1;

        ShadowRequest request = ShadowRequest.update(version)
                .desired(Map.of("temperature", 25))
                .build();

        Shadow newShadow = shadow.update(request.getUpdateState(), request.getVersion());
        Assertions.assertNotEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertNull(newShadow.getState().getDesired().get("temperature"), "未按预期设置 State 值错误");
        Assertions.assertNull(newShadow.getMetadata().getDesired().get("temperature"), "未按预期设置 Metadata 值错误");
    }

    @Test
    void testUpdateWhenVersionEqualMinusOneThenResetShadow() throws Exception {
        Integer version = -1;

        ShadowRequest request = ShadowRequest.update(version)
                .build();

        Shadow newShadow = shadow.update(request.getUpdateState(), request.getVersion());
        Assertions.assertEquals(0, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(MapUtils.isEmpty(newShadow.getState()), "未按预期设置 State 值错误");
        Assertions.assertTrue(MapUtils.isEmpty(newShadow.getMetadata()), "未按预期设置 Metadata 值错误");
    }


    @Test
    void testUpdateWhenFromPlatformThenAddDesired() throws Exception {
        Integer version = 3;

        ShadowRequest request = ShadowRequest.update(version)
                .desired(Map.of("temperature", 25))
                .build();

        Shadow newShadow = shadow.update(request.getUpdateState(), request.getVersion());
        Assertions.assertEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(newShadow.getState().getDesired().containsKey("temperature"), "未按预期设置 State 值错误");
        Assertions.assertTrue(newShadow.getMetadata().getDesired().containsKey("temperature"), "未按预期设置 Metadata 值错误");
    }

    @Test
    void testUpdateWhenFromDeviceThenAddReported() throws Exception {
        Integer version = 3;

        ShadowRequest request = ShadowRequest.update(version)
                .reported(Map.of("temperature", 25))
                .build();

        Shadow newShadow = shadow.update(request.getUpdateState(), request.getVersion());
        Assertions.assertEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(newShadow.getState().getReported().containsKey("temperature"), "未按预期设置 State 值错误");
        Assertions.assertTrue(newShadow.getMetadata().getReported().containsKey("temperature"), "未按预期设置 Metadata 值错误");
    }

    @Test
    void testDeleteWhenFromDeviceThenDeleteReported() throws Exception {
        Integer version = 2;

        ShadowRequest request = ShadowRequest.delete(version)
                .reported("color")
                .build();

        Shadow newShadow = shadow.delete(request.getDeleteState(), request.getVersion());
        Assertions.assertEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(newShadow.getState().isReportedEmpty(), "未按预期设置 State 值错误");
        Assertions.assertTrue(newShadow.getMetadata().isReportedEmpty(), "未按预期设置 Metadata 值错误");
    }

    @Test
    void testClearWhenFromPlatformThenClearDesired() throws Exception {
        Integer version = 2;

        ShadowRequest request = ShadowRequest.deleteAll(version)
                .desired()
                .build();

        Shadow newShadow = shadow.clear(request.getDeleteAllState(), request.getVersion());
        Assertions.assertEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(newShadow.getState().isDesiredEmpty(), "未按预期设置 State 值错误");
        Assertions.assertTrue(newShadow.getMetadata().isDesiredEmpty(), "未按预期设置 Metadata 值错误");
    }

    @Test
    void testClearWhenFromDeviceThenClearReported() throws Exception {
        Integer version = 2;

        ShadowRequest request = ShadowRequest.deleteAll(version)
                .reported()
                .build();

        Shadow newShadow = shadow.clear(request.getDeleteAllState(), request.getVersion());
        Assertions.assertEquals(version, newShadow.getVersion(), "版本号限制未生效错误");
        Assertions.assertTrue(newShadow.getState().isReportedEmpty(), "未按预期设置 State 值错误");
        Assertions.assertTrue(newShadow.getMetadata().isReportedEmpty(), "未按预期设置 Metadata 值错误");
    }
}
