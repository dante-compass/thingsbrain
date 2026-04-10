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

import org.dromara.dante.security.definition.AuthenticationManager;
import org.dromara.dante.security.domain.RegisteredClientTransmitter;
import org.dromara.thingsbrain.persistence.jpa.converter.HerodotusDeviceToAuthenticationConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.HerodotusDeviceToHerodotusMqttAccountConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDevice;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttAccount;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttCategory;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusMqttAccountService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusMqttCategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

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
    private final HerodotusMqttAccountService herodotusMqttAccountService;
    private final HerodotusMqttCategoryService herodotusMqttCategoryService;
    private final AuthenticationManager authenticationManager;
    private final Converter<HerodotusDevice, RegisteredClientTransmitter> toAuthentication;
    private final Converter<HerodotusDevice, HerodotusMqttAccount> toMqttAccount;

    public HerodotusDeviceManager(HerodotusDeviceService herodotusDeviceService, HerodotusMqttAccountService herodotusMqttAccountService, HerodotusMqttCategoryService herodotusMqttCategoryService, AuthenticationManager authenticationManager) {
        this.herodotusDeviceService = herodotusDeviceService;
        this.herodotusMqttAccountService = herodotusMqttAccountService;
        this.herodotusMqttCategoryService = herodotusMqttCategoryService;
        this.authenticationManager = authenticationManager;
        this.toAuthentication = new HerodotusDeviceToAuthenticationConverter();
        this.toMqttAccount = new HerodotusDeviceToHerodotusMqttAccountConverter();
    }

    public HerodotusDeviceService getHerodotusDeviceService() {
        return herodotusDeviceService;
    }

    private HerodotusDevice save(HerodotusDevice domain) {
        return herodotusDeviceService.saveAndFlush(domain);
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

    /**
     * 该方法为使用客户端动态注册时，oauth2_registered_client 生成信息后，反向同步创建 {@link HerodotusDevice} 信息。
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
    public HerodotusDevice registration(HerodotusDevice domain) {
        return save(domain);
    }

    private void createMqttAccount(HerodotusDevice domain) {
        HerodotusMqttAccount account = toMqttAccount.convert(domain);

        Set<HerodotusMqttCategory> categories = herodotusMqttCategoryService.findStandardCategoryForDevice();
        account.setCategories(categories);

        herodotusMqttAccountService.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public void activation(String clientId) {
        Optional<HerodotusDevice> optional = herodotusDeviceService.findByClientId(clientId);
        optional.ifPresent(herodotusDevice -> {
            herodotusDevice.setActivated(true);
            save(herodotusDevice);
            createMqttAccount(herodotusDevice);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        authenticationManager.disable(id);
        herodotusDeviceService.deleteById(id);
        herodotusMqttAccountService.deleteById(id);
    }
}
