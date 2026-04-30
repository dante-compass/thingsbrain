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
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.link.manager.specification;

import org.dromara.dante.core.support.factory.StrategyFactory;
import org.dromara.thingsbrain.link.commons.definition.DataStorageHandler;
import org.dromara.thingsbrain.link.commons.definition.SpecificationPostManager;

import java.util.Optional;

/**
 * <p>Description: 物模型上报数据管理器定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/9 16:29
 */
public class DefaultSpecificationPostManager implements SpecificationPostManager {

    private final StrategyFactory<DataStorageHandler> strategyFactory;

    public DefaultSpecificationPostManager(StrategyFactory<DataStorageHandler> strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public Optional<DataStorageHandler> getStorageHandler() {
        return strategyFactory.getHandler();
    }
}
