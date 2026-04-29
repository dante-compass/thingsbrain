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

import org.dromara.thingsbrain.link.manager.persistence.DefaultConnectionManager;
import org.dromara.thingsbrain.link.manager.persistence.DefaultIdentifierManager;
import org.dromara.thingsbrain.persistence.commons.manager.ConnectionManager;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.persistence.commons.service.DeviceConnectionService;
import org.dromara.thingsbrain.persistence.commons.service.DeviceService;
import org.dromara.thingsbrain.persistence.commons.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Persistence 相关 Manager 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/6 16:04
 */
@Configuration(proxyBeanMethods = false)
class LinkPersistenceManagerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LinkPersistenceManagerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Link Persistence Manager] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionManager connectionManager(DeviceConnectionService deviceConnectionService) {
        DefaultConnectionManager manager = new DefaultConnectionManager(deviceConnectionService);
        log.trace("[ThingsBrain] |- Bean [Connection Manager] Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public IdentifierManager identifierManager(ProductService productService, DeviceService deviceService) {
        DefaultIdentifierManager manager = new DefaultIdentifierManager(productService, deviceService);
        log.trace("[ThingsBrain] |- Bean [Identifier Manager] Configure.");
        return manager;
    }
}
