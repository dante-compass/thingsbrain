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

import cn.herodotus.thingsbrain.kernel.link.definition.shadow.AbstractShadow;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.State;
import com.google.common.base.MoreObjects;

import java.util.function.Consumer;

/**
 * <p>Description: 设备影子结构定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/28 21:33
 */
public class Shadow extends AbstractShadow {

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

    private void process(State state, Integer version, Consumer<State> consumer) {
        consumer.accept(state);
        this.setVersion(version);
        this.setTimestamp(System.currentTimeMillis());
    }

    public void update(State state, Integer version) {
        process(state, version, this::update);
    }

    public void delete(State state, Integer version) {
        process(state, version, this::delete);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .addValue(super.toString())
                .toString();
    }
}
