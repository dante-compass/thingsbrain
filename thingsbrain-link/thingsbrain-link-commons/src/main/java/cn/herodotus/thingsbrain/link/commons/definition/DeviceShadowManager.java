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

package cn.herodotus.thingsbrain.link.commons.definition;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.State;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.Shadow;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import cn.herodotus.thingsbrain.persistence.commons.service.DeviceShadowService;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p>Description: 设备影子 Service 定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/5 16:32
 */
public interface DeviceShadowManager {

    /**
     * 获取到设备影子服务
     *
     * @return 设备影子服务 {@link DeviceShadowService}
     */
    DeviceShadowService getDeviceShadowService();

    /**
     * 修改设备影子内容
     *
     * @param version      版本号
     * @param deviceShadow 设备影子实体
     * @param consumer     处理逻辑，update 或者 delete
     * @return 修改后的设备影子实体 {@link DeviceShadow}
     */
    default DeviceShadow modify(Long version, DeviceShadow deviceShadow, Consumer<Shadow> consumer) {
        String content = deviceShadow.getContent();
        Shadow shadow = JacksonUtils.toObject(content, Shadow.class);
        consumer.accept(shadow);
        deviceShadow.setContent(JacksonUtils.toJson(shadow));
        deviceShadow.setVersion(version);
        return deviceShadow;
    }

    /**
     * 修改设备影子内容
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param version    版本号
     * @param consumer   处理逻辑，update 或者 delete
     * @return 修改后的设备影子实体 {@link Optional}
     */
    default Optional<DeviceShadow> modify(String productKey, String deviceName, Long version, Consumer<Shadow> consumer) {
        Optional<DeviceShadow> optional = getDeviceShadowService().findOneByProductKeyAndDeviceName(productKey, deviceName);
        return optional.filter(domain -> domain.getVersion() < version)
                .map(domain -> modify(version, domain, consumer))
                .map(getDeviceShadowService()::save);
    }

    /**
     * 设备影子更新操作
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param state      状态值 {@link State}
     * @param version    版本号
     * @return 修改后的设备影子实体 {@link Optional}
     */
    default Optional<DeviceShadow> update(String productKey, String deviceName, State state, Long version) {
        return modify(productKey, deviceName, version, shadow -> shadow.update(state, version));
    }

    /**
     * 设备影子删除操作
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param state      状态值 {@link State}
     * @param version    版本号
     * @return 修改后的设备影子实体 {@link Optional}
     */
    default Optional<DeviceShadow> delete(String productKey, String deviceName, State state, Long version) {
        return modify(productKey, deviceName, version, shadow -> shadow.delete(state, version));
    }

    /**
     * 从 {@link DeviceShadow} 中读取 Shadow JSON 并转换成 {@link Shadow} 对象
     *
     * @param deviceShadow 设备影子实体 {@link DeviceShadow}
     * @return 设备影子对象或者 null
     */
    default Shadow read(DeviceShadow deviceShadow) {
        return Optional.ofNullable(deviceShadow.getContent())
                .map(content -> JacksonUtils.toObject(content, Shadow.class))
                .orElse(null);
    }

    /**
     * 获取具体设备影子 JSON 数据
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @return 设备影子 JSON 对应实体 {@link Optional}
     */
    default Optional<Shadow> get(String productKey, String deviceName) {
        Optional<DeviceShadow> optional = getDeviceShadowService().findOneByProductKeyAndDeviceName(productKey, deviceName);
        return optional.map(this::read);
    }
}
