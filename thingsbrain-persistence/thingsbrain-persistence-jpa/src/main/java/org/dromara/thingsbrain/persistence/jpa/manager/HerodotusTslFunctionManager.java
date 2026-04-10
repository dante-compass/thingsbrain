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

package org.dromara.thingsbrain.persistence.jpa.manager;

import org.dromara.thingsbrain.kernel.tsl.enums.Dimension;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslArgumentService;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * <p>Description: 物联网物模型功能 Manage </p>
 * <p>
 * 提取出 Manage 层，方便数据层面业务逻辑的视线，同时保持 Service 层代码的清爽
 *
 * @author : gengwei.zheng
 * @date : 2025/4/28 15:42
 */
public class HerodotusTslFunctionManager {

    private final HerodotusTslFunctionService herodotusTslFunctionService;
    private final HerodotusTslArgumentService herodotusTslArgumentService;

    public HerodotusTslFunctionManager(HerodotusTslFunctionService herodotusTslFunctionService, HerodotusTslArgumentService herodotusTslArgumentService) {
        this.herodotusTslFunctionService = herodotusTslFunctionService;
        this.herodotusTslArgumentService = herodotusTslArgumentService;
    }

    public HerodotusTslFunctionService getHerodotusTslFunctionService() {
        return herodotusTslFunctionService;
    }

    @Transactional(rollbackFor = Exception.class)
    public HerodotusTslFunction save(HerodotusTslFunction domain) {
        if (domain.getDimension() != Dimension.PROPERTY) {
            if (CollectionUtils.isNotEmpty(domain.getArguments())) {
                List<HerodotusTslArgument> attributes = herodotusTslArgumentService.saveAll(domain.getArguments());
                domain.setArguments(new HashSet<>(attributes));
            }
        }
        return herodotusTslFunctionService.saveAndFlush(domain);
    }
}
