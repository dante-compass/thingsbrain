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

package org.dromara.thingsbrain.kernel.commons.definition.domain.shadow;

import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 设备影子通用元素抽象定义 </p>
 * <p>
 * 之所以 extends HashMap<String, T> 是为了实现阿里云删除影子全部属性。
 * <p>
 * 里云删除影子全部属性 JSON 格式如下：
 * <pre>
 * {
 *     "method": "delete",
 *     "state": {
 *         "reported": "null"
 *     },
 *     "version": 1
 * }
 * </pre>
 * 如果不 extends HashMap<String, T>，在请求实体序列化时将会失败。具体可以使用 ShadowRequestTest 进行测试
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 22:57
 */
public abstract class AbstractShadowElement<T> extends HashMap<String, T> {

    private Map<String, T> reported;
    private Map<String, T> desired;

    protected AbstractShadowElement() {
        reported = new HashMap<>();
        desired = new HashMap<>();
    }

    public Map<String, T> getReported() {
        return reported;
    }

    public void setReported(Map<String, T> reported) {
        this.reported = reported;
    }

    public Map<String, T> getDesired() {
        return desired;
    }

    public void setDesired(Map<String, T> desired) {
        this.desired = desired;
    }

    public boolean hasReported() {
        return MapUtils.isNotEmpty(reported);
    }

    public boolean hasDesired() {
        return MapUtils.isNotEmpty(desired);
    }

    public boolean isNull() {
        return !hasReported() && !hasDesired();
    }

    public boolean justReported() {
        return hasReported() && !hasDesired();
    }

    public boolean justDesired() {
        return !hasReported() && hasDesired();
    }
}
