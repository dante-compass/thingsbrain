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

package org.dromara.thingsbrain.persistence.commons.service;

import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.springframework.data.domain.Page;

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
     * 设备注册
     *
     * @param device 设备信息
     */
    void registration(Device device);

    /**
     * 设备激活
     *
     * @param clientId 设备 ClientId
     */
    void activation(String clientId);
}
