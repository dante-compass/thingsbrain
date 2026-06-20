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

package cn.herodotus.thingsbrain.platform.autoconfigure;

import cn.herodotus.dante.core.function.EnumDictionaryBuilderCustomizer;
import cn.herodotus.dante.core.function.ErrorCodeMapperBuilderCustomizer;
import cn.herodotus.thingsbrain.platform.authentication.config.PlatformRegistrationConfiguration;
import cn.herodotus.thingsbrain.platform.autoconfigure.customizer.PlatformEnumDictionaryBuilderCustomizer;
import cn.herodotus.thingsbrain.platform.autoconfigure.customizer.PlatformErrorCodeMapperBuilderCustomizer;
import cn.herodotus.thingsbrain.platform.rest.config.PlatformRestConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: ThingsBrain 物联网平台自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/31 23:40
 */
@AutoConfiguration
@Import({
        PlatformRegistrationConfiguration.class,
        PlatformRestConfiguration.class,
})
public class PlatformAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PlatformAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Auto [Platform] Configure.");
    }

    @Bean
    public EnumDictionaryBuilderCustomizer thingsBrainPlatformEnumDictionaryBuilderCustomizer() {
        PlatformEnumDictionaryBuilderCustomizer customizer = new PlatformEnumDictionaryBuilderCustomizer();
        log.debug("[ThingsBrain] |- Strategy [Platform EnumDictionary Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer iotPlatformErrorCodeMapperBuilderCustomizer() {
        PlatformErrorCodeMapperBuilderCustomizer customizer = new PlatformErrorCodeMapperBuilderCustomizer();
        log.debug("[ThingsBrain] |- Strategy [Iot Platform ErrorCodeMapper Builder Customizer] Auto Configure.");
        return customizer;
    }
}
