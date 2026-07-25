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
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>Description: 物联网物模型模块 Jpa 存储 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 22:59
 */
public interface HerodotusTslFunctionRepository extends BaseJpaRepository<HerodotusTslFunction, String> {

    /**
     * 根据 ProductId 删除对应物模型配置。
     * <p>
     * 因为改操作通常会与 Product 的删除同时使用，所以使用 ProductId，方便操作
     *
     * @param productId 物联网 ProductId
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from HerodotusTslFunction f where f.productId = :productId")
    void deleteAllByProductId(@Param("productId") String productId);

    /**
     * 根据 ProductId 和 Required 删除对应物模型配置。
     * <p>
     * 该方法在删除 function 数据同时，会删除关系表中的数据。不能使用 @Modifying + @Query 的方式。如果使用之后，那么在删除时会出现外键关系关联错误。
     * <p>
     * 如果追求性能，后续可以考虑，在 function_argument 中间表中也增加一个 required 字段。然后手动先删除中间表数据。
     *
     * @param productId 物联网 ProductId
     * @param required  是否为必需
     */
    void deleteAllByProductIdAndRequired(@Param("productId") String productId, @Param("required") Boolean required);

    /**
     * 条件分页查询物模型功能。
     * <p>
     * 1. 通过覆盖原始 findAll 方法，来支持 @EntityGraph
     * 2. 物模型都是与产品绑定，所以常规翻页需要按 productId 显示。
     *
     * @param specification must not be {@literal null}.
     * @param pageable      must not be {@literal null}.
     * @return 物模型功能分页 {@link Page<HerodotusTslFunction>}
     */
    @Override
    @EntityGraph(PersistenceConstants.ENTITY_GRAPH_TSL_FUNCTION_WITH_ARGUMENTS)
    Page<HerodotusTslFunction> findAll(Specification<HerodotusTslFunction> specification, Pageable pageable);

    /**
     * 根据 Dimension {@link Dimension} 查询物模型中对应功能的数量
     * <p>
     * count 操作无需使用 {@link EntityGraph}。count 查询的目的是统计记录总数。它只需要一个数值结果，不会返回或处理任何 实体实例
     *
     * @param productId 物联网 ProductId
     * @param dimension 物模型维度 {@link Dimension}
     * @return 功能的数量
     */
    long countByProductIdAndDimension(String productId, Dimension dimension);

    /**
     * 根据 ProductId 和 required 标识符查询必需的 services 和 events
     * <p>
     * 只要物模型中添加了属性，那么就需要为其生成配套的 Get、Set Service 和 Post Event。这几个物模型功能，被称之为标准功能
     *
     * @param productId 物联网 ProductId
     * @param required  是否为必须功能
     * @return 必需功能列表
     */
    @EntityGraph(PersistenceConstants.ENTITY_GRAPH_TSL_FUNCTION_WITH_ARGUMENTS)
    List<HerodotusTslFunction> findAllByProductIdAndRequired(String productId, boolean required);

    /**
     * 根据 ProductId 查询对应产品下物模型所有功能
     *
     * @param productId 物联网 ProductId
     * @return 物模型功能列表
     */
    @EntityGraph(PersistenceConstants.ENTITY_GRAPH_TSL_FUNCTION_WITH_ARGUMENTS)
    List<HerodotusTslFunction> findAllByProductId(String productId);
}
