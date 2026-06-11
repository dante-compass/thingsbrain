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

package org.dromara.thingsbrain.persistence.jpa.logic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.dromara.dante.core.constant.SystemConstants;
import org.dromara.dante.core.domain.BaseEntity;
import org.dromara.thingsbrain.persistence.commons.constant.PersistenceConstants;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

/**
 * <p>Description: 物联网设备详情 Jpa 存储实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 17:34
 */
@Entity
@Table(name = "iot_device_connection", indexes = {
        @Index(name = "iot_device_connection_id_idx", columnList = "connect_id"),
        @Index(name = "iot_device_connection_pdk_idx", columnList = "product_key"),
        @Index(name = "iot_device_connection_dn_idx", columnList = "device_name"),
        @Index(name = "iot_device_connection_cid_idx", columnList = "client_id"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE)
public class HerodotusDeviceConnection implements BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "connect_id", length = 64)
    private String connectId;

    @Column(name = "product_key", length = 32)
    private String productKey;

    @Column(name = "device_name", length = 32)
    private String deviceName;

    @Column(name = "client_id", length = 100)
    private String clientId;

    @Column(name = "username", length = 128)
    private String username;

    @Column(name = "ip_address", length = 20)
    private String ipAddress;

    @Column(name = "sock_port", length = 20)
    private Integer sockPort;

    @Column(name = "protocol_name", length = 20)
    private String protocolName;

    @Column(name = "protocol_version")
    private Integer protocolVersion;

    @Column(name = "keep_alive")
    private Integer keepAlive;

    @Column(name = "clean_start")
    private Boolean cleanStart = Boolean.FALSE;

    @Column(name = "expiry_interval")
    private Long expiryInterval;

    @Column(name = "reason", length = 50)
    private String reason;

    @Column(name = "is_connected")
    private Boolean connected = Boolean.FALSE;

    @Column(name = "connected_at")
    @JsonFormat(pattern = SystemConstants.PATTERN__DATE_TIME, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    private LocalDateTime connectedAt;

    @Column(name = "disconnected_at")
    @JsonFormat(pattern = SystemConstants.PATTERN__DATE_TIME, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    private LocalDateTime disconnectedAt;

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getSockPort() {
        return sockPort;
    }

    public void setSockPort(Integer sockPort) {
        this.sockPort = sockPort;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public Integer getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getCleanStart() {
        return cleanStart;
    }

    public void setCleanStart(Boolean cleanStart) {
        this.cleanStart = cleanStart;
    }

    public Long getExpiryInterval() {
        return expiryInterval;
    }

    public void setExpiryInterval(Long expiryInterval) {
        this.expiryInterval = expiryInterval;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public LocalDateTime getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(LocalDateTime connectedAt) {
        this.connectedAt = connectedAt;
    }

    public LocalDateTime getDisconnectedAt() {
        return disconnectedAt;
    }

    public void setDisconnectedAt(LocalDateTime disconnectedAt) {
        this.disconnectedAt = disconnectedAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("connectId", connectId)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .add("clientId", clientId)
                .add("username", username)
                .add("ipAddress", ipAddress)
                .add("sockPort", sockPort)
                .add("protocolName", protocolName)
                .add("protocolVersion", protocolVersion)
                .add("keepAlive", keepAlive)
                .add("cleanStart", cleanStart)
                .add("expiryInterval", expiryInterval)
                .add("reason", reason)
                .add("connected", connected)
                .add("connectedAt", connectedAt)
                .add("disconnectedAt", disconnectedAt)
                .toString();
    }
}
