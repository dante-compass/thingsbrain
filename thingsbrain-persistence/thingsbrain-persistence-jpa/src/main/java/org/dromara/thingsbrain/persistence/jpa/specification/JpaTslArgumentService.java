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

package org.dromara.thingsbrain.persistence.jpa.specification;

import org.dromara.thingsbrain.persistence.commons.domain.TslArgument;
import org.dromara.thingsbrain.persistence.commons.service.TslArgumentService;
import org.dromara.thingsbrain.persistence.jpa.converter.ToTslArgumentConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslArgumentService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * <p>Description: 物联网物模型属性 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 16:07
 */
public class JpaTslArgumentService implements TslArgumentService {

    private final HerodotusTslArgumentService delegate;
    private final Converter<HerodotusTslArgument, TslArgument> toTslArgument;

    public JpaTslArgumentService(HerodotusTslArgumentService herodotusTslArgumentService) {
        this.delegate = herodotusTslArgumentService;
        this.toTslArgument = new ToTslArgumentConverter();
    }

    @Override
    public Page<TslArgument> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusTslArgument> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toTslArgument::convert);
    }

    @Override
    public Page<TslArgument> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusTslArgument> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toTslArgument::convert);
    }
}
