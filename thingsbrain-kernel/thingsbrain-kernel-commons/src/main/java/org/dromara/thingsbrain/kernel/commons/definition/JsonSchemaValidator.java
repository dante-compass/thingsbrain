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

package org.dromara.thingsbrain.kernel.commons.definition;

import org.dromara.thingsbrain.kernel.commons.domain.ValidationResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * <p>Description: JsonSchema 校验定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/8 21:13
 */
public interface JsonSchemaValidator {

    /**
     * 获取指定的 {@link ObjectMapper}。
     * <p>
     * 特殊的 {@link ObjectMapper}，该 {@link ObjectMapper} 需要忽略空值处理。与系统统一的 {@link ObjectMapper} 有差异，系统统一 {@link ObjectMapper} 不会忽略空值。
     *
     * @return {@link ObjectMapper}
     */
    ObjectMapper getObjectMapper();

    /**
     * 根据 Schema 校验 JSON 数据值
     *
     * @param data   待校验数据 {@link JsonNode}
     * @param schema 用于校验的 Schema {@link JsonNode}
     * @return 校验结果 {@link ValidationResult}
     */
    ValidationResult validate(JsonNode data, JsonNode schema);

    /**
     * 根据 Schema 校验 JSON 数据值
     *
     * @param data   待校验数据 {@link Map}
     * @param schema 用于校验的 Schema {@link JsonNode}
     * @return 校验结果 {@link ValidationResult}
     */
    ValidationResult validate(Map<String, Object> data, JsonNode schema);

    /**
     * 根据 Schema 校验 JSON 数据值
     *
     * @param data   待校验数据 {@link Map}
     * @param schema 用于校验的 Schema
     * @return 校验结果 {@link ValidationResult}
     */
    ValidationResult validate(Map<String, Object> data, String schema);
}
