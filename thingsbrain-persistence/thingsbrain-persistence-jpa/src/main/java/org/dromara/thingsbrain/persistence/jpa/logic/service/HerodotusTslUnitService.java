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

package org.dromara.thingsbrain.persistence.jpa.logic.service;

import org.dromara.dante.data.jpa.repository.BaseJpaRepository;
import org.dromara.dante.data.jpa.service.AbstractJpaService;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslUnit;
import org.dromara.thingsbrain.persistence.jpa.logic.repository.HerodotusTslUnitRepository;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 物联网物模型单位 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 21:46
 */
@Service
public class HerodotusTslUnitService extends AbstractJpaService<HerodotusTslUnit, String> {

    private final HerodotusTslUnitRepository herodotusTslUnitRepository;

    public HerodotusTslUnitService(HerodotusTslUnitRepository herodotusTslUnitRepository) {
        this.herodotusTslUnitRepository = herodotusTslUnitRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusTslUnit, String> getRepository() {
        return herodotusTslUnitRepository;
    }
}
