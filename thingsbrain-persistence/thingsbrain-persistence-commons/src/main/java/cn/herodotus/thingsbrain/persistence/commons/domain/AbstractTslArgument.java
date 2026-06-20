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

import cn.herodotus.thingsbrain.kernel.commons.jackson.JsonToObjectSerializer;
import cn.herodotus.thingsbrain.kernel.commons.jackson.ObjectToJsonDeserializer;
import cn.herodotus.thingsbrain.kernel.tsl.enums.ArgumentType;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import cn.herodotus.dante.data.commons.entity.AbstractAuditEntity;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * <p>Description: 物联网物模型参数共性参数统一抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/20 17:23
 */
@Schema(name = "物联网物模型参数共性参数统一抽象定义")
public abstract class AbstractTslArgument extends AbstractAuditEntity {

    @Schema(name = "属性唯一标识符", description = "物模型模块下唯一")
    @Size(max = 50)
    private String identifier;

    @Schema(name = "属性名称")
    @Size(max = 30)
    private String name;

    @Schema(name = "属性类型")
    private ArgumentType type;

    /**
     * Specs 为 JSON 字符串，增加下面的序列化器避免，发送给前端的 JSON 出现 '\'
     */
    @Schema(name = "数据描述", description = "采用JSON字符串形式存储属性规格描述内容")
    @JsonDeserialize(using = ObjectToJsonDeserializer.class)
    @JsonSerialize(using = JsonToObjectSerializer.class)
    private String specs;

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

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("identifier", identifier)
                .add("name", name)
                .add("type", type)
                .add("specs", specs)
                .addValue(super.toString())
                .toString();
    }
}
