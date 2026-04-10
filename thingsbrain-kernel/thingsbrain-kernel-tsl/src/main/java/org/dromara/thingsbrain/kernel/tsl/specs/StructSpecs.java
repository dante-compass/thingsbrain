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

package org.dromara.thingsbrain.kernel.tsl.specs;

import org.dromara.thingsbrain.kernel.tsl.definition.Describe;
import org.dromara.thingsbrain.kernel.tsl.definition.Specs;
import org.dromara.thingsbrain.kernel.tsl.describe.ObjectDescribe;
import org.dromara.thingsbrain.kernel.tsl.domain.Argument;

import java.util.ArrayList;

/**
 * <p>Description: TSL Struct 数据类型说明 </p>
 * <p>
 * 按照阿里云 TSL 规范，Array 还支持 Struct 类型，这会让TSL定义结构的 JSON 非常复杂，反序列化也复杂前端也复杂。
 * <p>
 * 所以，暂时预留 Struct 类型的代码，但是短期内不打算支持该类型。
 *
 * @author : gengwei.zheng
 * @date : 2024/8/5 14:31
 */
public class StructSpecs extends ArrayList<Argument> implements Specs {

    public StructSpecs() {
    }

    @Override
    public Describe getDescribe() {
        return new ObjectDescribe<>(this.stream());
    }
}
