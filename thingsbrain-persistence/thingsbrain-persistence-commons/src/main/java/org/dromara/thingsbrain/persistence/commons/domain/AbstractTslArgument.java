/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package org.dromara.thingsbrain.persistence.commons.domain;

import org.dromara.dante.data.commons.entity.AbstractAuditEntity;
import org.dromara.thingsbrain.kernel.commons.jackson.JsonToObjectSerializer;
import org.dromara.thingsbrain.kernel.commons.jackson.ObjectToJsonDeserializer;
import org.dromara.thingsbrain.kernel.tsl.enums.ArgumentType;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
