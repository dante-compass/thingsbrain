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

package org.dromara.thingsbrain.persistence.jpa.specification;

import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.service.ProductService;
import org.dromara.thingsbrain.persistence.jpa.converter.FromProductConverter;
import org.dromara.thingsbrain.persistence.jpa.converter.ToProductConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusProduct;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusProductService;
import org.dromara.thingsbrain.persistence.jpa.manager.HerodotusProductManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * <p>Description: 物联网产品 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 14:28
 */
public class JpaProductService implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(JpaProductService.class);

    private final HerodotusProductManager herodotusProductManager;
    private final HerodotusProductService delegate;
    private final Converter<HerodotusProduct, Product> toProduct;
    private final Converter<Product, HerodotusProduct> fromProduct;

    public JpaProductService(HerodotusProductManager herodotusProductManager) {
        this.herodotusProductManager = herodotusProductManager;
        this.delegate = herodotusProductManager.getHerodotusProductService();
        this.toProduct = new ToProductConverter();
        this.fromProduct = new FromProductConverter();
    }

    @Override
    public Page<Product> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusProduct> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toProduct::convert);
    }

    @Override
    public Page<Product> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusProduct> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toProduct::convert);
    }

    @Override
    public Product save(Product domain) {
        HerodotusProduct entity = herodotusProductManager.save(fromProduct.convert(domain));
        return toProduct.convert(entity);
    }

    @Override
    public void deleteById(String id) {
        herodotusProductManager.deleteById(id);
    }

    @Override
    public Page<Product> findByCondition(int pageNumber, int pageSize, String productKey, String productName, String categoryName) {
        Page<HerodotusProduct> pages = delegate.findByCondition(pageNumber, pageSize, productKey, productName, categoryName);
        return pages.map(toProduct::convert);
    }

    @Override
    public Optional<Product> findByProductKey(String productKey) {
        Optional<HerodotusProduct> optional = delegate.findByProductKey(productKey);
        return optional
                .map(this.toProduct::convert);
    }

    @Override
    public Product switchAuthentication(Product domain) {

        log.debug("[ThingsBrain] |- [SWITCH-AUTHENTICATION] Start to switch product authentication.");

        return Optional.ofNullable(domain)
                .map(this.fromProduct::convert)
                .flatMap(herodotusProductManager::switchAuthentication)
                .map(toProduct::convert)
                .orElseThrow(() -> new IllegalArgumentException("Product domain error or item not exist."));
    }

    @Override
    public Optional<Specification> generate(String productKey) {
        return herodotusProductManager.generateSpecification(productKey);
    }
}
