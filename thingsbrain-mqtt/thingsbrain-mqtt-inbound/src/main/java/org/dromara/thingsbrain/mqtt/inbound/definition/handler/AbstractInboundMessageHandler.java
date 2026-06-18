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

package org.dromara.thingsbrain.mqtt.inbound.definition.handler;

import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import tools.jackson.core.type.TypeReference;

/**
 * <p>Description: 入站消息处理器抽象定义 </p>
 *
 * @param <R> 入站请求数据反序列化类型
 * @author : gengwei.zheng
 * @date : 2025/10/20 21:45
 */
public abstract class AbstractInboundMessageHandler<R> {

    private final MqttTopic mqttTopic;

    protected AbstractInboundMessageHandler(MqttTopic mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    /**
     * 获取到处理器对应的主题对象 {@link MqttTopic}
     *
     * @return 主题对象 {@link MqttTopic}
     */
    protected MqttTopic getMqttTopic() {
        return mqttTopic;
    }

    /**
     * 从主题中获取到身份识别信息。
     *
     * @param topic Mqtt 主题
     * @return 身份信息 {@link CompleteIdentifier}
     */
    protected CompleteIdentifier getCompleteIdentifier(String topic) {
        return CompleteIdentifier.of(getMqttTopic().getTemplate(), topic).build();
    }

    /**
     * 获取数据反序列化类型定义
     * <p>
     * 设计该抽象方法，然后由子类定义数据反序列化的 {@link TypeReference}，是因为 Jackson 在处理反序列化时存在泛型擦除的问题。具体原因如下：
     * <p>
     * 例如：
     * <pre>
     *     LinkSysRequest<List<Attribute>> request = JacksonUtils.toObject(json, new TypeReference<>() {});
     * </pre>
     * 上面代码，放到测试类中，或者直接使用时没有任何问题，request 对象各个层次反序列化都是正常的。
     * <p>
     * 如果以泛型的方式，放入到抽象类中（早期本代码中就是使用这种方式），例如：
     * <pre>
     *      LinkSysRequest<T> request = JacksonUtils.toObject(json, new TypeReference<>() {});
     * </pre>
     * 虽然，在抽象类中可以指定类型，同样是反序列化 LinkSysRequest<List<Attribute>> ，因为泛型擦除的问题，会导致反序列化的结果中，Attribute 无法反序列化，结果变为 Map。
     * <p>
     * 所以在这里，设计了本抽象方法。通过在子类中，明确指定 TypeReference 来解决泛型擦除问题。
     *
     * @return 类型定义 {@link TypeReference}
     */
    protected abstract TypeReference<R> getTypeReference();
}
