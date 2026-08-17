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

package cn.herodotus.thingsbrain.persistence.jpa.manager;

import cn.herodotus.dante.security.definition.AuthenticationManager;
import cn.herodotus.dante.security.domain.RegisteredClientTransmitter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.HerodotusDeviceToAuthenticationConverter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.HerodotusDeviceToHerodotusDeviceConnectionConverter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.HerodotusDeviceToHerodotusMqttAccountConverter;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.*;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Description: 物联网 Mqtt 客户端注册 Jpa 存储 Manage </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/3 21:57
 */
public class HerodotusDeviceManager {

    private final HerodotusDeviceService herodotusDeviceService;
    private final HerodotusDeviceConnectionService herodotusDeviceConnectionService;
    private final HerodotusDeviceShadowService herodotusDeviceShadowService;
    private final HerodotusMqttAccountService herodotusMqttAccountService;
    private final HerodotusMqttCategoryService herodotusMqttCategoryService;
    private final AuthenticationManager authenticationManager;
    private final Converter<HerodotusDevice, RegisteredClientTransmitter> toAuthentication;
    private final Converter<HerodotusDevice, HerodotusMqttAccount> toMqttAccount;
    private final Converter<HerodotusDevice, HerodotusDeviceConnection> toDeviceConnection;

    public HerodotusDeviceManager(HerodotusDeviceService herodotusDeviceService, HerodotusDeviceConnectionService herodotusDeviceConnectionService, HerodotusDeviceShadowService herodotusDeviceShadowService, HerodotusMqttAccountService herodotusMqttAccountService, HerodotusMqttCategoryService herodotusMqttCategoryService, AuthenticationManager authenticationManager) {
        this.herodotusDeviceService = herodotusDeviceService;
        this.herodotusDeviceConnectionService = herodotusDeviceConnectionService;
        this.herodotusDeviceShadowService = herodotusDeviceShadowService;
        this.herodotusMqttAccountService = herodotusMqttAccountService;
        this.herodotusMqttCategoryService = herodotusMqttCategoryService;
        this.authenticationManager = authenticationManager;
        this.toAuthentication = new HerodotusDeviceToAuthenticationConverter();
        this.toMqttAccount = new HerodotusDeviceToHerodotusMqttAccountConverter();
        this.toDeviceConnection = new HerodotusDeviceToHerodotusDeviceConnectionConverter();
    }

    public HerodotusDeviceService getHerodotusDeviceService() {
        return herodotusDeviceService;
    }

    /**
     * 保存设备。
     * <p>
     * 因 clientId 是设备关键信息，又因 clientId 内容非常灵活不便于作为主键。所以额外查询一次，确保 clientId 的唯一性
     *
     * @param newDevice 设备实体 {@link HerodotusDevice}
     * @return 已保存设备实体 {@link HerodotusDevice}
     */
    private HerodotusDevice save(HerodotusDevice newDevice) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(newDevice.getClientId());
        optional.ifPresent(oldDevice -> newDevice.setDeviceId(oldDevice.getDeviceId()));
        return herodotusDeviceService.save(newDevice);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        authenticationManager.disable(id);
        herodotusDeviceService.deleteById(id);
        herodotusDeviceConnectionService.deleteById(id);
        herodotusMqttAccountService.deleteById(id);
    }

    /**
     * 通过界面直接创建设备。创建设备信息后，要同步开启认证，即同步在 oauth2_registered_client 生成信息。
     *
     * @param domain 设备信息 {@link HerodotusDevice}
     * @return 设备信息 {@link HerodotusDevice}
     */
    public HerodotusDevice creation(HerodotusDevice domain) {
        authenticationManager.enable(toAuthentication.convert(domain));
        return save(domain);
    }

    private void enableMqttIdentify(HerodotusDevice domain) {
        HerodotusMqttAccount account = toMqttAccount.convert(domain);

        Set<HerodotusMqttCategory> categories = herodotusMqttCategoryService.findCategoryForDevice();
        account.setCategories(categories);

        herodotusMqttAccountService.save(account);
    }

    /**
     * 设备激活
     * <p>
     * 目前设备激活未做复杂的设计, 仅根据 connection 中是否有记录判断设备激活状态。
     * <p>
     * 单独提取一个方法，是为了简化 Optional 操作的代码
     *
     * @param device     设备信息 {@link HerodotusDevice}
     * @param connection 设备上线信息 {@link HerodotusDeviceConnection}。允许传递 null，为 null 时用于 OAuth2 DeviceFlow 的验证，
     */
    private void activate(HerodotusDevice device, HerodotusDeviceConnection connection) {

        HerodotusDeviceConnection currentConnection = connection;
        if (ObjectUtils.isEmpty(currentConnection)) {
            // 如果 connection 为空，则认为是 OAuth2 DeviceFlow 的设备校验。
            // OAuth2 DeviceFlow 无法获取到 connection 对象，需要为其生成一个默认的、仅包含基本信息的、没有 mqtt 信息的 connection 对象
            currentConnection = toDeviceConnection.convert(device);
        }
        // 记录线下信息。激活操作代表第一次成功连接，所以也需要生成connection 信息
        HerodotusDeviceConnection deviceConnection = herodotusDeviceConnectionService.connected(currentConnection);
        // 第一次连接，生成 shadow 信息
        HerodotusDeviceShadow deviceShadow = herodotusDeviceShadowService.create(device.getProduct().getProductKey(), device.getDeviceName());

        // 为设备开启 Mqtt ACL
        enableMqttIdentify(device);

        // 更新设备的激活状态标识。目前仅作一个标识，没有实际约束操作。后续可以根据需要删除或者扩展
        device.setActivated(true);

        if (ObjectUtils.isNotEmpty(deviceConnection)) {
            device.setDeviceConnection(deviceConnection);
        }

        if (ObjectUtils.isNotEmpty(deviceShadow)) {
            device.setDeviceShadow(deviceShadow);
        }

        herodotusDeviceService.save(device);
    }

    /**
     * 设备激活
     * <p>
     * 该方法主要用于 Mqtt。Mqtt 首次连接进行激活，该场景下 HerodotusDeviceConnection 参数一定有值
     *
     * @param clientId   物联网设备 ClientId
     * @param connection 设备上线信息 {@link HerodotusDeviceConnection}
     */
    private void activate(String clientId, HerodotusDeviceConnection connection) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(clientId);
        optional.ifPresent(herodotusDevice -> activate(herodotusDevice, connection));
    }

    /**
     * 设备上线
     *
     * @param clientId      物联网设备 ClientId
     * @param newConnection 新的上线信息 {@link HerodotusDeviceConnection}
     */
    public void connected(String clientId, HerodotusDeviceConnection newConnection) {
        Optional<HerodotusDeviceConnection> optional = herodotusDeviceConnectionService.findByClientId(clientId);
        // 如果设备对应的 connected 信息已经存在，则认为是正常连接。如果不存在，则认为是第一次连接，所以需要进行激活操作
        optional.ifPresentOrElse(
                oldConnection -> herodotusDeviceConnectionService.reconnected(oldConnection, newConnection),
                () -> activate(clientId, newConnection));
    }

    /**
     * 客户端下线。
     *
     * @param clientId       物联网设备 ClientId
     * @param reason         下线理由。目前由 Mqtt Broker 传递。
     * @param disconnectedAt 下线时间
     */
    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        herodotusDeviceConnectionService.disconnected(clientId, reason, disconnectedAt);
    }

    /**
     * 设备注册认证。
     * <p>
     * 一机一密、一型一密注册认证成功之后，为其配置 mqtt 账号信息，后续可以使用 mqtt 账号连接。
     *
     * @param clientId 设备 ClientId
     */
    public void performMqttIdentification(String clientId) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(clientId);
        optional.ifPresent(this::enableMqttIdentify);
    }

    /**
     * 该方法为使用 OAuth2 客户端动态注册时，oauth2_registered_client 生成信息后，反向同步创建 {@link HerodotusDevice} 信息。
     * <p>
     * 注意：该方法不能与上面的 {@link #creation(HerodotusDevice)} 进行合并。
     * 如果合并，客户端注册成功会在 oauth2_registered_client 生成一条信息，同步生成设备信息后 authenticationManager.enable() 方法会再次更新 oauth2_registered_client 表中的信息。
     * <p>
     * 例如：客户端注册生成的 client 的 authorization_grant_types 字段有三种模式（refresh_token,client_credentials,password），
     * 同步生成设备信息后 authenticationManager.enable() 方法会覆盖该字段的值，最终变成一个模式（client_credentials）。
     *
     * @param domain 设备信息 {@link HerodotusDevice}
     * @return 设备信息 {@link HerodotusDevice}
     */
    public HerodotusDevice performOAuth2Synchronization(HerodotusDevice domain) {
        return save(domain);
    }

    /**
     * 该方法为使用 OAuth2 设备码授权模式校验设备时，校验通过后激活设备信息。
     * <p>
     * OAuth2 Device Flow 的 Verification 本身也是一种认证，认证通过后，后续直接访问连接即可。
     * <p>
     * OAuth2 DeviceFlow 验证通过后，调用该方法创建，该场景下 HerodotusDeviceConnection 参数没有值
     *
     * @param clientId 物联网设备 ClientId
     */
    public void performOAuth2Verification(String clientId) {
        activate(clientId, null);
    }
}
