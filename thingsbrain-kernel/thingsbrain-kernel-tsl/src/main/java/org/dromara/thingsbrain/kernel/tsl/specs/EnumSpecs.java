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
import org.dromara.thingsbrain.kernel.tsl.describe.EnumDescribe;

import java.util.LinkedHashMap;

/**
 * <p>Description: TSL Enum 数据类型说明 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 18:11
 */
public class EnumSpecs extends LinkedHashMap<String, String> implements Specs {

    public EnumSpecs() {
    }

    @Override
    public Describe getDescribe() {
        return new EnumDescribe();
    }
}
