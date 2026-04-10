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

package org.dromara.thingsbrain.kernel.tsl.domain;

import org.dromara.thingsbrain.kernel.tsl.definition.Specs;
import org.dromara.thingsbrain.kernel.tsl.enums.ArgumentType;
import org.dromara.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import org.dromara.thingsbrain.kernel.tsl.specs.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 物模型数据类型对象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 17:58
 */
public class DataType {

    /**
     * 参数类型
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private ArgumentType type;
    /**
     * 类型说明
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private Specs specs;

    public DataType() {
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public Specs getSpecs() {
        return specs;
    }

    /**
     * 这里的type 对应的是枚举类 AttributeType。
     * AttributeType 类被标记为 @JsonFormat(shape = JsonFormat.Shape.OBJECT)，意味着序列化成一个对象。所以 Jackson 只会根据 AttributeType 中 @JsonValue 标记的属性值作为转换依据。
     * 如果 AttributeType 就是普通的枚举，那么就使用 Enum.name() 的值作为转换依据。
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = true, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = IntegerSpecs.class, name = ArgumentType.INTEGER_SPECS),
            @JsonSubTypes.Type(value = FloatSpecs.class, name = ArgumentType.FLOAT_SPECS),
            @JsonSubTypes.Type(value = DoubleSpecs.class, name = ArgumentType.DOUBLE_SPECS),
            @JsonSubTypes.Type(value = DateSpecs.class, name = ArgumentType.DATE_SPECS),
            @JsonSubTypes.Type(value = TextSpecs.class, name = ArgumentType.TEXT_SPECS),
            @JsonSubTypes.Type(value = EnumSpecs.class, name = ArgumentType.ENUM_SPECS),
            @JsonSubTypes.Type(value = BooleanSpecs.class, name = ArgumentType.BOOLEAN_SPECS),
            @JsonSubTypes.Type(value = StructSpecs.class, name = ArgumentType.STRUCT_SPECS),
    })
    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("specs", specs)
                .add("type", type)
                .toString();
    }
}
