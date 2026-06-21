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

package cn.herodotus.thingsbrain.persistence.commons.domain;

import cn.herodotus.dante.data.commons.entity.AbstractSysEntity;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 物联网设备统一实体定义 </p>
 * 目前系统中，涉及到与 oauth2_registered_client 相关的对象，其各个字段与 RegisteredClient 对应关系如下：
 * <pre>
 *     <table>
 *         <thead>
 *             <tr>
 *                 <th>SAS</th>
 *                 <th>OAuth2Application</th>
 *                 <th>Product</th>
 *                 <th>Device</th>
 *             </tr>
 *         </thead>
 *         <tbody>
 *             <tr>
 *                 <td>id</td>
 *                 <td>applicationId</td>
 *                 <td>productId</td>
 *                 <td>deviceId</td>
 *             </tr>
 *             <tr>
 *                 <td>clientId</td>
 *                 <td>clientId</td>
 *                 <td>productKey</td>
 *                 <td>clientId(格式：{ProductKey}.{DeviceName})</td>
 *             </tr>
 *             <tr>
 *                 <td>clientSecret</td>
 *                 <td>clientSecret</td>
 *                 <td>productSecret</td>
 *                 <td>deviceSecret</td>
 *             </tr>
 *             <tr>
 *                 <td>clientName</td>
 *                 <td>applicationName</td>
 *                 <td>productName</td>
 *                 <td>deviceName</td>
 *             </tr>
 *         </tbody>
 *     </table>
 * </pre>
 * <p>
 * · 阿里云物联网定义中的设备 clientId, 默认为${ProductKey} + '.' + ${DeviceName}组成的字符串
 * · 阿里云物联网定义中的Mqtt clientId, 固定格式：${ClientId}|securemode=${Mode},signmethod=${SignMethod}|timestamp=${timestamp}|。是该值需自定义，长度在64个字符以内。若为设备的ID信息，建议使用您设备的MAC地址或SN码，方便您识别区分不同的设备。MQTT的协议字段。
 * MQTT的Client ID和设备的${ClientId}，切勿混淆。
 * <p>
 * 阿里云Mqtt 中的 Username为：由设备名称DeviceName、and（&）和产品ProductKey组成，固定格式为${DeviceName}&${ProductKey}。
 * 阿里云Mqtt 中的 password为：通过选择的加密方法，以设备的DeviceSecret为密钥，将参数和参数值拼接后，加密生成Password
 * <p>
 * 设备证书指ProductKey、DeviceName、DeviceSecret的组合。
 * · ProductKey：是物联网平台为产品颁发的全局唯一标识。该参数很重要，在设备认证以及通信中都会用到，因此需要您保管好。
 * · DeviceName：在注册设备时，自定义的或系统生成的设备名称，具备产品维度内的唯一性。该参数很重要，在设备认证以及通信中都会用到，因此需要您保管好。
 * · DeviceSecret：物联网平台为设备颁发的设备密钥，和DeviceName成对出现。该参数很重要，在设备认证时会用到，因此需要您保管好并且不能泄露。
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 17:40
 */
@Schema(name = "物联网设备统一实体定义")
public class Device extends AbstractSysEntity {

    @Schema(name = "设备终端ID", description = "与 oauth2_registered_client 表 id 保持一致")
    private String id;

    @Schema(name = "设备名称", description = "对应SAS RegisteredClient 中的 clientId。产品范围内唯一。主要用于一机一密")
    private String deviceName = IdUtil.fastSimpleUUID();

    @Schema(name = "设备密钥", description = "对应SAS RegisteredClient 中的 clientName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String deviceSecret = IdUtil.fastSimpleUUID();

    @Schema(name = "用户名", description = "默认为ProductKey.DeviceName")
    private String clientId;

    @Schema(name = "产品ID", title = "设备的集合，通常指一组具有相同功能的设备", description = "物联网平台为每个产品颁发全局唯一的ProductId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Product product;

    @Schema(name = "是否激活")
    private Boolean activated = Boolean.FALSE;

    @Schema(name = "回调地址", title = "支持多个值，以逗号分隔。客户端动态注册以及一些授权模式会用到。")
    private String redirectUris;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("deviceId", id)
                .add("deviceName", deviceName)
                .add("deviceSecret", deviceSecret)
                .add("clientId", clientId)
                .add("product", product)
                .add("activated", activated)
                .add("redirectUris", redirectUris)
                .addValue(super.toString())
                .toString();
    }
}
