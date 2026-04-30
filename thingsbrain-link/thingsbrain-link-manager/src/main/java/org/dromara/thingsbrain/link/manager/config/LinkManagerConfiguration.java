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

package org.dromara.thingsbrain.link.manager.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 自定义 Link 协议管理器模块配置 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/29 18:43
 */
@Configuration(proxyBeanMethods = false)
@Import({
        LinkPersistenceManagerConfiguration.class,
        LinkSpecificationManagerConfiguration.class,
        LinkDeviceManagerConfiguration.class,
        LinkOtaManagerConfiguration.class,
        LinkSubsetManagerConfiguration.class,
        LinkMqttManagerConfiguration.class
})
public class LinkManagerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LinkManagerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Link Manager] Configure.");
    }
}
