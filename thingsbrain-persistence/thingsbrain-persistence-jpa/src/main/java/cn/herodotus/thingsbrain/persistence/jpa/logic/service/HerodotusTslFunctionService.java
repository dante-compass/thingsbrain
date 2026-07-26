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
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.tsl.enums.AccessMode;
import cn.herodotus.thingsbrain.kernel.tsl.enums.CallType;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.EventType;
import cn.herodotus.thingsbrain.persistence.commons.enums.TslArgumentCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.repository.HerodotusTslFunctionRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public void deleteAllByProductId(String productId) {
        herodotusTslFunctionRepository.deleteAllByProductId(productId);
    }

    public void deleteAllByProductIdAndRequired(String productId) {
        herodotusTslFunctionRepository.deleteAllByProductIdAndRequired(productId, true);
    }

    public Page<HerodotusTslFunction> findByCondition(int pageNumber, int pageSize, String productId, String productKey, Boolean required) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<HerodotusTslFunction> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(productId)) {
                predicates.add(criteriaBuilder.equal(root.get("productId"), productId));
            }

            if (StringUtils.isNotBlank(productKey)) {
                predicates.add(criteriaBuilder.equal(root.get("productKey"), productKey));
            }

            if (ObjectUtils.isNotEmpty(required)) {
                predicates.add(criteriaBuilder.equal(root.get("required"), required));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public long findPropertyNumber(String productId) {
        return herodotusTslFunctionRepository.countByProductIdAndDimension(productId, Dimension.PROPERTY);
    }

    public List<HerodotusTslFunction> findAllByProductIdAndRequired(String productId) {
        return herodotusTslFunctionRepository.findAllByProductIdAndRequired(productId, true);
    }

    public List<HerodotusTslFunction> findAllByProductId(String productId) {
        return herodotusTslFunctionRepository.findAllByProductId(productId);
    }


    /**
     * 将 Property 类型功能的参数，与物模型默认的 Get、Set Service 以及 Post Event 进行关联。
     * 1. Property 的 AccessMode 为只读，则将其与标识符为 Get 的 Service 的 OutputData 进行关联。
     * 2. Property 的 AccessMode 为读写，则将其与标识符为 Get 的 Service 的 OutputData 以及与标识符为 Set 的 Service 的 InputData 进行关联。
     * 3. Property 将其与标识符为 Post 的 Event 的 OutputData 进行关联。
     *
     * @param function   Get、Set Service 或 Post Event 对应的功能 {@link HerodotusTslFunction}
     * @param argument   新增 Property 对应的 Argument {@link HerodotusTslArgument}
     * @param accessMode 新增 Property 的访问模式 {@link AccessMode}
     * @return 更新了关联关系的物模型功能  {@link HerodotusTslFunction}
     */
    private HerodotusTslFunction addArgumentToStandardFunction(HerodotusTslFunction function, HerodotusTslArgument argument, AccessMode accessMode) {
        Set<HerodotusTslFunctionArgument> functionArguments = function.getArguments();
        // 防止 function.getArguments() 为 null。
        if (CollectionUtils.isEmpty(functionArguments)) {
            functionArguments = new HashSet<>();
        }

        // 如果当前的 function 是 Event, 同时 identifier 为 'post'
        if (function.getDimension() == Dimension.EVENT && Strings.CS.equals(function.getIdentifier(), ProtocolConstants.ACTION__POST)) {
            functionArguments.add(new HerodotusTslFunctionArgument(function, argument, TslArgumentCategory.EVENTS_OUTPUT_DATA));
        }

        // 如果当前的 function 是 Service
        if (function.getDimension() == Dimension.SERVICE) {
            // 如果当前的 function 是 Service, 同时 identifier 为 'get'。不管 Property 的 AccessMode 是只读还是读写，都将其加入到 outputData 中
            if (Strings.CS.equals(function.getIdentifier(), ProtocolConstants.ACTION__GET)) {
                functionArguments.add(new HerodotusTslFunctionArgument(function, argument, TslArgumentCategory.SERVICES_OUTPUT_DATA));
            }

            // 如果当前的 function 是 Service, 同时 identifier 为 'set'。
            if (Strings.CS.equals(function.getIdentifier(), ProtocolConstants.ACTION__SET)) {
                // 如果 Property 的 AccessMode 是读写，则将其加入到 inputData 中；如果是只读，则忽略
                if (accessMode == AccessMode.READ_WRITE) {
                    functionArguments.add(new HerodotusTslFunctionArgument(function, argument, TslArgumentCategory.SERVICES_INPUT_DATA));
                }
            }
        }

        // 更新当前 function 与 argument 的关系 set
        function.setArguments(functionArguments);
        return function;
    }

    /**
     * 将 Property 类型功能的参数，与物模型默认的 Get、Set Service 以及 Post Event 进行关联。
     * 1. Property 的 AccessMode 为只读，则将其与标识符为 Get 的 Service 的 OutputData 进行关联。
     * 2. Property 的 AccessMode 为读写，则将其与标识符为 Get 的 Service 的 OutputData 以及与标识符为 Set 的 Service 的 InputData 进行关联。
     * 3. Property 将其与标识符为 Post 的 Event 的 OutputData 进行关联。
     *
     * @param target   Get、Set Service 或 Post Event 对应的功能 {@link HerodotusTslFunction}
     * @param property 新增 Property  {@link HerodotusTslFunction}
     * @return 更新了关联关系的物模型功能  {@link HerodotusTslFunction}
     */
    private HerodotusTslFunction addPropertyToStandardFunction(HerodotusTslFunction target, HerodotusTslFunction property) {
        // 从 Property 中获取到对应的 Argument。目前的设计中 Property 有且只有一个对应的 Argument。
        Optional<HerodotusTslFunctionArgument> optional = property.getArguments().stream().findFirst();

        return optional.map(HerodotusTslFunctionArgument::getArgument)
                .map(argument -> addArgumentToStandardFunction(target, argument, property.getAccessMode()))
                .orElse(null);
    }

    private HerodotusTslFunction createRequiredPostEvent(String productId, String productKey) {
        HerodotusTslFunction domain = new HerodotusTslFunction();
        domain.setProductId(productId);
        domain.setIdentifier(ProtocolConstants.ACTION__POST);
        domain.setName(ProtocolConstants.ACTION__POST);

        domain.setProductKey(productKey);
        domain.setDimension(Dimension.EVENT);
        domain.setRequired(Boolean.TRUE);
        domain.setEventType(EventType.INFO);
        domain.setMethod(MethodConstants.METHOD__THING_EVENT_PROPERTY_POST);
        domain.setDescription(ProtocolConstants.DESCRIPTION__PROPERTY_POST);
        return domain;
    }

    private HerodotusTslFunction createRequiredGetService(String productId, String productKey) {
        HerodotusTslFunction domain = new HerodotusTslFunction();
        domain.setProductId(productId);
        domain.setIdentifier(ProtocolConstants.ACTION__GET);
        domain.setName(ProtocolConstants.ACTION__GET);

        domain.setProductKey(productKey);
        domain.setDimension(Dimension.SERVICE);
        domain.setRequired(Boolean.TRUE);
        domain.setCallType(CallType.ASYNC);
        domain.setMethod(MethodConstants.METHOD__THING_SERVICE_PROPERTY_GET);
        domain.setDescription(ProtocolConstants.DESCRIPTION__PROPERTY_GET);
        return domain;
    }

    private HerodotusTslFunction createRequiredSetService(String productId, String productKey) {
        HerodotusTslFunction domain = new HerodotusTslFunction();
        domain.setProductId(productId);
        domain.setIdentifier(ProtocolConstants.ACTION__SET);
        domain.setName(ProtocolConstants.ACTION__SET);

        domain.setProductKey(productKey);
        domain.setDimension(Dimension.SERVICE);
        domain.setRequired(Boolean.TRUE);
        domain.setCallType(CallType.ASYNC);
        domain.setMethod(MethodConstants.METHOD__THING_SERVICE_PROPERTY_SET);
        domain.setDescription(ProtocolConstants.DESCRIPTION__PROPERTY_SET);
        return domain;
    }

    private void saveOrUpdateRequiredFunction(HerodotusTslFunction property) {
        List<HerodotusTslFunction> requiredFunctions = herodotusTslFunctionRepository.findAllByProductIdAndRequired(property.getProductId(), true);
        if (CollectionUtils.isEmpty(requiredFunctions)) {
            requiredFunctions = List.of(
                    createRequiredPostEvent(property.getProductId(), property.getProductKey()),
                    createRequiredGetService(property.getProductId(), property.getProductKey()),
                    createRequiredSetService(property.getProductId(), property.getProductKey()));
        }

        List<HerodotusTslFunction> newFunctions = requiredFunctions.stream()
                .map(function -> addPropertyToStandardFunction(function, property))
                .toList();
        herodotusTslFunctionRepository.saveAll(newFunctions);
    }

    @Transactional(rollbackFor = Exception.class)
    public HerodotusTslFunction save(HerodotusTslFunction domain) {
        HerodotusTslFunction function = herodotusTslFunctionRepository.save(domain);
        if (ObjectUtils.isNotEmpty(function) && domain.getDimension() == Dimension.PROPERTY) {
            saveOrUpdateRequiredFunction(function);
        }
        return function;
    }
}
