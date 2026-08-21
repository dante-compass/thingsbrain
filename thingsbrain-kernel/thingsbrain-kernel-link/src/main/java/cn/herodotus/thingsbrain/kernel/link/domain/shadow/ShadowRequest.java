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
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.link.definition.AbstractMethodDomain;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.State;
import cn.hutool.v7.core.bean.BeanUtil;
import org.apache.commons.lang3.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 设备影子数据交互请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/31 23:00
 */
public class ShadowRequest extends AbstractMethodDomain<Integer> {

    private Map<String, Object> state;

    public ShadowRequest() {
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    private boolean containsReported() {
        return getState().containsKey(ProtocolConstants.PARAMETER__REPORTED);
    }

    private boolean containsDesired() {
        return getState().containsKey(ProtocolConstants.PARAMETER__DESIRED);
    }

    private Object getReported() {
        return getState().get(ProtocolConstants.PARAMETER__REPORTED);
    }

    private Object getDesired() {
        return getState().get(ProtocolConstants.PARAMETER__DESIRED);
    }

    /**
     * 判断是否为删除影子全部属性操作
     *
     * @return true 是；false 不是。
     */
    public boolean isClearReported() {
        if (containsReported()) {
            return getReported() instanceof String reported && Strings.CS.equals(reported, SymbolConstants.NULL);
        }

        return false;
    }

    /**
     * 判断是否为删除影子全部 Desired 操作
     *
     * @return true 是；false 不是。
     */
    public boolean isClearDesired() {
        if (containsDesired()) {
            return getDesired() instanceof String desired && Strings.CS.equals(desired, SymbolConstants.NULL);
        }

        return false;
    }

    private State createReportedState() {
        Object object = getReported();
        Map<String, Object> reported = BeanUtil.beanToMap(object);
        State state = new State();
        state.setReported(reported);
        return state;
    }

    public State getUpdateState() {
        if (containsReported()) {
            return createReportedState();
        }

        if (containsDesired()) {
            Object object = getDesired();
            Map<String, Object> desired = BeanUtil.beanToMap(object);
            State state = new State();
            state.setDesired(desired);
            return state;
        }

        return null;
    }

    public State getDeleteState() {
        if (containsReported()) {
            return createReportedState();
        }

        return null;
    }

    public static UpdateBuilder update(Integer version) {
        return new UpdateBuilder(version);
    }

    public static DeleteBuilder delete(Integer version) {
        return new DeleteBuilder(version);
    }

    public static ClearBuilder clear(Integer version) {
        return new ClearBuilder(version);
    }

    private static abstract class AbstractBuilder {
        private final Integer version;
        private String method;
        private Map<String, Object> state;

        protected AbstractBuilder(String method, Integer version) {
            this.method = method;
            this.version = version;
        }

        protected Map<String, Object> getState() {
            return state;
        }

        protected void setState(Map<String, Object> state) {
            this.state = state;
        }

        protected void setMethod() {
            this.method = MethodConstants.METHOD__SHADOW_UPDATE;
        }

        public ShadowRequest build() {
            ShadowRequest request = new ShadowRequest();
            request.setState(getState());
            request.setVersion(version);
            request.setMethod(method);
            return request;
        }
    }

    public static class UpdateBuilder extends AbstractBuilder {

        protected UpdateBuilder(Integer version) {
            super(MethodConstants.METHOD__SHADOW_UPDATE, version);
        }

        public UpdateBuilder reported(Map<String, Object> data) {
            Map<String, Object> state = new HashMap<>();
            state.put(ProtocolConstants.PARAMETER__REPORTED, data);
            this.setState(state);
            return this;
        }

        public UpdateBuilder desired(Map<String, Object> data) {
            Map<String, Object> state = new HashMap<>();
            state.put(ProtocolConstants.PARAMETER__DESIRED, data);
            this.setState(state);
            return this;
        }
    }

    public static class DeleteBuilder extends AbstractBuilder {

        protected DeleteBuilder(Integer version) {
            super(MethodConstants.METHOD__SHADOW_DELETE, version);
        }

        public DeleteBuilder reported(String... items) {
            Map<String, String> data = Arrays.stream(items).collect(Collectors.toMap(item -> item, item -> SymbolConstants.NULL));
            this.reported(data);
            return this;
        }

        public DeleteBuilder reported(Map<String, String> data) {
            Map<String, Object> state = new HashMap<>();
            state.put(ProtocolConstants.PARAMETER__REPORTED, data);
            this.setState(state);
            return this;
        }
    }

    public static class ClearBuilder extends AbstractBuilder {

        protected ClearBuilder(Integer version) {
            super(MethodConstants.METHOD__SHADOW_DELETE, version);
        }

        public ClearBuilder reported() {
            Map<String, Object> state = new HashMap<>();
            state.put(ProtocolConstants.PARAMETER__REPORTED, SymbolConstants.NULL);
            this.setState(state);
            return this;
        }

        public ClearBuilder desired() {
            Map<String, Object> state = new HashMap<>();
            state.put(ProtocolConstants.PARAMETER__DESIRED, SymbolConstants.NULL);
            setMethod();
            setState(state);
            return this;
        }
    }
}
