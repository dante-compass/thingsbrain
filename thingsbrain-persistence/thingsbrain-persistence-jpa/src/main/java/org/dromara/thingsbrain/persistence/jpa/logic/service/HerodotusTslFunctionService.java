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

package org.dromara.thingsbrain.persistence.jpa.logic.service;

import org.dromara.dante.data.jpa.repository.BaseJpaRepository;
import org.dromara.dante.data.jpa.service.AbstractJpaService;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import org.dromara.thingsbrain.persistence.jpa.logic.repository.HerodotusTslFunctionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 物联网物模型模块 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 23:00
 */
@Service
public class HerodotusTslFunctionService extends AbstractJpaService<HerodotusTslFunction, String> {

    private final HerodotusTslFunctionRepository herodotusTslFunctionRepository;

    public HerodotusTslFunctionService(HerodotusTslFunctionRepository herodotusTslFunctionRepository) {
        this.herodotusTslFunctionRepository = herodotusTslFunctionRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusTslFunction, String> getRepository() {
        return herodotusTslFunctionRepository;
    }

    public Page<HerodotusTslFunction> findByProductId(int pageNumber, int pageSize, String productId) {
        return herodotusTslFunctionRepository.findByProductId(productId, PageRequest.of(pageNumber, pageSize));
    }

    public List<HerodotusTslFunction> findAllByProductKey(String productKey) {
        return herodotusTslFunctionRepository.findAllByProductKey(productKey);
    }

//    public List<HerodotusTslFunction> findAllSettableProperties(String productKey) {
//        return herodotusTslFunctionRepository.findAllByProductKeyAndDimensionAndAccessMode(productKey, Dimension.PROPERTY, AccessMode.READ_WRITE);
//    }
//
//    public List<HerodotusTslFunction> findAllCallableServices(String productKey) {
//        Specification<HerodotusTslFunction> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicates.add(criteriaBuilder.equal(root.get("productKey"), productKey));
//            predicates.add(criteriaBuilder.equal(root.get("dimension"), Dimension.SERVICE.name()));
//
//            Join<HerodotusTslFunction, HerodotusTslArgument> join = root.join("attributes", JoinType.LEFT);
//            predicates.add(criteriaBuilder.isFalse(join.get("output")));
//
//            Predicate[] predicateArray = new Predicate[predicates.size()];
//            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
//            return criteriaQuery.getRestriction();
//        };
//
//        return findAll(specification);
//    }
}
