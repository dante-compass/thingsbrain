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

package org.dromara.thingsbrain.persistence.jpa.logic.repository;

import org.dromara.dante.data.jpa.repository.BaseJpaRepository;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Description: 物联网物模型模块 Jpa 存储 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 22:59
 */
public interface HerodotusTslFunctionRepository extends BaseJpaRepository<HerodotusTslFunction, String> {

    Page<HerodotusTslFunction> findByProductId(String productId, Pageable pageable);

    List<HerodotusTslFunction> findAllByProductKey(String productKey);
}
