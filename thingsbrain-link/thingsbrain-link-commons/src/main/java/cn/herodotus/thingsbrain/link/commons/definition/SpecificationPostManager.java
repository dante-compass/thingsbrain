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

package cn.herodotus.thingsbrain.link.commons.definition;

import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.domain.specification.*;
import cn.herodotus.thingsbrain.link.commons.exception.DataStorageException;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 物模型上报数据管理器定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/9 17:04
 */
public interface SpecificationPostManager {

    /**
     * 获取存储管理器
     *
     * @return 处理器 {@link DataStorageHandler}
     */
    Optional<DataStorageHandler> getStorageHandler();

    /**
     * 保存设备上报属性数据
     *
     * @param productKey ProductKey
     * @param deviceName DeviceName
     * @param request    上报属性数据 {@link EventPropertyPost}
     * @return 返回数据 {@link Map}。无返回数据则返回空 Map
     * @throws InboundMessageProcessingException 入站消息处理异常
     */
    default Map<String, Object> property(String productKey, String deviceName, EventPropertyPost request) throws InboundMessageProcessingException {
        return getStorageHandler()
                .map(handler -> {
                    try {
                        return handler.property(productKey, deviceName, request);
                    } catch (DataStorageException e) {
                        return null;
                    }
                })
                .orElseThrow(() -> new InboundMessageProcessingException("保存设备上报属性数据发生未知异常"));
    }

    /**
     * 保存设备上报事件数据
     *
     * @param productKey ProductKey
     * @param deviceName DeviceName
     * @param identifier 物模型 Identifier
     * @param request    上报事件数据 {@link EventIdentifierPost}
     * @return 返回数据 {@link Map}。无返回数据则返回空 Map
     * @throws InboundMessageProcessingException 入站消息处理异常
     */
    default Map<String, Object> event(String productKey, String deviceName, String identifier, EventIdentifierPost request) throws InboundMessageProcessingException {
        return getStorageHandler()
                .map(handler -> {
                    try {
                        return handler.event(productKey, deviceName, identifier, request);
                    } catch (DataStorageException e) {
                        return null;
                    }
                })
                .orElseThrow(() -> new InboundMessageProcessingException("保存设备上报事件数据发生未知异常"));
    }

    /**
     * 保存网关批量上报数据
     *
     * @param productKey ProductKey
     * @param deviceName DeviceName
     * @param request    上报数据 {@link EventPropertyBatchPost}
     * @return 返回数据 {@link Map}。无返回数据则返回空 Map
     * @throws InboundMessageProcessingException 入站消息处理异常
     */
    default Map<String, Object> batch(String productKey, String deviceName, EventPropertyBatchPost request) throws InboundMessageProcessingException {
        return getStorageHandler()
                .map(handler -> {
                    try {
                        return handler.batch(productKey, deviceName, request);
                    } catch (DataStorageException e) {
                        return null;
                    }
                })
                .orElseThrow(() -> new InboundMessageProcessingException("保存网关批量上报数据发生未知异常"));
    }

    /**
     * 保存物模型历史数据上报数据
     *
     * @param productKey ProductKey
     * @param deviceName DeviceName
     * @param request    上报数据 {@link EventPropertyHistoryPost}
     * @return 返回数据 {@link Map}。无返回数据则返回空 Map
     * @throws InboundMessageProcessingException 入站消息处理异常
     */
    default Map<String, Object> history(String productKey, String deviceName, EventPropertyHistoryPost request) throws InboundMessageProcessingException {
        return getStorageHandler()
                .map(handler -> {
                    try {
                        return handler.history(productKey, deviceName, request);
                    } catch (DataStorageException e) {
                        return null;
                    }
                })
                .orElseThrow(() -> new InboundMessageProcessingException("保存物模型历史数据上报数据发生未知异常"));
    }

    /**
     * 保存设备批量上报属性、事件上报数据
     *
     * @param productKey ProductKey
     * @param deviceName DeviceName
     * @param request    上报数据 {@link EventPropertyPackPost}
     * @return 返回数据 {@link Map}。无返回数据则返回空 Map
     * @throws InboundMessageProcessingException 入站消息处理异常
     */
    default Map<String, Object> pack(String productKey, String deviceName, EventPropertyPackPost request) throws InboundMessageProcessingException {
        return getStorageHandler()
                .map(handler -> {
                    try {
                        return handler.pack(productKey, deviceName, request);
                    } catch (DataStorageException e) {
                        return null;
                    }
                })
                .orElseThrow(() -> new InboundMessageProcessingException("保存设备批量上报属性、事件上报数据未知异常"));
    }
}
