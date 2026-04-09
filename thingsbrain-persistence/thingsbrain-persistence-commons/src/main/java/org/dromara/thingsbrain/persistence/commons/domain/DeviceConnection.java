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

package org.dromara.thingsbrain.persistence.commons.domain;

import cn.herodotus.thingsbrain.kernel.commons.domain.AddressTuple;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.dante.core.constant.SystemConstants;
import org.dromara.dante.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>Description: 物联网设备连接信息统一实体定义 </p>
 * <p>
 * {@link DeviceConnection} 没有与 {@link Device} 合并为一个类，主要考虑为：
 * 1. 方便管理以及后续的扩展。可能后续其它协议会存在一个设备对应多个连接的情况。
 * 2. 可以记录更详细的连接信息，方便调试和发现问题。
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 22:30
 */
@Schema(name = "物联网设备连接信息统一实体定义")
public class DeviceConnection implements BaseEntity {

    @Schema(name = "设备连接ID")
    private String id;

    @Schema(name = "产品KEY")
    private String productKey;

    @Schema(name = "设备名称")
    private String deviceName;

    @Schema(name = "客户端ID", description = "这里的 ClientId 是 Mqtt 的 ClientId 与设备的 ClientId 不同")
    private String clientId;

    @Schema(name = "Mqtt登录用户名")
    private String username;

    @Schema(name = "IP地址")
    private String ipAddress;

    @Schema(name = "链接端口", description = "通过端口也可以判断是以哪种方式进行的链接")
    private Integer sockPort;

    @Schema(name = "Mqtt协议名称")
    private String protocolName;

    @Schema(name = "Mqtt协议版本")
    private Integer protocolVersion;

    @Schema(name = "KeepAlive", title = "客户端和 MQTT 服务器可以判定当前是否存在半连接问题，从而关闭对应连接")
    private Integer keepAlive;

    @Schema(name = "是否丢弃已存在会话并重新创建一个新的会话", description = "true 时表示必须丢弃任何已存在的会话，并创建一个全新的会话；为 false 时表示必须使用与 Client ID 关联的会话来恢复与客户端的通信（除非会话不存在）")
    private Boolean cleanStart = Boolean.FALSE;

    @Schema(name = "过期周期", title = "解决了 MQTT 3.1.1 中持久会话永久存在造成的服务器资源浪费问题", description = "设置为 0 或未设置，表示断开连接时会话即到期；设置为大于 0 的数值，则表示会话在网络连接关闭后会保持多少秒；设置为 0xFFFFFFFF 表示会话永远不会过期。")
    private Long expiryInterval;

    @Schema(name = "退出原因", title = "Emqx 断开连接会提供退出原因")
    private String reason;

    @Schema(name = "是否在线", title = "直接记录在线状态，减少不必要的计算")
    private Boolean connected = Boolean.FALSE;

    @Schema(name = "连接时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    private LocalDateTime connectedAt;

    @Schema(name = "断开连接时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    private LocalDateTime disconnectedAt;

    public void setUsername(Identifier deviceIdentifier) {
        this.setProductKey(deviceIdentifier.getProductKey());
        this.setDeviceName(deviceIdentifier.getDeviceName());
    }

    public void setSockName(AddressTuple addressTuple) {
        this.setIpAddress(addressTuple.getIp());
        this.setSockPort(Integer.valueOf(addressTuple.getPort()));
    }

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
                .add("id", id)
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
