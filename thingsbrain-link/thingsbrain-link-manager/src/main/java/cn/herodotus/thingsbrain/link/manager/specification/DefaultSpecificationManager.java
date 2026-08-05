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

package cn.herodotus.thingsbrain.link.manager.specification;

import cn.herodotus.dante.core.support.file.JsonSchemaFileManager;
import cn.herodotus.thingsbrain.kernel.commons.domain.SchemaValidationResult;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.kernel.tsl.validator.SchemaValidator;
import cn.herodotus.thingsbrain.link.commons.definition.SpecificationManager;
import cn.herodotus.thingsbrain.persistence.commons.domain.Product;
import cn.herodotus.thingsbrain.persistence.commons.service.ProductService;
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
    private final SchemaValidator schemaValidator;

    public DefaultSpecificationManager(ProductService productService, JsonSchemaFileManager jsonSchemaFileManager) {
        this.specificationResourceManager = new SpecificationResourceManager(jsonSchemaFileManager);
        this.productService = productService;
        this.schemaValidator = new SchemaValidator();
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
            return productService.generate(product)
                    .map(specificationResourceManager::put)
                    .filter(result -> result)
                    .map(status -> productService.save(product))
                    .map(ObjectUtils::isNotEmpty)
                    .orElse(false);
        }).orElse(false);
    }

    @Override
    public SchemaValidationResult verification(String productKey, String identifier, Map<String, Object> params) {
        return productService.findByProductKey(productKey)
                .filter(Product::getVerification)
                .flatMap(product -> specificationResourceManager.findService(product.getProductKey(), identifier)
                        .map(ServiceDimension::getInputData)
                        .map(specifications -> schemaValidator.validate(specifications, params)))
                .orElse(new SchemaValidationResult());
    }
}
