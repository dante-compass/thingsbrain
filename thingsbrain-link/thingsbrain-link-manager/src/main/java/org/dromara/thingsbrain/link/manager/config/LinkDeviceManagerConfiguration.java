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

import org.dromara.thingsbrain.link.commons.definition.*;
import org.dromara.thingsbrain.link.manager.device.*;
//import org.dromara.thingsbrain.persistence.commons.service.DeviceShadowService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Device 相关 Manager 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/7 17:26
 */
@Configuration(proxyBeanMethods = false)
class LinkDeviceManagerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LinkDeviceManagerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Link Device Manager] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceConfigLogManager deviceConfigLogManager() {
        DefaultDeviceConfigLogManager manager = new DefaultDeviceConfigLogManager();
        log.trace("[ThingsBrain] |- Bean [Default Device Config Log Manager Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceConfigManager deviceConfigManager() {
        DefaultDeviceConfigManager manager = new DefaultDeviceConfigManager();
        log.trace("[ThingsBrain] |- Bean [Default Device Config Manager Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceTagManager deviceTagManager() {
        DefaultDeviceTagManager manager = new DefaultDeviceTagManager();
        log.trace("[ThingsBrain] |- Bean [Default Device Tag Manager] Configure.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceJobManager deviceJobManager() {
        DefaultDeviceJobManager manager = new DefaultDeviceJobManager();
        log.trace("[ThingsBrain] |- Bean [Default Device Job Manager Configure.");
        return manager;
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public DeviceShadowManager deviceShadowManager(DeviceShadowService deviceShadowService) {
//        DefaultDeviceShadowManager manager = new DefaultDeviceShadowManager(deviceShadowService);
//        log.trace("[ThingsBrain] |- Bean [Default Device Shadow Manager] Configure.");
//        return manager;
//    }

}
