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

package cn.herodotus.thingsbrain.mqtt.outbound.service;

import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.link.domain.subset.TopoChange;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import cn.herodotus.dante.security.domain.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 管理拓扑关系 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/16 12:36
 */
@Service
public class SubsetTopoService {

    private static final MqttTopic TOPIC_ADD_NOTIFY = new MqttTopic(MethodConstants.METHOD__THING_TOPO_ADD_NOTIFY);
    private static final MqttTopic TOPIC_CHANGE = new MqttTopic(MethodConstants.METHOD__THING_TOPO_CHANGE, false);

    private final MqttMessagePublisher mqttMessagePublisher;

    public SubsetTopoService(MqttMessagePublisher mqttMessagePublisher) {
        this.mqttMessagePublisher = mqttMessagePublisher;
    }

    /**
     * 设备接收订阅云端推送日志配置
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param param      配置信息 {@link List}
     */
    public void addNotify(String productKey, String deviceName, List<Identifier> param, UserPrincipal userPrincipal) {
        mqttMessagePublisher.request(TOPIC_ADD_NOTIFY, productKey, deviceName, param, userPrincipal);
    }

    /**
     * 设备接收订阅云端推送日志配置
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param param      配置信息 {@link TopoChange}
     */
    public void change(String productKey, String deviceName, TopoChange param, UserPrincipal userPrincipal) {
        mqttMessagePublisher.request(TOPIC_CHANGE, productKey, deviceName, param, userPrincipal);
    }
}
