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

package cn.herodotus.thingsbrain.persistence.jpa.logic.service;

import cn.herodotus.dante.data.jpa.repository.BaseJpaRepository;
import cn.herodotus.dante.data.jpa.service.AbstractJpaService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceConnection;
import cn.herodotus.thingsbrain.persistence.jpa.logic.repository.HerodotusDeviceConnectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Description: 物联网设备连接详情 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 17:50
 */
@Service
public class HerodotusDeviceConnectionService extends AbstractJpaService<HerodotusDeviceConnection, String> {

    private final HerodotusDeviceConnectionRepository herodotusDeviceConnectionRepository;

    public HerodotusDeviceConnectionService(HerodotusDeviceConnectionRepository herodotusDeviceConnectionRepository) {
        this.herodotusDeviceConnectionRepository = herodotusDeviceConnectionRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusDeviceConnection, String> getRepository() {
        return herodotusDeviceConnectionRepository;
    }

    public Optional<HerodotusDeviceConnection> findByClientId(String clientId) {
        return herodotusDeviceConnectionRepository.findByClientId(clientId);
    }

    /**
     * 客户端上线。
     * <p>
     * 如果客户端信息已经存在，则更新现有信息状态。如果客户端信息不存在，则新建连接信息。
     *
     * @param newConnection 新的上线信息 {@link HerodotusDeviceConnection}
     */
    public void connected(HerodotusDeviceConnection newConnection) {
        save(newConnection);
    }

    /**
     * 客户端重新上线。
     * <p>
     * 用于区分客户端是首次上线还是，正常的上线。主要避免出现重复调用 findByClientId 情况。
     * <p>
     * 传递原有上下线信息，是为了获取原有信息中的 id，以确保 save 变为更新操作而不是新建
     *
     * @param oldConnection 原有上线信息 {@link HerodotusDeviceConnection}
     * @param newConnection 新的上线信息 {@link HerodotusDeviceConnection}
     */
    public void reconnected(HerodotusDeviceConnection oldConnection, HerodotusDeviceConnection newConnection) {
        newConnection.setConnectId(oldConnection.getConnectId());
        connected(newConnection);
    }

    /**
     * 客户端下线。
     *
     * @param clientId       物联网设备 ClientId
     * @param reason         下线理由。目前由 Mqtt Broker 传递。
     * @param disconnectedAt 下线时间
     */
    public void disconnected(String clientId, String reason, LocalDateTime disconnectedAt) {
        Optional<HerodotusDeviceConnection> optional = findByClientId(clientId);
        optional.ifPresent(connection -> {
            connection.setReason(reason);
            connection.setDisconnectedAt(disconnectedAt);
            connection.setConnected(false);
            save(connection);
        });
    }
}
