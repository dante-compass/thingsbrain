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

package org.dromara.thingsbrain.persistence.jpa.manager;

import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.security.definition.AuthenticationManager;
import org.dromara.dante.security.domain.RegisteredClientTransmitter;
import org.dromara.thingsbrain.persistence.jpa.converter.HerodotusDeviceToAuthenticationConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.HerodotusDeviceToHerodotusDeviceConnectionConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.HerodotusDeviceToHerodotusMqttAccountConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDevice;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttAccount;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttCategory;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceConnectionService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusMqttAccountService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusMqttCategoryService;
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
    private final HerodotusMqttAccountService herodotusMqttAccountService;
    private final HerodotusMqttCategoryService herodotusMqttCategoryService;
    private final AuthenticationManager authenticationManager;
    private final Converter<HerodotusDevice, RegisteredClientTransmitter> toAuthentication;
    private final Converter<HerodotusDevice, HerodotusMqttAccount> toMqttAccount;
    private final Converter<HerodotusDevice, HerodotusDeviceConnection> toDeviceConnection;

    public HerodotusDeviceManager(HerodotusDeviceService herodotusDeviceService, HerodotusDeviceConnectionService herodotusDeviceConnectionService, HerodotusMqttAccountService herodotusMqttAccountService, HerodotusMqttCategoryService herodotusMqttCategoryService, AuthenticationManager authenticationManager) {
        this.herodotusDeviceService = herodotusDeviceService;
        this.herodotusDeviceConnectionService = herodotusDeviceConnectionService;
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
     * @param domain 设备实体 {@link HerodotusDevice}
     * @return 已保存设备实体 {@link HerodotusDevice}
     */
    private HerodotusDevice save(HerodotusDevice domain) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(domain.getClientId());
        return optional.orElse(herodotusDeviceService.save(domain));
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

    private void createMqttIdentify(HerodotusDevice domain) {
        HerodotusMqttAccount account = toMqttAccount.convert(domain);

        Set<HerodotusMqttCategory> categories = herodotusMqttCategoryService.findStandardCategoryForDevice();
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
        if (ObjectUtils.isEmpty(connection)) {
            // 如果 connection 为空，则认为是 OAuth2 DeviceFlow 的设备校验
            HerodotusDeviceConnection defaultConnection = toDeviceConnection.convert(device);
            herodotusDeviceConnectionService.connected(defaultConnection);
            // DeviceFlow 检验通过，为设备创建 Mqtt 账号权限，OAuth2 与 Mqtt 的联动
            createMqttIdentify(device);
        } else {
            herodotusDeviceConnectionService.connected(connection);
        }

        device.setActivated(true);
        herodotusDeviceService.save(device);
    }

    /**
     * 设备激活
     * <p>
     * 该方法主要用于 Mqtt。Mqtt 首次连接进行激活，该场景下 HerodotusDeviceConnection 参数一定有值
     *
     * @param clientId 物联网设备 ClientId
     */
    private void activate(String clientId, HerodotusDeviceConnection domain) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(clientId);
        optional.ifPresent(herodotusDevice -> activate(herodotusDevice, domain));
    }

    /**
     * 设备上线
     *
     * @param clientId      物联网设备 ClientId
     * @param newConnection 新的上线信息 {@link HerodotusDeviceConnection}
     */
    public void connected(String clientId, HerodotusDeviceConnection newConnection) {
        Optional<HerodotusDeviceConnection> optional = herodotusDeviceConnectionService.findByClientId(clientId);
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
        optional.ifPresent(this::createMqttIdentify);
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
     * OAuth2 DeviceFlow 验证通过后，调用该方法创建，该场景下 HerodotusDeviceConnection 参数没有值
     *
     * @param clientId 物联网设备 ClientId
     */
    public void performOAuth2Verification(String clientId) {
        activate(clientId, null);
    }
}
