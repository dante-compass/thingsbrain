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

import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceConnectionService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Description: 物联网 Mqtt 设备连接 Jpa 存储 Manager </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/9/30 16:52
 */
public class HerodotusDeviceConnectionManager {

    private final HerodotusDeviceManager herodotusDeviceManager;
    private final HerodotusDeviceConnectionService herodotusDeviceConnectionService;

    public HerodotusDeviceConnectionManager(HerodotusDeviceManager herodotusDeviceManager, HerodotusDeviceConnectionService herodotusDeviceConnectionService) {
        this.herodotusDeviceManager = herodotusDeviceManager;
        this.herodotusDeviceConnectionService = herodotusDeviceConnectionService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void connected(String clientId, boolean isSignature, HerodotusDeviceConnection herodotusDeviceConnection) {

        Optional<HerodotusDeviceConnection> optional = herodotusDeviceConnectionService.findByClientId(herodotusDeviceConnection.getClientId());
        if (isSignature) {
            optional.ifPresentOrElse(
                    connection -> record(connection, herodotusDeviceConnection),
                    () -> activation(clientId, herodotusDeviceConnection)
            );
        } else {
            optional.ifPresentOrElse(
                    connection -> record(connection, herodotusDeviceConnection),
                    () -> connected(herodotusDeviceConnection));
        }
    }

    /**
     * 当前客户端上下线信息不存在。根据上线信息，创建上下线信息，并将上下线状态标记为 true
     *
     * @param connection 上下线信息 {@link HerodotusDeviceConnection}
     */
    private void connected(HerodotusDeviceConnection connection) {
        connection.setConnected(true);
        herodotusDeviceConnectionService.save(connection);
    }

    private void disconnected(HerodotusDeviceConnection connection) {
        connection.setConnected(true);
        herodotusDeviceConnectionService.save(connection);
    }

    /**
     * 当前客户端上下线信息已经存在。更新现有信息状态。
     * <p>
     * 传递原有上下线信息，是为了获取原有信息中的 id，以确保 save 变为更新操作而不是新建
     *
     * @param oldConnection 原有上下线信息 {@link HerodotusDeviceConnection}
     * @param newConnection 新的上下线信息 {@link HerodotusDeviceConnection}
     */
    private void record(HerodotusDeviceConnection oldConnection, HerodotusDeviceConnection newConnection) {
        newConnection.setConnectId(oldConnection.getConnectId());
        connected(newConnection);
    }

    /**
     * 如果是首次上线，那么进行激活操作。
     * <p>
     * 这里根据上下线信息表中是否已经存在信息判断是否为首次上线。采用这种方式，主要为了减少查询次数，否则只能每次都去查询设备是否激活。
     *
     * @param clientId      设备 ClientId（注意：这里是设备 ClientId, 不是 Mqtt ClientId）
     * @param newConnection 新的上下线信息 {@link HerodotusDeviceConnection}
     */
    private void activation(String clientId, HerodotusDeviceConnection newConnection) {
        connected(newConnection);
        herodotusDeviceManager.activation(clientId);
    }

    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        Optional<HerodotusDeviceConnection> optional = herodotusDeviceConnectionService.findByClientId(clientId);
        optional.ifPresent(connection -> {
            connection.setReason(reason);
            connection.setDisconnectedAt(disconnectedAt);
            disconnected(connection);
        });
    }
}
