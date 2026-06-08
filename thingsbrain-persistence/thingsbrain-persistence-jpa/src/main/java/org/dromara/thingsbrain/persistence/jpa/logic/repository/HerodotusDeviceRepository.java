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

package org.dromara.thingsbrain.persistence.jpa.logic.repository;

import jakarta.persistence.QueryHint;
import org.dromara.dante.data.jpa.repository.BaseJpaRepository;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusDevice;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

/**
 * <p>Description: 物联网设备 Jpa 存储 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/20 16:17
 */
public interface HerodotusDeviceRepository extends BaseJpaRepository<HerodotusDevice, String> {

    /**
     * 通过 clientId 查找设备信息
     *
     * @param clientId 即 productKey/deviceName
     * @return {@link HerodotusDevice}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<HerodotusDevice> findByClientId(String clientId);

    /**
     * 根据 deviceName 查询设备详情
     *
     * @param deviceName 物联网平台为设备颁发产品内唯一的证书 DeviceName
     * @return 设备详情 {@link HerodotusDevice}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<HerodotusDevice> findByDeviceName(String deviceName);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @EntityGraph(attributePaths = {"deviceTags", "deviceTags.tag"})
    Optional<HerodotusDevice> findWithTagsByDeviceId(String deviceId);
}
