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

package cn.herodotus.thingsbrain.persistence.jpa.logic.service;

import cn.herodotus.dante.data.jpa.repository.BaseJpaRepository;
import cn.herodotus.dante.data.jpa.service.AbstractJpaService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttAccount;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusMqttCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.repository.HerodotusMqttAccountRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 物联网 Mqtt 客户端注册 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/16 14:52
 */
@Service
public class HerodotusMqttAccountService extends AbstractJpaService<HerodotusMqttAccount, String> {

    private final HerodotusMqttAccountRepository herodotusMqttAccountRepository;

    public HerodotusMqttAccountService(HerodotusMqttAccountRepository herodotusMqttAccountRepository) {
        this.herodotusMqttAccountRepository = herodotusMqttAccountRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusMqttAccount, String> getRepository() {
        return herodotusMqttAccountRepository;
    }

    public Page<HerodotusMqttAccount> findByCondition(int pageNumber, int pageSize, String clientId, String username) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<HerodotusMqttAccount> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(clientId)) {
                predicates.add(criteriaBuilder.like(root.get("clientId"), like(clientId)));
            }

            if (StringUtils.isNotBlank(username)) {
                predicates.add(criteriaBuilder.like(root.get("mobilePhoneNumber"), like(username)));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public HerodotusMqttAccount assign(String accountId, String[] categories) {

        Set<HerodotusMqttCategory> mqttCategories = Arrays.stream(categories).map(categoryId -> {
            HerodotusMqttCategory category = new HerodotusMqttCategory();
            category.setCategoryId(categoryId);
            return category;
        }).collect(Collectors.toSet());

        Optional<HerodotusMqttAccount> mqttAccount = findById(accountId);

        return mqttAccount.map(data -> {
                    data.setCategories(mqttCategories);
                    return data;
                })
                .map(this::save)
                .orElse(null);
    }
}
