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

package cn.herodotus.thingsbrain.persistence.commons.service;

import cn.herodotus.dante.data.commons.service.BaseWriteAndPageService;
import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import cn.herodotus.thingsbrain.persistence.commons.domain.Product;
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

    /**
     * 生成物模型声明
     *
     * @param productKey 物联网 ProductKey
     * @return 物模型声明对象
     */
    Optional<Specification> generate(String productKey);
}
