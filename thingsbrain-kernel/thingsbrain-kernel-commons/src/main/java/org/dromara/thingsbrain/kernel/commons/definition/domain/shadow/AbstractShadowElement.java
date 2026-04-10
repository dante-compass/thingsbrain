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

package org.dromara.thingsbrain.kernel.commons.definition.domain.shadow;

import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 设备影子通用元素抽象定义 </p>
 * <p>
 * 之所以 extends HashMap<String, T> 是为了实现阿里云删除影子全部属性。
 * <p>
 * 里云删除影子全部属性 JSON 格式如下：
 * <pre>
 * {
 *     "method": "delete",
 *     "state": {
 *         "reported": "null"
 *     },
 *     "version": 1
 * }
 * </pre>
 * 如果不 extends HashMap<String, T>，在请求实体序列化时将会失败。具体可以使用 ShadowRequestTest 进行测试
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 22:57
 */
public abstract class AbstractShadowElement<T> extends HashMap<String, T> {

    private Map<String, T> reported;
    private Map<String, T> desired;

    protected AbstractShadowElement() {
        reported = new HashMap<>();
        desired = new HashMap<>();
    }

    public Map<String, T> getReported() {
        return reported;
    }

    public void setReported(Map<String, T> reported) {
        this.reported = reported;
    }

    public Map<String, T> getDesired() {
        return desired;
    }

    public void setDesired(Map<String, T> desired) {
        this.desired = desired;
    }

    public boolean hasReported() {
        return MapUtils.isNotEmpty(reported);
    }

    public boolean hasDesired() {
        return MapUtils.isNotEmpty(desired);
    }

    public boolean isNull() {
        return !hasReported() && !hasDesired();
    }

    public boolean justReported() {
        return hasReported() && !hasDesired();
    }

    public boolean justDesired() {
        return !hasReported() && hasDesired();
    }
}
