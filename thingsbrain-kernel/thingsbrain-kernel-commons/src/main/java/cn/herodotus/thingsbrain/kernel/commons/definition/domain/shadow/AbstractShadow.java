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

package cn.herodotus.thingsbrain.kernel.commons.definition.domain.shadow;

import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import org.apache.commons.lang3.Strings;
import org.dromara.dante.core.constant.SymbolConstants;
import org.dromara.dante.core.domain.BaseEntity;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 设备影子核心数据抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 22:24
 */
public abstract class AbstractShadow implements BaseEntity {

    private State state;
    private Metadata metadata;
    private Long version = 0L;

    protected AbstractShadow() {
        this.state = new State();
        this.metadata = new Metadata();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private Map<String, MetadataTimestamp> toMetadata(Map<String, Object> data) {
        return data.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new MetadataTimestamp()));
    }

    protected void update(State state) {
        if (state.justReported()) {
            Map<String, Object> stateData = state.getReported();
            this.state.getReported().putAll(stateData);
            this.metadata.getReported().putAll(toMetadata(stateData));
        }

        if (state.justDesired()) {
            Map<String, Object> stateData = state.getDesired();
            this.state.getDesired().putAll(stateData);
            this.metadata.getDesired().putAll(toMetadata(stateData));
        }
    }

    private void removeReported(String key) {
        this.state.getReported().remove(key);
        this.metadata.getReported().remove(key);
    }

    private void removeAllReported() {
        this.state.setReported(Map.of());
        this.metadata.setReported(Map.of());
    }

    private void removeAllDesired() {
        this.state.setDesired(Map.of());
        this.metadata.setDesired(Map.of());
    }

    /**
     * 判断是否为删除影子全部属性。
     *
     * @param state 请求参数 {@link State}
     * @return true 删除全部属性，false 删除指定属性。
     */
    private boolean isRemoveAllReported(State state) {
        return state.isNull() && state.containsKey(ProtocolConstants.PARAMETER__REPORTED) && Strings.CS.equals((String) state.get(ProtocolConstants.PARAMETER__REPORTED), SymbolConstants.NULL);
    }

    /**
     * 判断是否为删除影子全部属性。
     *
     * @param state 请求参数 {@link State}
     * @return true 删除全部属性，false 删除指定属性。
     */
    private boolean isRemoveAllDesired(State state) {
        return state.isNull() && state.containsKey(ProtocolConstants.PARAMETER__DESIRED) && Strings.CS.equals((String) state.get(ProtocolConstants.PARAMETER__DESIRED), SymbolConstants.NULL);
    }

    protected void delete(State state) {

        if (isRemoveAllReported(state)) {
            removeAllReported();
        } else {
            state.getReported().entrySet()
                    .stream()
                    .filter(entry -> Strings.CS.equals((String) entry.getValue(), SymbolConstants.NULL))
                    .forEach(entry -> removeReported(entry.getKey()));
        }

        if (isRemoveAllDesired(state)) {
            removeAllDesired();
        }
    }
}
