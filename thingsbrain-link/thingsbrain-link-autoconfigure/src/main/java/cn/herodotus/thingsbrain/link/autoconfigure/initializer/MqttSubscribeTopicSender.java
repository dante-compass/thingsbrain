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

package cn.herodotus.thingsbrain.link.autoconfigure.initializer;

import cn.herodotus.thingsbrain.link.commons.definition.MqttAuthorizationManager;
import cn.herodotus.thingsbrain.persistence.commons.domain.MqttCategory;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Optional;

/**
 * <p>Description: 系统 Mqtt 订阅主题发送器 </p>
 * <p>
 * 在启动正常启动之后，获取到平台对应的必要的订阅主题，以 Event 的方式将订阅主题信息发送到 infrastructure-module-mqtt 模块中动态添加以实现主题的订阅
 * <p>
 * 之所以要使用这种方式的原因：
 * 1. 实现系统订阅主题的动态管理，如果采用配置文件的方式配置，无法动态进行修改和管理
 * 2. 从模块依赖关系角度考虑，infrastructure-module-mqtt 模块并没有依赖 assemble 相关模块，相反 infrastructure-core 被 assemble-core 依赖，导致无法调用和数据层相关的代码
 * 3. 从模块解耦的角度考虑，infrastructure-module-mqtt 相对独立，仅有一个获取订阅主题的特殊需求，如果直接依赖数据层相关的模块，徒增耦合性无法提升独立性。
 * <p>
 * 所以采取 Event 的方式，系统启动后，读取订阅数据，然后发送到 infrastructure-module-mqtt 模块中。
 * <p>
 * 目前，还不支持数据库表变化后，同步变化。后续看功能需要，适时添加。
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 13:03
 */
public class MqttSubscribeTopicSender implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(MqttSubscribeTopicSender.class);

    private final MqttAuthorizationManager mqttAuthorizationManager;

    public MqttSubscribeTopicSender(MqttAuthorizationManager mqttAuthorizationManager) {
        this.mqttAuthorizationManager = mqttAuthorizationManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<MqttCategory> optional = mqttAuthorizationManager.findSubscribeCategoryForPlatform();

        optional.ifPresent(mqttCategory -> {
            if (CollectionUtils.isNotEmpty(mqttCategory.getAuthorities())) {
                log.info("[ThingsBrain] |- Found [{}] subscribe topics.", mqttCategory.getAuthorities().size());
//                ServiceContextHolder.publishEvent(new MqttSubscribeTopicAppenderEvent(mqttCategory.getAuthorities()));
            }
        });
    }
}
