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
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.link.manager.specification;

import org.dromara.dante.core.support.file.JsonSchemaFileManager;
import org.dromara.thingsbrain.kernel.commons.exception.JsonSchemaValidateErrorException;
import org.dromara.thingsbrain.kernel.tsl.domain.ServiceDimension;
import org.dromara.thingsbrain.kernel.tsl.validator.ArgumentValidator;
import org.dromara.thingsbrain.link.commons.definition.SpecificationManager;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.service.ProductService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 物模型管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/24 23:22
 */
public class DefaultSpecificationManager implements SpecificationManager {

    private final SpecificationResourceManager specificationResourceManager;
    private final ProductService productService;
    private final ArgumentValidator argumentValidator;

    public DefaultSpecificationManager(ProductService productService, JsonSchemaFileManager jsonSchemaFileManager) {
        this.specificationResourceManager = new SpecificationResourceManager(jsonSchemaFileManager);
        this.productService = productService;
        this.argumentValidator = new ArgumentValidator();
    }

    @Override
    public ProductService getProductService() {
        return this.productService;
    }

    @Override
    public boolean release(String productKey) {
        Optional<Product> optional = productService.findByProductKey(productKey);

        return optional.map(product -> {
            product.setRelease(true);
            return productService.generate(productKey)
                    .map(specification -> specificationResourceManager.put(productKey, specification))
                    .filter(result -> result)
                    .map(status -> productService.save(product))
                    .map(ObjectUtils::isNotEmpty)
                    .orElse(false);
        }).orElse(false);
    }

    @Override
    public void verification(String productKey, String identifier, Map<String, Object> params) {
        Optional<Product> optional = productService.findByProductKey(productKey);

        optional.filter(Product::getVerification)
                .flatMap(product -> specificationResourceManager.findService(productKey, identifier)
                        .map(ServiceDimension::getInputData)
                        .map(data -> argumentValidator.validate(params, data)))
                .ifPresent(validationResult -> {
                    if (BooleanUtils.isFalse(validationResult.getValid())) {
                        throw new JsonSchemaValidateErrorException(validationResult.getMessage());
                    }
                });

    }
}
