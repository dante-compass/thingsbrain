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

package cn.herodotus.thingsbrain.persistence.commons.domain;

import cn.herodotus.thingsbrain.kernel.tsl.enums.AccessMode;
import cn.herodotus.thingsbrain.kernel.tsl.enums.CallType;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.EventType;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 物联网物模型功能统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 16:04
 */
@Schema(name = "物联网物模型功能统一实体定义")
public class TslFunction extends AbstractTslEntity {

    @Schema(name = "功能Id")
    private String id;

    @Schema(name = "产品ID")
    private String productId;

    @Schema(name = "产品KEY", title = "冗余字段方便使用")
    private String productKey;

    @Schema(name = "维度")
    private Dimension dimension;

    @Schema(name = "属性读写类型", description = "只有物模型 properties 需要该属性")
    private AccessMode accessMode;

    @Schema(name = "事件类型", description = "只有物模型 events 需要该属性")
    private EventType eventType;

    @Schema(name = "服务调用类型", description = "只有物模型 services 需要该属性")
    private CallType callType;

    @Schema(name = "方法", title = "对于默认的 Event 和 Service 可以预存 method 方便使用")
    private String method;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "功能参数")
    private TslFunctionArgument arguments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TslFunctionArgument getArguments() {
        return arguments;
    }

    public void setArguments(TslFunctionArgument arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("productId", productId)
                .add("productKey", productKey)
                .add("dimension", dimension)
                .add("accessMode", accessMode)
                .add("eventType", eventType)
                .add("callType", callType)
                .add("method", method)
                .add("description", description)
                .add("arguments", arguments)
                .toString();
    }
}
