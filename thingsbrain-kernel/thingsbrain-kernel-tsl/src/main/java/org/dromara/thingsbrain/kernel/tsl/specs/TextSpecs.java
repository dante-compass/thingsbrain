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
import org.dromara.thingsbrain.kernel.tsl.describe.TextDescribe;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: TSL Text 数据类型说明 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 18:10
 */
public class TextSpecs implements Specs {

    private Integer length;

    public TextSpecs() {
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("length", length)
                .toString();
    }

    @Override
    public Describe getDescribe() {
        if (ObjectUtils.isNotEmpty(getLength())) {
            return new TextDescribe(0, getLength());
        } else {
            return new TextDescribe();
        }
    }
}
