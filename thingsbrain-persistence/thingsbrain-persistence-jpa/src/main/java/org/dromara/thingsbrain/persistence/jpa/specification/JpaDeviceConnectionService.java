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

import org.apache.commons.lang3.ObjectUtils;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.service.DeviceConnectionService;
import org.dromara.thingsbrain.persistence.jpa.converter.FromDeviceConnectionConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import org.dromara.thingsbrain.persistence.jpa.manager.HerodotusDeviceConnectionManager;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * <p>Description: 物联网设备连接详情 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/6 16:00
 */
public class JpaDeviceConnectionService implements DeviceConnectionService {

    private final HerodotusDeviceConnectionManager herodotusDeviceConnectionManager;
    private final Converter<DeviceConnection, HerodotusDeviceConnection> fromDeviceConnection;

    public JpaDeviceConnectionService(HerodotusDeviceConnectionManager herodotusDeviceConnectionManager) {
        this.herodotusDeviceConnectionManager = herodotusDeviceConnectionManager;
        this.fromDeviceConnection = new FromDeviceConnectionConverter();
    }

    @Override
    public void connected(String clientId, boolean isSignature, DeviceConnection deviceConnection) {
        HerodotusDeviceConnection connection = fromDeviceConnection.convert(deviceConnection);
        if (ObjectUtils.isNotEmpty(connection)) {
            herodotusDeviceConnectionManager.connected(clientId, isSignature, connection);
        }
    }

    @Override
    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        herodotusDeviceConnectionManager.disconnected(clientId, reason, disconnectedAt);
    }
}
