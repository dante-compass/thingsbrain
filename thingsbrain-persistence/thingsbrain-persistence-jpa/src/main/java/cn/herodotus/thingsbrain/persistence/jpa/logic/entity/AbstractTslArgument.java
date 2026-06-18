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

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.thingsbrain.kernel.tsl.enums.ArgumentType;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.dromara.dante.data.jpa.entity.AbstractAuditEntity;

/**
 * <p>Description: 物模型 Attribute 公共属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/20 17:23
 */
@MappedSuperclass
public abstract class AbstractTslArgument extends AbstractAuditEntity {

    @Column(name = "identifier", length = 50)
    private String identifier;

    @Column(name = "argument_name", length = 30)
    private String name;

    @Column(name = "argument_type", length = 50)
    @Enumerated(EnumType.STRING)
    private ArgumentType type;

    @Column(name = "argument_specs", columnDefinition = "TEXT")
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

    public void setName(String argumentName) {
        this.name = argumentName;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType argumentType) {
        this.type = argumentType;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String argumentSpecs) {
        this.specs = argumentSpecs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("identifier", identifier)
                .add("name", name)
                .add("type", type)
                .add("specs", specs)
                .toString();
    }
}
