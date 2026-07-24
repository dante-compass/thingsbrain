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

package cn.herodotus.thingsbrain.persistence.jpa.logic.repository;

import cn.herodotus.dante.data.jpa.repository.BaseJpaRepository;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.generator.HerodotusTslFunctionArgumentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Description: 物联网物模型功能与参数关联 Jpa 存储 Repository </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/20 13:03
 */
public interface HerodotusTslFunctionArgumentRepository extends BaseJpaRepository<HerodotusTslFunctionArgument, HerodotusTslFunctionArgumentId> {

    /**
     * 根据 ProductId 删除对应物模型配置。
     * <p>
     * 因为改操作通常会与 Product 的删除同时使用，所以使用 ProductId，方便操作
     *
     * @param productId 物联网 ProductId
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from HerodotusTslFunctionArgument f where f.productId = : productId")
    void deleteAllByProductId(@Param("productId") String productId);
}
