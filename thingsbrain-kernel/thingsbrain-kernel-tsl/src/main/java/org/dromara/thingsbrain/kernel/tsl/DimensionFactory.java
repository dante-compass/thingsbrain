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

package org.dromara.thingsbrain.kernel.tsl;

import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.dante.core.utils.StringTemplateUtils;
import org.dromara.thingsbrain.kernel.commons.constant.MethodConstants;
import org.dromara.thingsbrain.kernel.commons.constant.ProtocolConstants;
import org.dromara.thingsbrain.kernel.tsl.domain.*;
import org.dromara.thingsbrain.kernel.tsl.enums.AccessMode;
import org.dromara.thingsbrain.kernel.tsl.enums.CallType;
import org.dromara.thingsbrain.kernel.tsl.enums.EventType;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 维度实体生成工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/3 0:39
 */
public class DimensionFactory {

    private static final String POST_PROPERTY_EVENT_METHOD_FORMAT = "things.event.${identifier}.post";
    private static final String INVOKE_SERVICE_METHOD_FORMAT = "things.service.${identifier}";

    private static String replace(String format, String key, String value) {
        return StringTemplateUtils.replace(format, Map.of(key, value));
    }

    private static String postEventMethod(String identifier) {
        return replace(MethodConstants.METHOD_FORMAT__POST_EVENT, ProtocolConstants.VARIABLE__EVENT_IDENTIFIER, identifier);
    }

    private static String invokeServiceMethod(String identifier) {
        return replace(MethodConstants.METHOD_FORMAT__INVOKE_SERVICE, ProtocolConstants.VARIABLE__SERVICE_IDENTIFIER, identifier);
    }

    /**
     * 将字符串类型的模型 Specs JSON 数据转换成物模型参数实体 {@link Argument}
     *
     * @param specs Specs JSON 数据
     * @return 物模型参数实体 {@link Argument}
     */
    public static Argument argument(String specs) {
        return JacksonUtils.toObject(specs, Argument.class);
    }

    /**
     * 生成 {@link PropertyDimension} 实体。
     *
     * @param name       功能名称
     * @param identifier 标识符
     * @param required   是否为必须
     * @param accessMode 读写类型 {@link AccessMode}
     * @param dataType   数据类型 {@link DataType}
     * @return 属性维度实体 {@link PropertyDimension}
     */
    public static PropertyDimension property(String name, String identifier, Boolean required, AccessMode accessMode, DataType dataType) {
        PropertyDimension domain = new PropertyDimension();
        domain.setName(name);
        domain.setIdentifier(identifier);
        domain.setRequired(required);
        domain.setAccessMode(accessMode);
        domain.setDataType(dataType);
        return domain;
    }

    /**
     * 生成 {@link EventDimension} 实体。
     *
     * @param name       功能名称
     * @param identifier 标识符
     * @param required   是否为必须
     * @param eventType  事件类型 {@link EventType}
     * @param desc       描述
     * @param method     方法
     * @param outputData 输出数据
     * @return 事件维度实体 {@link EventDimension}
     */
    public static EventDimension event(String name, String identifier, Boolean required, EventType eventType, String desc, String method, List<Argument> outputData) {
        EventDimension domain = new EventDimension();
        domain.setName(name);
        domain.setIdentifier(identifier);
        domain.setRequired(required);
        domain.setType(eventType);
        domain.setDesc(desc);
        domain.setMethod(method);
        domain.setOutputData(outputData);
        return domain;
    }

    /**
     * 生成 {@link EventDimension} 实体。
     *
     * @param name       功能名称
     * @param identifier 标识符
     * @param required   是否为必须
     * @param eventType  事件类型 {@link EventType}
     * @param desc       描述
     * @param outputData 输出数据
     * @return 事件维度实体 {@link EventDimension}
     */
    public static EventDimension event(String name, String identifier, Boolean required, EventType eventType, String desc, List<Argument> outputData) {
        return event(name, identifier, required, eventType, desc, postEventMethod(identifier), outputData);
    }

    /**
     * 生成默认“属性上报”事件维度实体 {@link EventDimension}
     *
     * @param outputData 输出数据
     * @return 事件维度实体 {@link EventDimension}
     */
    public static EventDimension post(List<Argument> outputData) {
        return event(ProtocolConstants.ACTION__POST, ProtocolConstants.ACTION__POST, true, EventType.INFO, "属性上报", MethodConstants.METHOD__THING_EVENT_PROPERTY_POST, outputData);
    }

    /**
     * 生成 {@link ServiceDimension} 实体。
     *
     * @param name       功能名称
     * @param identifier 标识符
     * @param required   是否为必须
     * @param callType   服务调用类型 {@link CallType}
     * @param desc       描述
     * @param method     方法
     * @param inputData  输入数据
     * @param outputData 输出数据
     * @return 服务维度实体 {@link ServiceDimension}
     */
    public static ServiceDimension service(String name, String identifier, Boolean required, CallType callType, String desc, String method, List<Argument> inputData, List<Argument> outputData) {
        ServiceDimension domain = new ServiceDimension();
        domain.setName(name);
        domain.setIdentifier(identifier);
        domain.setRequired(required);
        domain.setCallType(callType);
        domain.setDesc(desc);
        domain.setMethod(method);
        domain.setOutputData(outputData);
        domain.setInputData(inputData);
        return domain;
    }

    /**
     * 生成 {@link ServiceDimension} 实体。
     *
     * @param name       功能名称
     * @param identifier 标识符
     * @param required   是否为必须
     * @param callType   服务调用类型 {@link CallType}
     * @param desc       描述
     * @param inputData  输入数据
     * @param outputData 输出数据
     * @return 服务维度实体 {@link ServiceDimension}
     */
    public static ServiceDimension service(String name, String identifier, Boolean required, CallType callType, String desc, List<Argument> inputData, List<Argument> outputData) {
        return service(name, identifier, required, callType, desc, invokeServiceMethod(identifier), inputData, outputData);
    }

    /**
     * 成默认“属性设置”服务维度实体 {@link ServiceDimension}
     *
     * @param inputData 输入数据
     * @return 服务维度实体 {@link ServiceDimension}
     */
    public static ServiceDimension set(List<Argument> inputData) {
        return service(ProtocolConstants.ACTION__SET, ProtocolConstants.ACTION__SET, true, CallType.ASYNC, "属性设置", MethodConstants.METHOD__THING_SERVICE_PROPERTY_SET, inputData, List.of());
    }

    /**
     * 生成默认“属性获取”服务维度实体 {@link ServiceDimension}
     *
     * @param outputData 输出数据
     * @param inputData  输入数据
     * @return 服务维度实体 {@link ServiceDimension}
     */
    public static ServiceDimension get(List<Argument> inputData, List<Argument> outputData) {
        return service(ProtocolConstants.ACTION__GET, ProtocolConstants.ACTION__GET, true, CallType.ASYNC, "属性获取", MethodConstants.METHOD__THING_SERVICE_PROPERTY_GET, inputData, outputData);
    }
}
