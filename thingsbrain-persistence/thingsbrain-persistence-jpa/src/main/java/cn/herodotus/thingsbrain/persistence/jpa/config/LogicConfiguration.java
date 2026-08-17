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

import cn.herodotus.dante.security.definition.AuthenticationManager;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.*;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusDeviceManager;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusProductManager;
import cn.herodotus.thingsbrain.persistence.jpa.manager.HerodotusTslFunctionManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>Description: Jpa 基础 Logic 内容配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/9/30 16:31
 */
@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = {
        "cn.herodotus.thingsbrain.persistence.jpa.logic.entity"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.thingsbrain.persistence.jpa.logic.repository",
})
@ComponentScan(basePackages = {
        "cn.herodotus.thingsbrain.persistence.jpa.logic.service",
})
class LogicConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LogicConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Persistence Iot JPA Logic] Configure.");
    }

    @Bean
    public HerodotusTslFunctionManager herodotusTslFunctionManager(HerodotusTslFunctionService herodotusTslFunctionService, HerodotusTslArgumentService herodotusTslArgumentService, HerodotusTslFunctionArgumentService herodotusTslFunctionArgumentService) {
        return new HerodotusTslFunctionManager(herodotusTslFunctionService, herodotusTslArgumentService, herodotusTslFunctionArgumentService);
    }

    @Bean
    public HerodotusProductManager herodotusProductManager(HerodotusProductService herodotusProductService, HerodotusTslFunctionManager herodotusTslFunctionManager, AuthenticationManager authenticationManager) {
        return new HerodotusProductManager(herodotusProductService, herodotusTslFunctionManager, authenticationManager);
    }

    @Bean
    public HerodotusDeviceManager herodotusDeviceManager(HerodotusDeviceService herodotusDeviceService, HerodotusDeviceConnectionService herodotusDeviceConnectionService, HerodotusDeviceShadowService herodotusDeviceShadowService, HerodotusMqttAccountService herodotusMqttAccountService, HerodotusMqttCategoryService herodotusMqttCategoryService, AuthenticationManager authenticationManager) {
        return new HerodotusDeviceManager(herodotusDeviceService, herodotusDeviceConnectionService, herodotusDeviceShadowService, herodotusMqttAccountService, herodotusMqttCategoryService, authenticationManager);
    }
}
