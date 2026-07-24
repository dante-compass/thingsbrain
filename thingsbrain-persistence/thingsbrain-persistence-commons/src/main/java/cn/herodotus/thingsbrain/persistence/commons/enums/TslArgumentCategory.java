/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsMesh.
 *
 * Herodotus ThingsMesh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsMesh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.thingsbrain.persistence.commons.enums;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.core.domain.DictionaryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物模型参数类别 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/19 15:31
 */
@Schema(name = "物模型参数类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TslArgumentCategory implements DictionaryEnum {

    PROPERTIES("PROPERTIES", "属性参数"),
    EVENTS_OUTPUT_DATA("EVENTS_OUTPUT_DATA", "事件输出数据参数"),
    SERVICES_OUTPUT_DATA("SERVICES_OUTPUT_DATA", "服务输出数据参数"),
    SERVICES_INPUT_DATA("SERVICES_INPUT_DATA", "服务输入数据参数");

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    private static final Map<String, TslArgumentCategory> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (TslArgumentCategory tslArgumentCategory : TslArgumentCategory.values()) {
            INDEX_MAP.put(tslArgumentCategory.name(), tslArgumentCategory);
            DICTIONARIES.add(tslArgumentCategory.getDictionary(tslArgumentCategory.name(), tslArgumentCategory.ordinal()));
        }
    }

    TslArgumentCategory(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public static TslArgumentCategory get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
