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

import cn.herodotus.thingsbrain.kernel.tsl.enums.AccessMode;
import cn.herodotus.thingsbrain.kernel.tsl.enums.CallType;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.EventType;

/**
 * <p>Description: 模型转换元数据 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/3 23:12
 */
public interface SpecificationMetadata {

    /**
     * 物模型标识符
     *
     * @return 物模型标识符
     */
    String getIdentifier();

    /**
     * 物模型功能名称
     *
     * @return 物模型功能名称
     */
    String getName();

    /**
     * 物模型维度
     *
     * @return 物模型维度
     */
    Dimension getDimension();

    /**
     * 物模型 Property 读写类型
     *
     * @return 物模型 Property 读写类型
     */
    AccessMode getAccessMode();

    /**
     * 物模型 Event 事件类型
     *
     * @return 物模型 Event 事件类型
     */
    EventType getEventType();

    /**
     * 物模型 Service 服务调用类型
     *
     * @return 物模型 Service 服务调用类型
     */
    CallType getCallType();

    /**
     * 物模型是否为必需标识
     *
     * @return 物模型是否为必需标识
     */
    Boolean getRequired();

    /**
     * 物模型功能描述，目前仅 Service 和 Event 需要
     *
     * @return 物模型功能描述
     */
    String getDescription();

    /**
     * 物模型功能方法，目前仅 Service 和 Event 需要
     *
     * @return 物模型功能方法
     */
    String getMethod();
}
