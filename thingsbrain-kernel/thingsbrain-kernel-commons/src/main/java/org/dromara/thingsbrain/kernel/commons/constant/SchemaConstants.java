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

package org.dromara.thingsbrain.kernel.commons.constant;

import java.util.Map;

/**
 * <p>Description: JSON 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/4 21:18
 */
public interface SchemaConstants {

    String SCHEMA_TYPE = "type";
    String SCHEMA_TYPE_STRING = "string";
    String SCHEMA_TYPE_INTEGER = "integer";
    String SCHEMA_TYPE_NUMBER = "number";
    String SCHEMA_TYPE_OBJECT = "object";

    String SCHEMA_TYPE_ENUM_PATTERN = "^(-[1-9][0-9]*|[1-9][0-9]*|[0]{1,1})$";

    Map<String, String> SCHEMA_DESCRIBE_STRING = Map.of(SCHEMA_TYPE, SCHEMA_TYPE_STRING);
    Map<String, Map<String, String>> SCHEMA_DESCRIBE_ENUM = Map.of(SCHEMA_TYPE_ENUM_PATTERN, SCHEMA_DESCRIBE_STRING);
}
