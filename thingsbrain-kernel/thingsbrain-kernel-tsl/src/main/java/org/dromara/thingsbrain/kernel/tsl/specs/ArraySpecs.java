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
import org.dromara.thingsbrain.kernel.tsl.domain.DataType;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: TSL Array 数据类型说明 </p>
 * <p>
 * 暂时不支持 Array 类型
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 18:08
 */
@Deprecated
public class ArraySpecs implements Specs {

    /**
     * 数组元素的个数，最大512（array类型特有）
     */
    private String size;
    /**
     * 数组元素的类型（array类型特有）
     */
    private DataType item;

    public ArraySpecs() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public DataType getItem() {
        return item;
    }

    public void setItem(DataType item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("item", item)
                .toString();
    }

    @Override
    public Describe getDescribe() {
        return null;
    }
}
