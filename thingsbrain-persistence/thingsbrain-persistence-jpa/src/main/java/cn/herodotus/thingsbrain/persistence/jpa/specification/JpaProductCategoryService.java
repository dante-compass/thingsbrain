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

package cn.herodotus.thingsbrain.persistence.jpa.specification;

import cn.herodotus.thingsbrain.persistence.commons.domain.ProductCategory;
import cn.herodotus.thingsbrain.persistence.commons.service.ProductCategoryService;
import cn.herodotus.thingsbrain.persistence.jpa.converter.FromProductCategoryConverter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.ToProductCategoryConverter;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusProductCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusProductCategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * <p>Description: 物联网产品分类 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/3/29 11:34
 */
public class JpaProductCategoryService implements ProductCategoryService {

    private final HerodotusProductCategoryService delegate;
    private final Converter<HerodotusProductCategory, ProductCategory> toProductCategory;
    private final Converter<ProductCategory, HerodotusProductCategory> fromProductCategory;

    public JpaProductCategoryService(HerodotusProductCategoryService herodotusProductCategoryService) {
        this.delegate = herodotusProductCategoryService;
        this.toProductCategory = new ToProductCategoryConverter();
        this.fromProductCategory = new FromProductCategoryConverter();
    }

    @Override
    public Page<ProductCategory> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusProductCategory> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toProductCategory::convert);
    }

    @Override
    public Page<ProductCategory> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusProductCategory> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toProductCategory::convert);
    }

    @Override
    public ProductCategory save(ProductCategory domain) {
        HerodotusProductCategory entity = delegate.save(fromProductCategory.convert(domain));
        return toProductCategory.convert(entity);
    }

    @Override
    public void deleteById(String id) {
        delegate.deleteById(id);
    }
}
