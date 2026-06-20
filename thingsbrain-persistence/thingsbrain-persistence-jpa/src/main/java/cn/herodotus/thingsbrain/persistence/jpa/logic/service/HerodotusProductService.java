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
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusProduct;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusProductCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.repository.HerodotusProductRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>Description: 物联网产品 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/9 22:42
 */
@Service
public class HerodotusProductService extends AbstractJpaService<HerodotusProduct, String> {

    private final HerodotusProductRepository herodotusProductRepository;

    public HerodotusProductService(HerodotusProductRepository herodotusProductRepository) {
        this.herodotusProductRepository = herodotusProductRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusProduct, String> getRepository() {
        return herodotusProductRepository;
    }

    public Optional<HerodotusProduct> findByProductKey(String productKey) {
        return herodotusProductRepository.findByProductKey(productKey);
    }

    public Page<HerodotusProduct> findByCondition(int pageNumber, int pageSize, String productKey, String productName, String categoryName) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<HerodotusProduct> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(productKey)) {
                predicates.add(criteriaBuilder.like(root.get("productKey"), like(productKey)));
            }

            if (ObjectUtils.isNotEmpty(productName)) {
                predicates.add(criteriaBuilder.like(root.get("productName"), like(productName)));
            }

            if (ObjectUtils.isNotEmpty(categoryName)) {
                Join<HerodotusProduct, HerodotusProductCategory> join = root.join("category", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(join.get("categoryName"), categoryName));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }
}
