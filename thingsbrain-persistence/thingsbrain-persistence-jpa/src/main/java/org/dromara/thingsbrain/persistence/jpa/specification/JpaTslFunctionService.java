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

import org.dromara.thingsbrain.persistence.commons.domain.TslFunction;
import org.dromara.thingsbrain.persistence.commons.service.TslFunctionService;
import org.dromara.thingsbrain.persistence.jpa.converter.FromTslFunctionConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.ToTslFunctionConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionService;
import org.dromara.thingsbrain.persistence.jpa.manager.HerodotusTslFunctionManager;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * <p>Description: 物联网物模型模块 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 16:07
 */
public class JpaTslFunctionService implements TslFunctionService {

    private final HerodotusTslFunctionService delegate;
    private final HerodotusTslFunctionManager herodotusTslFunctionManager;
    private final Converter<HerodotusTslFunction, TslFunction> toFunction;
    private final Converter<TslFunction, HerodotusTslFunction> fromFunction;

    public JpaTslFunctionService(HerodotusTslFunctionManager herodotusTslFunctionManager) {
        this.delegate = herodotusTslFunctionManager.getHerodotusTslFunctionService();
        this.herodotusTslFunctionManager = herodotusTslFunctionManager;
        this.toFunction = new ToTslFunctionConverter();
        this.fromFunction = new FromTslFunctionConverter();
    }

    @Override
    public Page<TslFunction> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusTslFunction> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toFunction::convert);
    }

    @Override
    public Page<TslFunction> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusTslFunction> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toFunction::convert);
    }

    @Override
    public TslFunction save(TslFunction domain) {
        HerodotusTslFunction entity = herodotusTslFunctionManager.save(fromFunction.convert(domain));
        return toFunction.convert(entity);
    }

    @Override
    public void deleteById(String id) {
        delegate.deleteById(id);
    }


    @Override
    public Page<TslFunction> findByProductId(int pageNumber, int pageSize, String productId) {
        Page<HerodotusTslFunction> pages = delegate.findByProductId(pageNumber, pageSize, productId);
        return pages.map(toFunction::convert);
    }
}
