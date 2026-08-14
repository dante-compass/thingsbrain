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

package cn.herodotus.thingsbrain.mqtt.commons.domain;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.constant.SystemConstants;
import cn.herodotus.dante.core.utils.StringTemplateUtils;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.mqtt.commons.enums.TopicCategory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.Map;

/**
 * <p>Description: 物联网主题定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/13 12:36
 */
public class MqttTopic {

    public enum Parameter {
        COMMON, EVENT, SERVICE;
    }

    /**
     * 协议中 Method 值，使用该值映射具体操作
     */
    private final String method;
    /**
     * 带有 ${} 占位符的主题定义
     */
    private final String template;
    /**
     * 带有 ${} 占位符的响应主题定义。相比 template 多了 “_reply”
     */
    private final String replyTemplate;
    /**
     * 是否支持回复
     */
    private final boolean supportReply;
    /**
     * 系统主题参数类型
     */
    private final Parameter parameter;

    /**
     * 构造方法，主要用于构造 Sys 相关相关主题
     *
     * @param method 对应方法。Shadow 相关主题没有该参数。
     */
    public MqttTopic(String method) {
        this(method, true);
    }

    /**
     * 构造方法，主要用于构造 Sys 相关相关主题
     *
     * @param method       对应方法。Shadow 相关主题没有该参数。
     * @param supportReply 值为 true 在响应主题结尾添加 _reply。响应主题不是以 _reply 结尾，那么设置为 false。
     */
    public MqttTopic(String method, boolean supportReply) {
        this(TopicCategory.SYS, method, supportReply);
    }

    /**
     * 构造方法。主要用于构造物模型相关相关主题
     *
     * @param method    对应方法。Shadow 相关主题没有该参数。
     * @param parameter 用于物模型相关主题的区分。
     */
    public MqttTopic(String method, Parameter parameter) {
        this(method, true, parameter);
    }

    /**
     * 构造方法。主要用于构造物模型相关相关主题
     *
     * @param method       对应方法。Shadow 相关主题没有该参数。
     * @param supportReply 值为 true 在响应主题结尾添加 _reply。响应主题不是以 _reply 结尾，那么设置为 false。
     * @param parameter    用于物模型相关主题的区分。
     */
    public MqttTopic(String method, boolean supportReply, Parameter parameter) {
        this(TopicCategory.SYS, method, supportReply, parameter, null, null);
    }


    /**
     * 构造方法。主要用于构造设备影子主题。
     *
     * @param template      带有 ${} 占位符的主题模版定义
     * @param replyTemplate 带有 ${} 占位符的响应主题模版定义
     */
    public MqttTopic(String template, String replyTemplate) {
        this(TopicCategory.SHADOW, null, true, Parameter.COMMON, template, replyTemplate);
    }

    /**
     * 构造方法。
     *
     * @param classify 主题类别 {@link TopicCategory}
     * @param method   对应方法。Shadow 相关主题没有该参数。
     */
    public MqttTopic(TopicCategory classify, String method) {
        this(classify, method, true);
    }

    /**
     * 构造方法。
     *
     * @param classify     主题类别 {@link TopicCategory}
     * @param method       对应方法。Shadow 相关主题没有该参数。
     * @param supportReply 值为 true 在响应主题结尾添加 _reply。响应主题不是以 _reply 结尾，那么设置为 false。
     */
    public MqttTopic(TopicCategory classify, String method, boolean supportReply) {
        this(classify, method, supportReply, Parameter.COMMON, null, null);
    }

    /**
     * 构造方法。
     *
     * @param category      主题类别 {@link TopicCategory}
     * @param method        对应方法。Shadow 相关主题没有该参数。
     * @param supportReply  值为 true 在响应主题结尾添加 _reply。响应主题不是以 _reply 结尾，那么设置为 false。
     * @param parameter     用于物模型相关主题的区分。
     * @param template      带有 ${} 占位符的主题模版定义
     * @param replyTemplate 带有 ${} 占位符的响应主题模版定义
     */
    public MqttTopic(TopicCategory category, String method, boolean supportReply, Parameter parameter, String template, String replyTemplate) {
        this.method = method;
        this.parameter = parameter;
        this.supportReply = supportReply;
        this.template = createTemplate(template, category);
        this.replyTemplate = createReplyTemplate(replyTemplate);
    }

    /**
     * 生成 Ota 主题
     *
     * @param operation Method 转换后的操作
     * @return Ota 主题模板
     */
    private String createOtaTopicTemplate(String operation) {
        // 生成主体格式：ota/xx/xx${productKey}/${deviceName}
        return TopicCategory.OTA.getValue() + operation + ProtocolConstants.FORMAT_LEVEL__COMMON;
    }

    /**
     * 生成 Ext主题模板
     *
     * @param operation Method 转换后的操作
     * @return Ext 主题模板
     */
    private String createExtTopicTemplate(String operation) {
        // 生成主体格式：ext/session/${productKey}/${deviceName}/xx/xx
        // 如果是 reply 类型主题，则生成 ext/session/${productKey}/${deviceName}/xx/xx_reply
        return TopicCategory.EXT.getValue() + "/session" + ProtocolConstants.FORMAT_LEVEL__COMMON + operation;
    }

    /**
     * 生成 Sys 主题模板
     *
     * @param operation Method 转换后的操作
     * @param parameter 用于物模型相关主题的区分
     * @return Sys 主题模板
     */
    private String createSysTopicTemplate(String operation, Parameter parameter) {
        // 生成主体格式：sys/${productKey}/${deviceName}/xx/xx/xx
        String main = TopicCategory.SYS.getValue() + ProtocolConstants.FORMAT_LEVEL__COMMON + operation;
        return switch (parameter) {
            // 如果是 reply 类型主题，则生成 sys/${productKey}/${deviceName}/thing/event/${tsl.event.identifier}/post_reply
            case EVENT -> main + ProtocolConstants.FORMAT_LEVEL__EVENT + ProtocolConstants.FORMAT_LEVEL__POST;
            // 如果是 reply 类型主题，则生成 sys/${productKey}/${deviceName}/thing/service/${tsl.service.identifier}
            // 阿里云的标准是 sys/${productKey}/${deviceName}/thing/service/${tsl.service.identifier}_reply 这不利于当前配置订阅
            case SERVICE -> main + ProtocolConstants.FORMAT_LEVEL__SERVICE;
            // 如果是 reply 类型主题，则生成 sys/${productKey}/${deviceName}/xx/xx/xx_reply
            default -> main;
        };
    }

    /**
     * 生成主题模板
     *
     * @param category 主题类别 {@link TopicCategory}
     * @return 主题模板
     */
    private String createTopicTemplate(TopicCategory category) {
        String operation = SymbolConstants.FORWARD_SLASH + Strings.CS.replace(method, SymbolConstants.PERIOD, SymbolConstants.FORWARD_SLASH);
        return switch (category) {
            case OTA -> createOtaTopicTemplate(operation);
            case EXT -> createExtTopicTemplate(operation);
            default -> createSysTopicTemplate(operation, this.parameter);
        };
    }

    /**
     * 生成主题模板
     *
     * @param template 指定模板
     * @param category 主题类别 {@link TopicCategory}
     * @return 主题模板
     */
    private String createTemplate(String template, TopicCategory category) {
        if (StringUtils.isNotEmpty(template)) {
            // 类似于 shadow 主题，非常少就不用动态生成，直接指定即可
            return template;
        } else {
            return createTopicTemplate(category);
        }
    }

    private String createReplyTemplate(String template) {
        if (StringUtils.isNotEmpty(template)) {
            return template;
        } else {
            if (isSupportReply()) {
                if (this.parameter == Parameter.SERVICE) {
                    return this.template;
                }
                return this.template + ProtocolConstants.ACTION__REPLY;
            }

            return null;
        }
    }

    /**
     * 获取与主题对应的 Method。有些主题不会包含 Method。
     *
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * 获取涉及 identifier 的主题对应的 Method
     * ·自定义属性上报：thing.event.${tsl.event.identifier}.post
     * ·自定义服务调用：thing.service.${tsl.service.identifier}
     *
     * @param identifier 标识符
     * @return method
     */
    public String getMethod(String identifier) {
        return switch (parameter) {
            case EVENT ->
                    this.method + SymbolConstants.PERIOD + identifier + SymbolConstants.PERIOD + ProtocolConstants.ACTION__POST;
            case SERVICE -> this.method + SymbolConstants.PERIOD + identifier;
            default -> this.method;
        };
    }

    public boolean isSupportReply() {
        return supportReply;
    }

    /**
     * 获取与主题对应的、包含占位符的主题模版
     * 例如：sys/${productKey}/${deviceName}/thing/event/property/post
     *
     * @return 主题模版
     */
    public String getTemplate() {
        return template;
    }

    /**
     * 获取与主题对应的、包含占位符的响应主题模版
     * 例如：sys/${productKey}/${deviceName}/thing/event/property/post_reply
     *
     * @return 响应主题模版
     */
    public String getReplyTemplate() {
        return replyTemplate;
    }

    /**
     * 生成主题通用方法
     *
     * @param template   主题模板字符串
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param identifier 物模型 Service 和 Event 使用的标识符
     * @return 完整的主题
     */
    private String createTopic(String template, String productKey, String deviceName, String identifier) {
        if (StringUtils.isNotEmpty(identifier)) {
            if (this.parameter == Parameter.SERVICE) {
                return StringTemplateUtils.replace(template, Map.of(SystemConstants.KEY__PRODUCT_KEY, productKey, SystemConstants.KEY__DEVICE_NAME, deviceName, ProtocolConstants.VARIABLE__SERVICE_IDENTIFIER, identifier));
            } else {
                return StringTemplateUtils.replace(template, Map.of(SystemConstants.KEY__PRODUCT_KEY, productKey, SystemConstants.KEY__DEVICE_NAME, deviceName, ProtocolConstants.VARIABLE__EVENT_IDENTIFIER, identifier));
            }
        }

        return StringTemplateUtils.replace(template, Map.of(SystemConstants.KEY__PRODUCT_KEY, productKey, SystemConstants.KEY__DEVICE_NAME, deviceName));
    }

    /**
     * 获取根据参数拼装的完整主题
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param identifier 物模型 Service 和 Event 使用的标识符
     * @return 完整的主题
     */
    public String getTopic(String productKey, String deviceName, String identifier) {
        return createTopic(this.template, productKey, deviceName, identifier);
    }

    /**
     * 获取根据参数拼装的完整主题
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @return 完整的主题
     */
    public String getTopic(String productKey, String deviceName) {
        return getTopic(productKey, deviceName, null);
    }

    /**
     * 获取根据参数拼装的完整主题
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param identifier 物模型 Service 和 Event 使用的标识符
     * @return 完整的主题
     */
    public String getReplyTopic(String productKey, String deviceName, String identifier) {
        if (isSupportReply()) {
            return createTopic(this.replyTemplate, productKey, deviceName, identifier);
        }

        return null;
    }

    /**
     * 获取根据参数拼装的完整主题
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @return 完整的主题
     */
    public String getReplyTopic(String productKey, String deviceName) {
        return getReplyTopic(productKey, deviceName, null);
    }
}
