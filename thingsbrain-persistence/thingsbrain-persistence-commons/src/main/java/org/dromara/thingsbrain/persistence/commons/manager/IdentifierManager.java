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

package org.dromara.thingsbrain.persistence.commons.manager;

import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.domain.Product;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Description: 物联网设备标识管理器 </p>
 * <p>
 * 物联网 Product 和 Device 是关键的标识信息，在很多地方会使用。大量注入 ProductService 和 DeviceService，模块间耦合性高，代码不够清爽。
 * {@link IdentifierManager} 将 ProductService 和 DeviceService 相关操作包装成 Manager 层，用 {@link IdentifierManager} 进行统一的调用。后续如果有需要，也可以自定义进行扩展。
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 14:54
 */
public interface IdentifierManager {

    /**
     * 通过 productKey 查询物联网产品
     *
     * @param productKey 物联网产品 ProductKey
     * @return 物联网产品 {@link Product}
     */
    Optional<Product> findProductByProductKey(String productKey);

    /**
     * 通过 clientId 查询物联网设备
     *
     * @param clientId 物联网设备 ClientId
     * @return 物联网设备 {@link Device}
     */
    Optional<Device> findDeviceByClientId(String clientId);

    /**
     * 通过 deviceName 查询物联网设备
     *
     * @param deviceName 物联网设备 DeviceName
     * @return 物联网设备 {@link Device}
     */
    Optional<Device> findDeviceByDeviceName(String deviceName);

    /**
     * 设备上线
     *
     * @param clientId         设备 ClientId
     * @param deviceConnection 上线信息 {@link DeviceConnection}
     */
    void connected(String clientId, DeviceConnection deviceConnection);

    /**
     * 设备下线
     *
     * @param clientId       设备 ClientId
     * @param reason         下线原因
     * @param disconnectedAt 下线时间 {@link LocalDateTime}
     */
    void disconnected(String clientId, String reason, LocalDateTime disconnectedAt);

    /**
     * Mqtt 设备注册认证。
     * <p>
     * 一机一密、一型一密注册认证成功之后，为其配置 mqtt 账号信息，后续可以使用 mqtt 账号连接。
     *
     * @param clientId 物联网设备 ClientId
     */
    void performMqttIdentification(String clientId);

    /**
     * 该方法为使用 OAuth2 客户端动态注册时，oauth2_registered_client 生成信息后，反向同步创建设备信息
     *
     * @param device 物联网设备 {@link Device}
     */
    void performOAuth2Synchronization(Device device);

    /**
     * 该方法为使用 OAuth2 设备码授权模式校验设备时，校验通过后激活设备信息。
     *
     * @param clientId 物联网设备 ClientId
     */
    void performOAuth2Verification(String clientId);
}
