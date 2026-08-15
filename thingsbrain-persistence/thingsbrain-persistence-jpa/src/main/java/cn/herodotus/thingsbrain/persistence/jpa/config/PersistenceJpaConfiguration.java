/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * ThingsBrain licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ThingsBrain 是 Dante Cloud 系统生态产品，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 ThingsBrain 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.thingsbrain.persistence.jpa.config;

import cn.herodotus.thingsbrain.persistence.commons.condition.ConditionalOnIotPersistence;
import cn.herodotus.thingsbrain.persistence.commons.condition.IotPersistence;
import cn.herodotus.thingsbrain.persistence.commons.service.*;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.*;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusDeviceManager;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusProductManager;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusTslFunctionManager;
import cn.herodotus.thingsbrain.persistence.jpa.specification.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 使用 JPA 作为底层存储的 IOT 持久化配置 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/9 0:17
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnIotPersistence(IotPersistence.JPA)
@Import({LogicConfiguration.class})
public class PersistenceJpaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PersistenceJpaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Persistence Iot JPA] Configure.");
    }

    @Bean
    public ProductCategoryService productCategoryService(HerodotusProductCategoryService herodotusProductCategoryService) {
        JpaProductCategoryService service = new JpaProductCategoryService(herodotusProductCategoryService);
        log.trace("[ThingsBrain] |- Bean [Jpa Product Category Service] Configure.");
        return service;
    }

    @Bean
    public ProductService productService(HerodotusProductManager herodotusProductManager) {
        JpaProductService service = new JpaProductService(herodotusProductManager);
        log.trace("[ThingsBrain] |- Bean [Jpa Product Service] Configure.");
        return service;
    }

    @Bean
    public DeviceService deviceService(HerodotusDeviceManager herodotusDeviceManager) {
        JpaDeviceService service = new JpaDeviceService(herodotusDeviceManager);
        log.trace("[ThingsBrain] |- Bean [Jpa Device Service] Configure.");
        return service;
    }

    @Bean
    public DeviceShadowService deviceShadowService(HerodotusDeviceShadowService herodotusDeviceShadowService) {
        JpaDeviceShadowService service = new JpaDeviceShadowService(herodotusDeviceShadowService);
        log.trace("[ThingsBrain] |- Bean [Jpa Device Shadow Service] Configure.");
        return service;
    }

    @Bean
    public MqttAccountService mqttAccountService(HerodotusMqttAccountService herodotusMqttAccountService) {
        JpaMqttAccountService service = new JpaMqttAccountService(herodotusMqttAccountService);
        log.trace("[ThingsBrain] |- Bean [Jpa Mqtt Account Service] Configure.");
        return service;
    }

    @Bean
    public MqttCategoryService mqttCategoryService(HerodotusMqttCategoryService herodotusMqttCategoryService) {
        JpaMqttCategoryService service = new JpaMqttCategoryService(herodotusMqttCategoryService);
        log.trace("[ThingsBrain] |- Bean [Jpa Mqtt Category Service] Configure.");
        return service;
    }

    @Bean
    public MqttAuthorityService mqttAuthorityService(HerodotusMqttAuthorityService herodotusMqttAuthorityService) {
        JpaMqttAuthorityService service = new JpaMqttAuthorityService(herodotusMqttAuthorityService);
        log.trace("[ThingsBrain] |- Bean [Jpa Mqtt Authority Service] Configure.");
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public TslUnitService tslUnitService(HerodotusTslUnitService herodotusTslUnitService) {
        JpaTslUnitService service = new JpaTslUnitService(herodotusTslUnitService);
        log.trace("[ThingsBrain] |- Bean [Jpa Tsl Unit Service] Configure.");
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public TslFunctionService tslFunctionService(HerodotusTslFunctionManager herodotusTslFunctionManager) {
        JpaTslFunctionService service = new JpaTslFunctionService(herodotusTslFunctionManager);
        log.trace("[ThingsBrain] |- Bean [Jpa Tsl Function Service] Configure.");
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public TslArgumentService tslAttributeService(HerodotusTslArgumentService herodotusTslArgumentService) {
        JpaTslArgumentService service = new JpaTslArgumentService(herodotusTslArgumentService);
        log.trace("[ThingsBrain] |- Bean [Jpa Tsl Attribute Service] Configure.");
        return service;
    }
}
