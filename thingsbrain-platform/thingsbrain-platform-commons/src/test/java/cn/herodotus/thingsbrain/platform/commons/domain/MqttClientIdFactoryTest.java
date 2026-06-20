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

package cn.herodotus.thingsbrain.platform.commons.domain;

import cn.herodotus.dante.core.enums.SignatureMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>Description: {@link MqttClientIdFactory} 测试类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/9 23:59
 */
public class MqttClientIdFactoryTest {

    @Test
    void testBuildBaseMqttClientId() throws Exception {
        MqttClientIdFactory mqttClientIdFactory = MqttClientIdFactory.with("aaa", "bbb")
                .secureMode(2)
                .signMethod(SignatureMethod.HMAC_MD5)
                .timestamp("1234")
                .build();

        System.out.println(mqttClientIdFactory.getId());

        Assertions.assertEquals("aaa.bbb|securemode=2,signmethod=hmacmd5,timestamp=1234|", mqttClientIdFactory.getId(), "生成 Mqtt Client Id 出错");
    }

    @Test
    void testParseBaseMqttClientId() throws Exception {
        String id = "aaa.bbb|securemode=2,signmethod=hmacmd5,timestamp=1234|";
        MqttClientIdFactory mqttClientIdFactory = MqttClientIdFactory.of(id).parse();

//        Assertions.assertEquals("aaa", mqttClientIdFactory.getProductKey(), "解析 Mqtt Client Id 时，productKey 出错");
//        Assertions.assertEquals("bbb", mqttClientIdFactory.getDeviceName(), "解析 Mqtt Client Id 时，deviceName 出错");
        Assertions.assertEquals(2, mqttClientIdFactory.getSecureMode(), "解析 Mqtt Client Id 时，secureMode 出错");
        Assertions.assertEquals(SignatureMethod.HMAC_MD5, mqttClientIdFactory.getSignMethod(), "解析 Mqtt Client Id 时，signMethod 出错");
        Assertions.assertEquals(1234L, mqttClientIdFactory.getTimestamp(), "解析 Mqtt Client Id 时，timestamp 出错");
    }
}
