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
import cn.herodotus.thingsbrain.kernel.link.domain.job.JobNotify;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttTopic;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 设备任务服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 16:37
 */
@Service
public class MqttDeviceJobService {

    private static final MqttTopic TOPIC_JOB_NOTIFY = new MqttTopic(MethodConstants.METHOD__THING_JOB_NOTIFY, false);

    private final MqttOutboundMessagePublisher mqttOutboundMessagePublisher;

    public MqttDeviceJobService(MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {
        this.mqttOutboundMessagePublisher = mqttOutboundMessagePublisher;
    }

    /**
     * 设备接收订阅云端推送日志配置
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param data       配置信息 {@link JobNotify}
     */
    public void notify(String productKey, String deviceName, JobNotify data) {
        mqttOutboundMessagePublisher.request(TOPIC_JOB_NOTIFY, productKey, deviceName, data);
    }
}
