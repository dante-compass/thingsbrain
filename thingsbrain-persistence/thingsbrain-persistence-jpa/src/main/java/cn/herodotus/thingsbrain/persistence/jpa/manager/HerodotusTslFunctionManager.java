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

package cn.herodotus.thingsbrain.persistence.jpa.manager;

import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunctionArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslArgumentService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionArgumentService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 物联网物模型功能 Manage </p>
 * <p>
 * 提取出 Manage 层，方便数据层面业务逻辑的视线，同时保持 Service 层代码的清爽
 *
 * @author : gengwei.zheng
 * @date : 2025/4/28 15:42
 */
public class HerodotusTslFunctionManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusTslFunctionManager.class);

    private final HerodotusTslFunctionService herodotusTslFunctionService;
    private final HerodotusTslArgumentService herodotusTslArgumentService;
    private final HerodotusTslFunctionArgumentService herodotusTslFunctionArgumentService;

    public HerodotusTslFunctionManager(HerodotusTslFunctionService herodotusTslFunctionService, HerodotusTslArgumentService herodotusTslArgumentService, HerodotusTslFunctionArgumentService herodotusTslFunctionArgumentService) {
        this.herodotusTslFunctionService = herodotusTslFunctionService;
        this.herodotusTslArgumentService = herodotusTslArgumentService;
        this.herodotusTslFunctionArgumentService = herodotusTslFunctionArgumentService;
    }

    public HerodotusTslFunctionService getHerodotusTslFunctionService() {
        return herodotusTslFunctionService;
    }

    public List<HerodotusTslFunction> findAllByProductId(String productId) {
        return herodotusTslFunctionService.findAllByProductId(productId);
    }

    public HerodotusTslFunction save(HerodotusTslFunction domain) {
        return herodotusTslFunctionService.save(domain);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByProductId(String productId) {
        herodotusTslFunctionArgumentService.deleteAllByProductId(productId);
        herodotusTslArgumentService.deleteAllByProductId(productId);
        herodotusTslFunctionService.deleteAllByProductId(productId);
    }

    /**
     * 删除其它 Property 功能。
     * <p>
     * 物模型中，当 Property 数量大于等 2 时，删除任意 Property，需要同步将 Get、Set Service 和 Post Event 与当前 Property 对应 Argument 的关联关系删除。
     *
     * @param property 任意 Property {@link HerodotusTslFunction}
     */
    private void deletesOtherProperty(HerodotusTslFunction property) {
        List<HerodotusTslFunction> requiredFunctions = herodotusTslFunctionService.findAllRequiredByProductId(property.getProductId());

        List<HerodotusTslFunction> newFunctions = requiredFunctions.stream().map(function -> function.removeArgument(property.getIdentifier())).toList();
        herodotusTslFunctionService.saveAll(newFunctions);
        // 删除当前 Property（会同步删除 HerodotusTslFunctionArgument）。
        herodotusTslFunctionService.deleteById(property.getFunctionId());
    }

    /**
     * 删除最后一个 Property 功能。
     * <p>
     * 删除最后一个 Property 时，需要将该物模型必需的 Get、Set Service 和 Post Event 同步删掉。
     * <p>
     * 主要注意顺序：
     * 1. 先删除该物模型必需的 Get、Set Service 和 Post Event，这会同步删除 {@link HerodotusTslFunctionArgument}。这会解除 Get、Set Service 和 Post Event 与 Property 参数的关系
     * 2. 再删除当前对应的 Property，即当前物模型最后一个 Property function。这个操作会同步删除 {@link HerodotusTslFunctionArgument}
     * 3. 最后删除该 Property 对应的参数 {@link HerodotusTslArgument}
     *
     * @param property 当前物模型最后一个 Property {@link HerodotusTslFunction}
     */
    private void deleteLastProperty(HerodotusTslFunction property) {
        // 先删除所有必需的 function（会同步删除 HerodotusTslFunctionArgument），即 Get、Set Service 和 Post Event
        herodotusTslFunctionService.deleteAllByProductIdAndRequired(property.getProductId());
        // 再删除当前 Property（会同步删除 HerodotusTslFunctionArgument）。
        herodotusTslFunctionService.deleteById(property.getFunctionId());
        // 最后删除对应 HerodotusTslArgument
        HerodotusTslArgument argument = property.getFirstArgument();
        if (ObjectUtils.isNotEmpty(argument)) {
            herodotusTslArgumentService.deleteById(argument.getArgumentId());
        }
    }

    private void deleteEventOrService(HerodotusTslFunction function) {
        List<String> arguments = new ArrayList<>();
        // 如果存在关联关系，则先获取到关联的 Argument
        if (CollectionUtils.isNotEmpty(function.getArguments())) {
            arguments = function.getArguments().stream()
                    .map(HerodotusTslFunctionArgument::getArgument)
                    .map(HerodotusTslArgument::getArgumentId)
                    .toList();
        }

        // 先删除当前 function，会同步解除关联关系
        herodotusTslFunctionService.deleteById(function.getFunctionId());

        // 如果存在关联 Argument 则批量删除
        if (CollectionUtils.isNotEmpty(arguments)) {
            // 注意：这里要使用 deleteAllById 而不能使用 deleteAllInBatch。
            // 联合主键关联多对多处理，手动的删除逻辑，如果以对象的方式删除，会收到 Hibernate Session 中实体对象的关联关系的影响。即使 function 已经删除，arguments 对象中还会有关联关系存在。
            // 所以手动删除时，通过 deleteBy 根据 ID 处理，可以解决外键关联问题。
            herodotusTslArgumentService.deleteAllById(arguments);
        }
    }

    private void delete(HerodotusTslFunction function) {
        if (function.getDimension() == Dimension.PROPERTY) {
            long count = herodotusTslFunctionService.findPropertyNumber(function.getProductId());
            if (count >= 2L) {
                deletesOtherProperty(function);
            } else if (count == 1L) {
                deleteLastProperty(function);
            } else {
                // 如果当前 function 是 property，但是查询数量还未为 0 或者 负数。意味着数据库或者数据本身存在较大问题，建议手动检查处理。
                log.error("[ThingsMesh] |- TSL of product [{}] maybe exist fatal error.", function.getProductId());
            }
        } else {
            deleteEventOrService(function);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        herodotusTslFunctionService.findById(id).ifPresent(this::delete);
    }
}
