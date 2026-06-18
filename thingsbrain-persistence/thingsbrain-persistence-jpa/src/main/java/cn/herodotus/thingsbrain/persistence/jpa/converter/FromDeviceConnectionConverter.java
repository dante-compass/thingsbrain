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

package cn.herodotus.thingsbrain.persistence.jpa.converter;

import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceConnection;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: {@link DeviceConnection} 转 {@link HerodotusDeviceConnection}转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/7 15:28
 */
public class FromDeviceConnectionConverter implements Converter<DeviceConnection, HerodotusDeviceConnection> {

    @Override
    public HerodotusDeviceConnection convert(DeviceConnection source) {

        HerodotusDeviceConnection target = new HerodotusDeviceConnection();
        target.setConnectId(source.getId());
        target.setProductKey(source.getProductKey());
        target.setDeviceName(source.getDeviceName());
        target.setClientId(source.getClientId());
        target.setUsername(source.getUsername());
        target.setIpAddress(source.getIpAddress());
        target.setSockPort(source.getSockPort());
        target.setProtocolName(source.getProtocolName());
        target.setProtocolVersion(source.getProtocolVersion());
        target.setKeepAlive(source.getKeepAlive());
        target.setCleanStart(source.getCleanStart());
        target.setExpiryInterval(source.getExpiryInterval());
        target.setReason(source.getReason());
        target.setConnected(source.getConnected());
        target.setConnectedAt(source.getConnectedAt());
        target.setDisconnectedAt(source.getDisconnectedAt());

        return target;
    }
}
