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

package cn.herodotus.thingsbrain.persistence.commons.service;

import cn.herodotus.thingsbrain.persistence.commons.domain.Device;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Description: 物联网设备分类服务定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/3 19:11
 */
public interface DeviceService extends BaseWriteAndPageService<Device, String> {

    /**
     * 分页条件查询。根据 ProductKey 进行分页查询
     *
     * @param pageNumber 当前页数
     * @param pageSize   分页大小
     * @param productKey 物联网 ProductKey
     * @return 查询结果 {@link Page<Device>}
     */
    Page<Device> findByCondition(int pageNumber, int pageSize, String productKey);

    /**
     * 根据 clientId 查询设备。ClientId 默认格式为：ProductKey.DeviceName
     *
     * @param clientId 设备 clientId
     * @return 设备 {@link Device}
     */
    Optional<Device> findByClientId(String clientId);

    /**
     * 根据 deviceName 查询设备。
     *
     * @param deviceName 设备名称
     * @return 设备 {@link Device}
     */
    Optional<Device> findByDeviceName(String deviceName);

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
