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
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.Shadow;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowRequest;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import cn.herodotus.thingsbrain.persistence.commons.service.DeviceShadowService;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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
     * 判断是否允许进行修改。
     * <p>
     * 只有当新版本大于当前版本时，设备影子才会接收设备端的请求，并更新设备影子版本。
     * 如果version设置为-1时，表示清空设备影子数据，设备影子会接收设备端的请求，并将设备影子版本更新为0
     *
     * @param requestVersion 请求中传递的新 Version
     * @param shadowVersion  设备影子当前 Version
     * @return true 允许修改；false 不允许修改。
     */
    private boolean isAllowModify(Integer requestVersion, Integer shadowVersion) {
        return requestVersion > shadowVersion || Objects.equals(requestVersion, KernelConstants.VALUE__SHADOW_CLEAR_REQUEST);
    }

    /**
     * 是否设备影子执行了更新。
     * <p>
     * 新 shadow 的 version 值，如果和请求中的 version 相等或者 version 为 0，说明设备影子发生了变化，那么就更新数据库，反之不做任何操作
     *
     * @param requestVersion 请求中传递的新 Version
     * @param shadowVersion  数设备影子当前 Version
     * @return true 已经修改；false 未修改。
     */
    private boolean isModified(Integer requestVersion, Integer shadowVersion) {
        return Objects.equals(requestVersion, shadowVersion) || Objects.equals(shadowVersion, KernelConstants.VALUE__SHADOW_CLEAR_RESULT);
    }

    /**
     * 修改设备影子内容
     *
     * @param version      版本号
     * @param deviceShadow 设备影子实体
     * @param function     处理逻辑，update 或者 delete
     * @return 修改后的设备影子实体 {@link DeviceShadow}
     */
    private DeviceShadow modify(Integer version, DeviceShadow deviceShadow, Function<Shadow, Shadow> function) {
        String content = deviceShadow.getContent();
        Shadow shadow = JacksonUtils.toObject(content, Shadow.class);
        Shadow newShadow = function.apply(shadow);

        if (isModified(version, newShadow.getVersion())) {
            deviceShadow.setContent(JacksonUtils.toJson(newShadow));
            deviceShadow.setVersion(version);
            return deviceShadow;
        }

        // 表示未做任何变更
        return null;
    }

    /**
     * 修改设备影子内容
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param version    版本号
     * @param function   处理逻辑，update 或者 delete
     * @return 修改后的设备影子实体 {@link Optional}
     */
    private Optional<DeviceShadow> modify(String productKey, String deviceName, Integer version, Function<Shadow, Shadow> function) {
        return getDeviceShadowService().findOneByProductKeyAndDeviceName(productKey, deviceName)
                .filter(domain -> isAllowModify(version, domain.getVersion()))
                .map(domain -> modify(version, domain, function))
                .map(getDeviceShadowService()::save);
    }

    /**
     * 设备影子更新操作
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param request    请求值 {@link ShadowRequest}
     * @return 修改后的设备影子实体 {@link Optional}
     */
    default Optional<DeviceShadow> update(String productKey, String deviceName, ShadowRequest request) {
        if (request.isClearDesired()) {
            return modify(productKey, deviceName, request.getVersion(), shadow -> shadow.clearDesired(request.getVersion()));
        } else {
            return modify(productKey, deviceName, request.getVersion(), shadow -> shadow.update(request.getUpdateState(), request.getVersion()));
        }

    }

    /**
     * 设备影子删除操作
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param request    请求值 {@link ShadowRequest}
     * @return 修改后的设备影子实体 {@link Optional}
     */
    default Optional<DeviceShadow> delete(String productKey, String deviceName, ShadowRequest request) {
        if (request.isClearReported()) {
            return modify(productKey, deviceName, request.getVersion(), shadow -> shadow.clearReported(request.getVersion()));
        } else {
            return modify(productKey, deviceName, request.getVersion(), shadow -> shadow.delete(request.getDeleteState(), request.getVersion()));
        }
    }

    /**
     * 从 {@link DeviceShadow} 中读取 Shadow JSON 并转换成 {@link Shadow} 对象
     *
     * @param deviceShadow 设备影子实体 {@link DeviceShadow}
     * @return 设备影子或者空的影子
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
