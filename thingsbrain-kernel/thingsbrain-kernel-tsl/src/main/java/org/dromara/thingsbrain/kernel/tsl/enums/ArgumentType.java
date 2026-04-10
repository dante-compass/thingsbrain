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

package org.dromara.thingsbrain.kernel.tsl.enums;

import org.dromara.dante.core.domain.Dictionary;
import org.dromara.dante.core.domain.DictionaryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物模型数据属性类型枚举 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 18:52
 */
@Schema(name = "物模型数据属性类型枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArgumentType implements DictionaryEnum {

    INTEGER(ArgumentType.INTEGER_SPECS, "int32(整数型)"),
    FLOAT(ArgumentType.FLOAT_SPECS, "float(单精度浮点型)"),
    DOUBLE(ArgumentType.DOUBLE_SPECS, "double(双精度浮点型)"),
    TEXT(ArgumentType.TEXT_SPECS, "text(字符串)"),
    DATE(ArgumentType.DATE_SPECS, "date(时间型)"),
    BOOLEAN(ArgumentType.BOOLEAN_SPECS, "bool(布尔型)"),
    ENUM(ArgumentType.ENUM_SPECS, "enum(枚举型)"),
    STRUCT(ArgumentType.STRUCT_SPECS, "struct(结构体)");

    public static final String INTEGER_SPECS = "int";
    public static final String FLOAT_SPECS = "float";
    public static final String DOUBLE_SPECS = "double";
    public static final String TEXT_SPECS = "text";
    public static final String DATE_SPECS = "date";
    public static final String BOOLEAN_SPECS = "bool";
    public static final String ENUM_SPECS = "enum";
    public static final String STRUCT_SPECS = "struct";

    private static final Map<String, ArgumentType> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (ArgumentType argumentType : ArgumentType.values()) {
            INDEX_MAP.put(argumentType.getValue(), argumentType);
            DICTIONARIES.add(argumentType.getDictionary(argumentType.name(), argumentType.ordinal()));
        }
    }

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    ArgumentType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static ArgumentType get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
