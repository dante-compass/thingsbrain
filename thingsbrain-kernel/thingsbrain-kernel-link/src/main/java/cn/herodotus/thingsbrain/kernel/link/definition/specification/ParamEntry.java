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

package cn.herodotus.thingsbrain.kernel.link.definition.specification;

import cn.herodotus.dante.core.domain.BaseModel;
import com.google.common.base.MoreObjects;

/**
 * <p>Description:  物模型上报数据条目通用定义实体</p>
 * <p>
 * 物模型上行数据，param 数据条目统一定义。
 *
 * @author : gengwei.zheng
 * @date : 2024/8/31 12:18
 */
class ParamEntry<T> implements BaseModel {

    public ParamEntry() {

    }

    public ParamEntry(T value) {
        this(value, System.currentTimeMillis());
    }

    public ParamEntry(T value, Long time) {
        this.value = value;
        this.time = time;
    }

    /**
     * 上报的属性值
     */
    private T value;
    /**
     * 事件上报的时间戳，类型为UTC毫秒级时间。
     * 该参数为可选字段。根据您的业务场景决定消息中是否带时间戳。如果消息频繁，需根据时间戳判断消息顺序，建议消息中带有时间戳。
     * · 若上传time，物联网平台的云端保存上传的时间作为事件上报时间。
     * · 若不上传time，物联网平台的云端自动生成事件上报时间并保存。
     */
    private Long time;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("time", time)
                .toString();
    }
}
