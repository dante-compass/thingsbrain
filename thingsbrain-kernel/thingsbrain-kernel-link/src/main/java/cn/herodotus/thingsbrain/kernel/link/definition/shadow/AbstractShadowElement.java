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

package cn.herodotus.thingsbrain.kernel.link.definition.shadow;

import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowRequest;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 设备影子通用元素抽象定义 </p>
 * <p>
 * String, Map<String, T>>，主要是为了更好的控制 reported 或 desired 在设备影子中的显示，避免在 Jackson 层面做处理。
 * 将 reported 或 desired 作为 Map 中的 Key，在序列化时，有这个 key 那么 json 中就有对应数据，没有这个 key 那么 json 中就没有对应数据。
 * <p>
 * 如果将 reported 或 desired 作为实体属性，就需要在 json 层面做控制不显示等处理。更新或者覆盖时代码操作比较繁琐
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 22:57
 */
public abstract class AbstractShadowElement<T> extends HashMap<String, Map<String, T>> {

    protected AbstractShadowElement() {
        super();
    }

    /**
     * 获取 reported 或 desired 对应的 Map 类型值
     *
     * @param type reported 或 desired
     * @return 值
     */
    private Map<String, T> getValue(String type) {
        return this.get(type);
    }

    /**
     * 设置 reported 或 desired 对应的值
     *
     * @param type  reported 或 desired
     * @param value Map 类型值
     */
    private void setValue(String type, Map<String, T> value) {
        this.put(type, value);
    }

    /**
     * 删除 reported 或 desired
     *
     * @param type reported 或 desired
     */
    private void clear(String type) {
        this.remove(type);
    }

    public Map<String, T> getReported() {
        return getValue(ProtocolConstants.PARAMETER__REPORTED);
    }

    public Map<String, T> getDesired() {
        return getValue(ProtocolConstants.PARAMETER__DESIRED);
    }

    public void setReported(Map<String, T> reported) {
        setValue(ProtocolConstants.PARAMETER__REPORTED, reported);
    }

    public void setDesired(Map<String, T> desired) {
        setValue(ProtocolConstants.PARAMETER__DESIRED, desired);
    }

    public void clearReported() {
        clear(ProtocolConstants.PARAMETER__REPORTED);
    }

    public void clearDesired() {
        clear(ProtocolConstants.PARAMETER__DESIRED);
    }

    public boolean isReportedEmpty() {
        return !containsKey(ProtocolConstants.PARAMETER__REPORTED) || MapUtils.isEmpty(getReported());
    }

    public boolean isDesiredEmpty() {
        return !containsKey(ProtocolConstants.PARAMETER__DESIRED) || MapUtils.isEmpty(getDesired());
    }

    /**
     * 主要用于清空影子数据时，用来判断是否为清空 reported;
     *
     * @return 否为清空 reported
     */
    public boolean isClearReported() {
        return containsKey(ProtocolConstants.PARAMETER__REPORTED);
    }

    /**
     * 主要用于清空影子数据时，用来判断是否为清空 desired;
     *
     * @return 否为清空 desired
     */
    public boolean isClearDesired() {
        return containsKey(ProtocolConstants.PARAMETER__DESIRED);
    }

    /**
     * 当前数据中是否只有 reported 内容。
     * <p>
     * 注意：
     * 为了让 {@link ShadowRequest} 中的 Map<String, Object> state 可以适配成 {@link State} 中的 Map<String, Map<String,Object>> 类型，
     * 所以在转换过程中，将 {@link ShadowRequest} 中的 "reported": "null" 转换为 {@link State} "reported": null。
     * 因此，该放法在下面场景下，
     * <pre>
     * {
     *     "method": "delete",
     *     "state": {
     *         "reported": "null"
     *     },
     *     "version": 1
     * }
     * </pre>
     * 会始终返回 false。出现这种情况，需要改用 {@link #clearReported()} 方法
     *
     * @return 否只有 reported 内容
     */
    public boolean onlyReported() {
        return !isReportedEmpty() && isDesiredEmpty();
    }

    /**
     * 当前数据中是否只有 desired 内容。
     * <p>
     * 注意：
     * 为了让 {@link ShadowRequest} 中的 Map<String, Object> state 可以适配成 {@link State} 中的 Map<String, Map<String,Object>> 类型，
     * 所以在转换过程中，将 {@link ShadowRequest} 中的 "desired": "null" 转换为 {@link State} "desired": null。
     * 因此，该放法在下面场景下，
     * <pre>
     * {
     *     "method": "delete",
     *     "state": {
     *         "desired": "null"
     *     },
     *     "version": 1
     * }
     * </pre>
     * 会始终返回 false。出现这种情况，需要改用 {@link #clearDesired()}  方法
     *
     * @return 否只有 desired 内容
     */
    public boolean onlyDesired() {
        return isReportedEmpty() && !isDesiredEmpty();
    }

    public boolean isNull() {
        return this.isEmpty();
    }
}
