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

package org.dromara.thingsbrain.persistence.jpa.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.dante.data.jpa.converter.AbstractFromSysEntityConverter;
import org.dromara.thingsbrain.persistence.commons.domain.MqttAccount;
import org.dromara.thingsbrain.persistence.commons.domain.MqttCategory;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttAccount;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttCategory;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: {@link MqttAccount} 转 {@link HerodotusMqttAccount} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 14:34
 */
public class FromMqttAccountConverter extends AbstractFromSysEntityConverter<MqttAccount, HerodotusMqttAccount> {

    private final Converter<MqttCategory, HerodotusMqttCategory> fromMqttCategory;

    public FromMqttAccountConverter() {
        this.fromMqttCategory = new FromMqttCategoryConverter();
    }

    @Override
    public HerodotusMqttAccount getInstance() {
        return new HerodotusMqttAccount();
    }

    @Override
    public void prepare(MqttAccount source, HerodotusMqttAccount target) {

        target.setAccountId(source.getId());
        target.setClientId(source.getClientId());
        target.setUsername(source.getUsername());
        target.setPassword(source.getPassword());
        target.setSuperUser(source.getSuperUser());

        Optional.of(source.getCategories()) // 实体中设置了默认空集合
                .filter(CollectionUtils::isNotEmpty) // 主要判断数量是否为 0
                .ifPresent(categories -> {
                    Set<HerodotusMqttCategory> items = categories.stream().map(fromMqttCategory::convert).collect(Collectors.toSet());
                    target.setCategories(items);
                });
    }
}
