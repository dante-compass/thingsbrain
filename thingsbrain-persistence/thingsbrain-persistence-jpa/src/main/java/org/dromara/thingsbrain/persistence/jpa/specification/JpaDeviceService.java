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

package org.dromara.thingsbrain.persistence.jpa.specification;

import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.service.DeviceService;
import org.dromara.thingsbrain.persistence.jpa.converter.FromDeviceConnectionConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.FromDeviceConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.ToDeviceConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDevice;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceService;
import org.dromara.thingsbrain.persistence.jpa.manager.HerodotusDeviceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Description: 物联网设备 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/3 19:08
 */
public class JpaDeviceService implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(JpaDeviceService.class);

    private final HerodotusDeviceManager herodotusDeviceManager;
    private final HerodotusDeviceService delegate;
    private final Converter<HerodotusDevice, Device> toDevice;
    private final Converter<Device, HerodotusDevice> fromDevice;
    private final Converter<DeviceConnection, HerodotusDeviceConnection> fromDeviceConnection;

    public JpaDeviceService(HerodotusDeviceManager herodotusDeviceManager) {
        this.delegate = herodotusDeviceManager.getHerodotusDeviceService();
        this.herodotusDeviceManager = herodotusDeviceManager;
        this.toDevice = new ToDeviceConverter();
        this.fromDevice = new FromDeviceConverter();
        this.fromDeviceConnection = new FromDeviceConnectionConverter();
    }

    @Override
    public Page<Device> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusDevice> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toDevice::convert);
    }

    @Override
    public Page<Device> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusDevice> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toDevice::convert);
    }

    @Override
    public Device save(Device domain) {
        HerodotusDevice device = fromDevice.convert(domain);
        HerodotusDevice result = herodotusDeviceManager.creation(device);
        return toDevice.convert(result);
    }

    @Override
    public void deleteById(String id) {
        herodotusDeviceManager.deleteById(id);
    }

    @Override
    public Page<Device> findByCondition(int pageNumber, int pageSize, String productKey) {
        Page<HerodotusDevice> pages = delegate.findByCondition(pageNumber, pageSize, productKey);
        return pages.map(toDevice::convert);
    }

    @Override
    public Optional<Device> findByClientId(String clientId) {
        Optional<HerodotusDevice> domain = delegate.findByClientId(clientId);
        return domain.map(toDevice::convert);
    }

    @Override
    public Optional<Device> findByDeviceName(String deviceName) {
        Optional<HerodotusDevice> domain = delegate.findByDeviceName(deviceName);
        return domain.map(toDevice::convert);
    }

    @Override
    public void connected(String clientId, DeviceConnection deviceConnection) {
        HerodotusDeviceConnection connection = fromDeviceConnection.convert(deviceConnection);
        herodotusDeviceManager.connected(clientId, connection);
    }

    @Override
    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        herodotusDeviceManager.disconnected(clientId, reason, disconnectedAt);
    }

    @Override
    public void performMqttIdentification(String clientId) {
        herodotusDeviceManager.performMqttIdentification(clientId);
    }

    @Override
    public void performOAuth2Synchronization(Device domain) {
        HerodotusDevice device = fromDevice.convert(domain);
        HerodotusDevice result = herodotusDeviceManager.performOAuth2Synchronization(device);
        log.debug("[ThingsBrain] |- [OAUTH2-CLIENT-REGISTRATION] OAuth2 client registration process FINISHED for [{}].", result.getClientId());
    }

    @Override
    public void performOAuth2Verification(String clientId) {
        herodotusDeviceManager.performOAuth2Verification(clientId);
        log.debug("[ThingsBrain] |- [OAUTH2-DEVICE-VERIFICATION] OAuth2 device verification process FINISHED for [{}].", clientId);
    }
}
