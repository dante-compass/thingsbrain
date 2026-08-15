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

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.dante.data.jpa.entity.AbstractSysEntity;
import cn.herodotus.dante.security.definition.RegisteredClientDetails;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.herodotus.thingsbrain.persistence.commons.enums.AuthenticationMode;
import cn.herodotus.thingsbrain.persistence.commons.enums.GatewayProtocol;
import cn.herodotus.thingsbrain.persistence.commons.enums.NetworkingMethod;
import cn.herodotus.thingsbrain.persistence.commons.enums.NodeType;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 物联网产品 Jpa 存储实体定义 </p>
 * <p>
 * 参考阿里云物联网平台设计
 * <p>
 * 阿里云物联网平台基本概念：产品，是设备的集合，通常指一组具有相同功能的设备。物联网平台为每个产品颁发全局唯一的ProductKey。
 * 参见：<a href="https://help.aliyun.com/zh/iot/product-overview/terms?spm=a2c4g.11186623.0.0.7888b581QW8Qqn">阿里云物联网平台文档</a>
 * <p>
 * 认证：
 * 阿里云设备接入认证方案：一机一密 和 一型一密
 * · 一机一密：每个设备烧录其唯一的设备证书（ProductKey、DeviceName和DeviceSecret）。当设备与物联网平台建立连接时，物联网平台对其携带的设备证书信息进行认证。
 * · 一型一密：同一产品下所有设备可以烧录相同产品证书（即ProductKey和ProductSecret）。设备发送激活请求时，物联网平台对其携带的产品证书信息进行认证，认证通过，下发该设备接入所需的信息。设备再携带这些信息与物联网平台建立连接。
 * 这两种方案可以与 OAuth2 结合
 * · 一机一密：就相当于正常的客户端注册。每个设备都是客户端，需要为每个设备分配 OAuth2 的 clientId 和 clientSecret。每个设备的 clientId 和 clientSecret 都是不同的。最理想的方式就是采用客户端注册。
 * · 一型一密：就相当于每个产品就是一个 OAuth2 客户端。这类产品下的所有设备，clientId 和 clientSecret 都是相同的。
 * 其实这也符合 OAuth2 客户端注册的方式。OAuth2 客户端注册首先是要有一个"父"客户端，然后才能实现客户端注册。
 * <p>
 * 注意：
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
 * @date : 2024/8/9 17:00
 */
@Entity
@Table(name = "iot_product",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_name"})},
        indexes = {@Index(name = "iot_product_id_idx", columnList = "product_id"), @Index(name = "iot_product_pk_idx", columnList = "product_key")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_PRODUCT)
public class HerodotusProduct extends AbstractSysEntity implements RegisteredClientDetails {

    @Id
    @UuidGenerator
    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "product_key", length = 32, unique = true)
    private String productKey = IdUtil.fastSimpleUUID();

    @Column(name = "product_name", length = 128)
    private String productName = IdUtil.fastSimpleUUID();

    private String productSecret = IdUtil.fastSimpleUUID();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_PRODUCT_CATEGORY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private HerodotusProductCategory category;

    @Column(name = "node")
    @Enumerated(EnumType.ORDINAL)
    private NodeType nodeType;

    @Column(name = "protocol")
    @Enumerated(EnumType.ORDINAL)
    private GatewayProtocol gatewayProtocol;

    @Column(name = "networking")
    @Enumerated(EnumType.ORDINAL)
    private NetworkingMethod networkingMethod;

    @Column(name = "authentication")
    @Enumerated(EnumType.ORDINAL)
    private AuthenticationMode authenticationMode;

    @Column(name = "registration")
    private Boolean registration = Boolean.FALSE;

    @Column(name = "verification")
    private Boolean verification = Boolean.FALSE;

    @Column(name = "photo_url", length = 1024)
    private String photoUrl;

    @Column(name = "quantity")
    private Integer quantity = 0;

    @Column(name = "is_release")
    private Boolean release = Boolean.FALSE;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public HerodotusProductCategory getCategory() {
        return category;
    }

    public void setCategory(HerodotusProductCategory category) {
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
    public String getId() {
        return getProductId();
    }

    @Override
    public String getClientId() {
        return getProductKey();
    }

    @Override
    public String getClientName() {
        return getProductName();
    }

    /**
     * 接口 {@link RegisteredClientDetails}中的方法 {@link RegisteredClientDetails#getClientSecret()} 被定义为 `default`。
     * 主要为了解决部分实现不需要 clientSecret 方法。
     * 在此处一定要实现，否则会导致动态开启认证生成的 client 信息中密码为空。
     *
     * @return 客户端密钥
     */
    @Override
    public String getClientSecret() {
        return this.getProductSecret();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productId", productId)
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
