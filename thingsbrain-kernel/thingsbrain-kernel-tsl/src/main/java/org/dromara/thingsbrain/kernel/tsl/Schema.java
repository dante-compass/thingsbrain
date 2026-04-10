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

package org.dromara.thingsbrain.kernel.tsl;

import org.dromara.thingsbrain.kernel.tsl.describe.ObjectDescribe;
import org.dromara.thingsbrain.kernel.tsl.domain.Argument;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

/**
 * <p>Description: Json Schema 定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/5 0:28
 */
public class Schema<T extends Argument> extends ObjectDescribe<T> {

    @JsonProperty("$schema")
    private String schema = "https://json-schema.org/draft/2020-12/schema";

    public Schema(T argument) {
        this(Stream.of(argument));
    }

    public Schema(Stream<T> arguments) {
        super(arguments);
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
