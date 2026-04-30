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

package org.dromara.thingsbrain.link.manager.config;

import org.dromara.dante.core.support.factory.StrategyFactory;
import org.dromara.dante.core.support.file.JsonSchemaFileManager;
import org.dromara.thingsbrain.link.commons.definition.DataStorageHandler;
import org.dromara.thingsbrain.link.commons.definition.SpecificationManager;
import org.dromara.thingsbrain.link.commons.definition.SpecificationPostManager;
import org.dromara.thingsbrain.link.manager.specification.DefaultSpecificationManager;
import org.dromara.thingsbrain.link.manager.specification.DefaultSpecificationPostManager;
import org.dromara.thingsbrain.persistence.commons.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Specification 相关 Manager 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/6 16:09
 */
class LinkSpecificationManagerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LinkSpecificationManagerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Link Specification Manager] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SpecificationManager specificationManager(ProductService productService, JsonSchemaFileManager jsonSchemaFileManager) {
        DefaultSpecificationManager manager = new DefaultSpecificationManager(productService, jsonSchemaFileManager);
        log.trace("[ThingsBrain] |- Bean [Default Specification Manager] Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpecificationPostManager specificationPostManager(StrategyFactory<DataStorageHandler> strategyFactory) {
        DefaultSpecificationPostManager manager = new DefaultSpecificationPostManager(strategyFactory);
        log.trace("[ThingsBrain] |- Bean [Default Specification Post Manager] Configure.");
        return manager;
    }
}
