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

package org.dromara.thingsbrain.persistence.commons.service;

import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * <p>Description: 物联网产品服务定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 14:28
 */
public interface ProductService extends BaseWriteAndPageService<Product, String> {

    /**
     * 根据 productKey 获取物联网产品
     *
     * @param productKey 物联网产品 ProductKey
     * @return 物联网产品 {@link Product}
     */
    Optional<Product> findByProductKey(String productKey);

    Page<Product> findByCondition(int pageNumber, int pageSize, String productKey, String productName, String categoryName);

    /**
     * 开启或关闭某个产品的设备动态注册功能。
     * <p>
     * SAS 目前没有开启和关闭动态注册的功能。为了解决这个问题采取的方式是添加和删除 OAuth2 RegisteredClient 信息。
     * 因为，按照当前 OAuth2 和 SAS 设计，必须要先有一个 RegisteredClient（我称之为父 RegisteredClient），才能进行客户端动态注册。如果没有这个信息，是无法进行动态注册的。
     *
     * @param domain 当前产品信息 {@link Product}
     * @return 产品信息 {@link Product}
     */
    Product switchAuthentication(Product domain);
//
//    /**
//     * 生成物模型声明
//     *
//     * @param productKey 物联网 ProductKey
//     * @return 物模型声明对象
//     */
//    Optional<Specification> generate(String productKey);
}
