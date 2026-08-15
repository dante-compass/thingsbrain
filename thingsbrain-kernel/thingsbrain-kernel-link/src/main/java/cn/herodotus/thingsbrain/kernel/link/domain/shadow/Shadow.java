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

package cn.herodotus.thingsbrain.kernel.link.domain.shadow;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.AbstractShadow;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.Metadata;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.MetadataTimestamp;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.State;
import com.google.common.base.MoreObjects;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>Description: 设备影子结构定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/28 21:33
 */
public class Shadow extends AbstractShadow {

    private static final Logger log = LoggerFactory.getLogger(Shadow.class);

    private Long timestamp;

    public Shadow() {
        super();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * reported 和 desired 数据转换成 metadata 数据
     *
     * @param data reported 和 desired 数据
     * @return metadata 数据 {@link Map}
     */
    private Map<String, MetadataTimestamp> toMetadata(Map<String, Object> data) {
        return data.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new MetadataTimestamp()));
    }

    /**
     * 删除全部上报属性
     * <p>
     * 主要用于处理以下请求场景：
     * <pre>
     * {
     *     "method": "delete",
     *     "state": {
     *         "reported": "null"
     *     },
     *     "version": 1
     * }
     * </pre>
     */
    private void clearReported() {
        getState().clearReported();
        getMetadata().clearReported();
    }

    /**
     * 删除全部预期属性
     * <p>
     * 主要用于处理以下请求场景：
     * <pre>
     * {
     *     "method": "update",
     *     "state": {
     *         "desired": "null"
     *     },
     *     "version": 4
     * }
     * </pre>
     */
    private void clearDesired() {
        getState().clearDesired();
        getMetadata().clearDesired();
    }

    /**
     * 更新 state 中 reported 数据，同步更新 metadata 中的对应 reported 数据
     *
     * @param state 设备上报数据 {@link State}
     */
    private void updateReported(State state) {
        Map<String, MetadataTimestamp> metadata = toMetadata(state.getReported());
        getState().setReported(state.getReported());
        getMetadata().setReported(metadata);
    }

    /**
     * 更新 state 中 desired 数据，同步更新 metadata 中的对应 desired 数据
     *
     * @param state 平台期望数据  {@link State}
     */
    private void updateDesired(State state) {
        Map<String, MetadataTimestamp> metadata = toMetadata(state.getDesired());
        getState().setDesired(state.getDesired());
        getMetadata().setDesired(metadata);
    }

    /**
     * 删除 reported 中的指定条目。如果 reported 为空，则清空 reported.
     *
     * @param state 设备上报待删除数据 {@link State}
     */
    private void delete(State state) {
        Map<String, Object> stateReported = getState().getReported();
        Map<String, MetadataTimestamp> metadataReported = getMetadata().getReported();

        state.getReported().forEach((key, value) -> {
            String str = (String) value;
            if (Strings.CS.equals(str, SymbolConstants.NULL)) {
                stateReported.remove(key);
                metadataReported.remove(key);
            }
        });

        if (MapUtils.isNotEmpty(stateReported)) {
            getState().setReported(stateReported);
        } else {
            getState().clearReported();
        }

        if (MapUtils.isNotEmpty(metadataReported)) {
            getMetadata().setReported(metadataReported);
        } else {
            getMetadata().clearReported();
        }
    }

    /**
     * 清空设备影子数据，并将设备影子版本更新为0
     */
    private void reset() {
        setState(new State());
        setMetadata(new Metadata());
        setVersion(KernelConstants.VALUE__SHADOW_CLEAR_RESULT);
    }

    /**
     * 设备影子操作抽象定义
     *
     * @param version 版本
     */
    private void process(Integer version) {
        this.setVersion(version);
        this.setTimestamp(System.currentTimeMillis());
    }

    /**
     * 设备影子无参数无返回值操作抽象定义
     *
     * @param version  版本
     * @param runnable 操作
     */
    private void process(Integer version, Runnable runnable) {
        runnable.run();
        process(version);
    }

    /**
     * 设备影子有参数无返回值操作抽象定义
     *
     * @param state    状态数据 {@link State}
     * @param version  版本
     * @param consumer 操作
     */
    private void process(State state, Integer version, Consumer<State> consumer) {
        consumer.accept(state);
        process(version);
    }

    /**
     * 设备影子更新操作
     *
     * @param state   状态数据 {@link State}
     * @param version 版本
     */
    public Shadow update(State state, Integer version) {
        // 如果 version 设置为-1时，表示清空设备影子数据，设备影子会接收设备端的请求，并将设备影子版本更新为0。
        if (Objects.equals(version, KernelConstants.VALUE__SHADOW_CLEAR_REQUEST)) {
            log.info("[ThingsMesh] |- Device shadow reset for version is -1.");
            this.reset();
            this.setTimestamp(System.currentTimeMillis());
        } else {
            if (state.onlyReported()) {
                log.info("[ThingsMesh] |- Device shadow updated for device reported.");
                process(state, version, this::updateReported);
            }

            if (state.onlyDesired()) {
                log.info("[ThingsMesh] |- Device shadow updated for platform desired.");
                process(state, version, this::updateDesired);
            }
        }

        return this;
    }

    /**
     * 删除影子中某一属性
     *
     * @param state   状态数据 {@link State}
     * @param version 版本
     */
    public Shadow delete(State state, Integer version) {
        log.info("[ThingsMesh] |- Device shadow delete properties.");
        process(state, version, this::delete);

        return this;
    }

    /**
     * 删除影子中某一属性
     *
     * @param version 版本
     */
    public Shadow clearReported(Integer version) {
        log.info("[ThingsMesh] |- Device shadow empty reported for.");
        process(version, this::clearReported);
        return this;
    }

    /**
     * 删除影子中某一属性
     *
     * @param version 版本
     */
    public Shadow clearDesired(Integer version) {
        log.info("[ThingsMesh] |- Device shadow empty desired.");
        process(version, this::clearDesired);

        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .addValue(super.toString())
                .toString();
    }
}
