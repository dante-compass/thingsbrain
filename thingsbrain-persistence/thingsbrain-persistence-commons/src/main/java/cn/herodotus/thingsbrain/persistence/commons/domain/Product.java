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

import cn.herodotus.thingsbrain.persistence.commons.enums.AuthenticationMode;
import cn.herodotus.thingsbrain.persistence.commons.enums.GatewayProtocol;
import cn.herodotus.thingsbrain.persistence.commons.enums.NetworkingMethod;
import cn.herodotus.thingsbrain.persistence.commons.enums.NodeType;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.dante.data.commons.entity.AbstractSysEntity;

/**
 * <p>Description: 物联网产品统一实体定义 </p>
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
 * @date : 2025/4/2 12:47
 */
@Schema(name = "物联网产品统一实体定义")
public class Product extends AbstractSysEntity {

    @Schema(name = "产品Id", description = "对应SAS RegisteredClient 中的 id")
    private String id;

    @Schema(name = "产品Key", description = "对应SAS RegisteredClient 中的 clientId")
    private String productKey = IdUtil.fastSimpleUUID();

    @Schema(name = "产品名称", description = "对应SAS RegisteredClient 中的 clientName")
    private String productName = IdUtil.fastSimpleUUID();

    @Schema(name = "产品秘钥", description = "对应SAS RegisteredClient 中的 clientSecret")
    private String productSecret = IdUtil.fastSimpleUUID();

    @Schema(name = "产品分类ID")
    private ProductCategory category;

    @Schema(name = "节点类型", description = "MQTT型实例下仅支持选择直连设备")
    private NodeType nodeType;

    @Schema(name = "网关协议", description = "节点类型选择为网关子设备的参数")
    private GatewayProtocol gatewayProtocol;

    @Schema(name = "联网方式")
    private NetworkingMethod networkingMethod;

    @Schema(name = "认证方式")
    private AuthenticationMode authenticationMode;

    @Schema(name = "是否开启动态注册", description = "是否开启动态注册")
    private Boolean registration = Boolean.FALSE;

    @Schema(name = "是否开启数据校验", description = "true校验，对应阿里云物联网弱校验；false免校验，对应阿里云物联网免校验。")
    private Boolean verification = Boolean.FALSE;

    @Schema(name = "产品图片链接")
    private String photoUrl;

    @Schema(name = "设备数量")
    private Integer quantity = 0;

    @Schema(name = "是否发布", title = "用于控制模块可用性的状态")
    private Boolean release = Boolean.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSecret() {
        return productSecret;
    }

    public void setProductSecret(String productSecret) {
        this.productSecret = productSecret;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public GatewayProtocol getGatewayProtocol() {
        return gatewayProtocol;
    }

    public void setGatewayProtocol(GatewayProtocol gatewayProtocol) {
        this.gatewayProtocol = gatewayProtocol;
    }

    public NetworkingMethod getNetworkingMethod() {
        return networkingMethod;
    }

    public void setNetworkingMethod(NetworkingMethod networkingMethod) {
        this.networkingMethod = networkingMethod;
    }

    public AuthenticationMode getAuthenticationMethod() {
        return authenticationMode;
    }

    public void setAuthenticationMethod(AuthenticationMode authenticationMode) {
        this.authenticationMode = authenticationMode;
    }

    public Boolean getRegistration() {
        return registration;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    public Boolean getVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getRelease() {
        return release;
    }

    public void setRelease(Boolean release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("productId", id)
                .add("productKey", productKey)
                .add("productName", productName)
                .add("productSecret", productSecret)
                .add("category", category)
                .add("nodeType", nodeType)
                .add("gatewayProtocol", gatewayProtocol)
                .add("networkingMethod", networkingMethod)
                .add("authenticationMode", authenticationMode)
                .add("registration", registration)
                .add("verification", verification)
                .add("photoUrl", photoUrl)
                .add("quantity", quantity)
                .add("release", release)
                .toString();
    }
}
