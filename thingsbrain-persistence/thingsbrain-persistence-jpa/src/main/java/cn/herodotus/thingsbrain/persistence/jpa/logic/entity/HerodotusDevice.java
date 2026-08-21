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
import cn.herodotus.thingsbrain.persistence.jpa.logic.generator.HerodotusDeviceUuidGenerator;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 物联网设备 Jpa 存储实体定义 </p>
 * <p>
 * 单独创建一个表，方便以后扩展，并且不影响现有 OAuth2 Device 的管理。
 *
 * @author : gengwei.zheng
 * @date : 2023/9/20 14:08
 */
@Entity
@Table(name = "iot_device", indexes = {
        @Index(name = "iot_device_id_idx", columnList = "device_id"),
        @Index(name = "iot_device_pdk_idx", columnList = "product_id"),
        @Index(name = "iot_device_dn_idx", columnList = "device_name"),
        @Index(name = "iot_device_un_idx", columnList = "client_id"),
})
//@NamedEntityGraph(
//        name = "Device.tags",
//        attributeNodes = {
//                @NamedAttributeNode(value = "deviceTags", subgraph = "deviceTags.tag")
//        },
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "deviceTags.tag",
//                        attributeNodes = @NamedAttributeNode("tag")
//                )
//        }
//)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE)
public class HerodotusDevice extends AbstractSysEntity implements RegisteredClientDetails {

    @Id
    @HerodotusDeviceUuidGenerator
    @Column(name = "device_id", length = 64)
    private String deviceId;

    @Column(name = "device_name", length = 32)
    private String deviceName = IdUtil.fastSimpleUUID();

    @Column(name = "device_secret", length = 256)
    private String deviceSecret = IdUtil.fastSimpleUUID();

    /**
     * Mqtt Client Identifier 是用来唯一标识 Mqtt Client 的。由于设计的 ProductKey 和 DeviceName 是全局唯一的，就足以区分不同的设备。因此用这个二源组作为 Client Identifier 就足够了。
     * 所以，通常情况下 Mqtt 的 Username(格式：${deviceName}&${ProductKey})就足以区分客户端了，逻辑上就没有必要使用 Client Identifier。
     * <p>
     * 但是在某些场景下，可能会出现多个设备使用同样的设备三元组。这种情况 ProductKey 和 DeviceName 就不足以区分设备。
     * <p>
     * 所以目前的规则为：
     * · 如果用户不指定 clientId，即通过界面操作创建的设备就由系统自动生成，以 ${ProductKey}.${deviceName} 作为 clientId
     * · 如果用户指定 clientId，即动态客户端注册方式，建议使用设备的MAC地址或SN码
     * <p>
     * 设备客户端 ID 和 Mqtt 客户端ID 的区别：
     * · 如果不兼容阿里云物联网 Mqtt 认证的方式，那么 Mqtt ClientId 就可以直接使用设备的 ClientId
     * · 如果要兼容阿里云物联网 Mqtt 认证的方式，那么 设备的 ClientId 就仅是 Mqtt ClientId 的一部分，格式为：clientId|参数|
     */
    @Column(name = "client_id", length = 100)
    private String clientId;

    @Column(name = "is_activated")
    private Boolean activated = Boolean.FALSE;

    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_PRODUCT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private HerodotusProduct product;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_CONNECTION)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "connection_id")
    private HerodotusDeviceConnection deviceConnection;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_SHADOW)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "shadow_id")
    private HerodotusDeviceShadow deviceShadow;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_TAG)
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<HerodotusDeviceTag> deviceTags = new HashSet<>();

    /**
     * 便捷方法
     *
     * @return 标签集合
     */
    @Transient
    public Set<HerodotusTag> getTags() {
        return deviceTags.stream()
                .map(HerodotusDeviceTag::getTag)
                .collect(Collectors.toSet());
    }

    @PrePersist
    public void onInsert() {
        if (ObjectUtils.isNotEmpty(this.product)) {
            this.product.setQuantity(product.getQuantity() + 1);
        }
    }

    @PreRemove
    public void onDelete() {
        if (ObjectUtils.isNotEmpty(this.product)) {
            this.product.setQuantity(product.getQuantity() - 1);
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public HerodotusProduct getProduct() {
        return product;
    }

    public void setProduct(HerodotusProduct product) {
        this.product = product;
    }

    public HerodotusDeviceConnection getDeviceConnection() {
        return deviceConnection;
    }

    public void setDeviceConnection(HerodotusDeviceConnection deviceConnection) {
        this.deviceConnection = deviceConnection;
    }

    public HerodotusDeviceShadow getDeviceShadow() {
        return deviceShadow;
    }

    public void setDeviceShadow(HerodotusDeviceShadow deviceShadow) {
        this.deviceShadow = deviceShadow;
    }

    public Set<HerodotusDeviceTag> getDeviceTags() {
        return deviceTags;
    }

    public void setDeviceTags(Set<HerodotusDeviceTag> deviceTags) {
        this.deviceTags = deviceTags;
    }

    @Override
    public String getId() {
        return this.getDeviceId();
    }

    @Override
    public String getClientId() {
        return clientId;
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
        return this.getDeviceSecret();
    }

    @Override
    public String getClientName() {
        return this.getDeviceName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("deviceId", deviceId)
                .add("deviceName", deviceName)
                .add("deviceSecret", deviceSecret)
                .add("clientId", clientId)
                .add("activated", activated)
                .add("redirectUris", redirectUris)
                .toString();
    }
}
