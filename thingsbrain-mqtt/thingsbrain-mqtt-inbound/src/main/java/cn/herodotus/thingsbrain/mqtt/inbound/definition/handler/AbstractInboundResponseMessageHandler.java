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

package cn.herodotus.thingsbrain.mqtt.inbound.definition.handler;

import cn.herodotus.dante.core.function.ThrowableBiFunction;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkResponse;

/**
 * <p>Description: 支持回复的入站消息处理器抽象定义 </p>
 *
 * @param <I> 入站请求业务数据类型
 * @param <O> 出站响应结果数据类型
 * @param <M> 对应业务处理 Manager
 * @param <R> 入站请求数据反序列化类型
 * @author : gengwei.zheng
 * @date : 2025/10/20 21:45
 */
public abstract class AbstractInboundResponseMessageHandler<I, O, M, R> extends AbstractInboundMessageHandler<R> {

    protected AbstractInboundResponseMessageHandler(MqttTopic mqttTopic) {
        super(mqttTopic);
    }

    /**
     * 获取处理器业务逻辑处理定义
     *
     * @param messageManager 逻辑处理管理器
     * @return 业务逻辑处理定义
     */
    protected abstract ThrowableBiFunction<CompleteIdentifier, I, O, InboundMessageProcessingException> getFunction(M messageManager);

    /**
     * 操作成功响应
     *
     * @param id          消息 ID
     * @param data        错误数据
     * @param isNeedReply 是否需要回复
     * @return 响应对象 {@link LinkResponse}。可以为 null，null 表示不需要发送响应信息
     */
    protected LinkResponse<?> success(String id, O data, boolean isNeedReply) {
        if (isNeedReply) {
            return LinkResponse.success(id, data);
        }

        return null;
    }

    /**
     * 操作成功响应
     *
     * @param id   消息 ID
     * @param data 错误数据
     * @return 响应对象 {@link LinkResponse}。可以为 null，null 表示不需要发送响应信息
     */
    protected LinkResponse<?> success(String id, O data) {
        return success(id, data, true);
    }

    /**
     * 操作失败响应
     *
     * @param id          消息 ID
     * @param isNeedReply 是否需要回复
     * @return 响应对象 {@link LinkResponse}。可以为 null，null 表示不需要发送响应信息
     */
    protected LinkResponse<?> internalServerError(String id, boolean isNeedReply) {
        if (isNeedReply) {
            return LinkResponse.internalServerError(id, null);
        }
        return null;
    }

    /**
     * 操作失败响应
     *
     * @param id 消息 ID
     * @return 响应对象 {@link LinkResponse}。可以为 null，null 表示不需要发送响应信息
     */
    protected LinkResponse<?> internalServerError(String id) {
        return internalServerError(id, true);
    }
}
