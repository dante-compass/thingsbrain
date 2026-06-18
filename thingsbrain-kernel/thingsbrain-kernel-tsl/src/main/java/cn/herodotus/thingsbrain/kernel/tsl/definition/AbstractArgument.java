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

package cn.herodotus.thingsbrain.kernel.tsl.definition;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;
import cn.herodotus.thingsbrain.kernel.tsl.jackson2.SpecificationViews;

import java.io.Serializable;

/**
 * <p>Description: 物模型参数通用属性定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 17:43
 */
public abstract class AbstractArgument implements Serializable {

    /**
     * 参数唯一标识符
     * <p>
     * Property: 属性唯一标识符（物模型模块下唯一）
     * Event: 事件唯一标识符（物模型模块下唯一，其中post是默认生成的属性上报事件
     * Service: 服务唯一标识符（物模型模块下唯一，其中set/get是根据属性的accessMode默认生成的服务）
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private String identifier;
    /**
     * 参数名称
     * <p>
     * Property: 属性名称
     * Event: 事件名称
     * Service: 服务名称
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private String name;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("identifier", identifier)
                .add("name", name)
                .toString();
    }
}
