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

package cn.herodotus.thingsbrain.kernel.commons.definition;

import cn.herodotus.thingsbrain.kernel.commons.domain.EmqxAuthenticationStatus;

/**
 * <p>Description: Emqx Http 方式客户端认证定义 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/27 13:10
 */
public interface EmqxAuthenticationHandler {

    /**
     * 执行 Emqx Http 方式客户端认证。
     * <p>
     * 如果返回的 HTTP 状态码为 200，认证结果通过 Body 中的 result 标识，Emqx Http 授权响应支持三种状态，可选值为：
     * · allow：允许发布或订阅
     * · deny：禁止发布或订阅
     * · ignore：忽略请求，移交下一个认证器以继续执行认证链。ignore 为 默认值
     * <p>
     * 进入该方法的前提是：设备尚未接电激活，Emqx 数据库认证方式中，数据库尚未有任何设备信息。
     *
     * @param mqttClientId Mqtt ClientId
     * @param mqttUsername Mqtt 用户名
     * @param mqttPassword Mqtt 密码
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    EmqxAuthenticationStatus process(String mqttClientId, String mqttUsername, String mqttPassword);
}
