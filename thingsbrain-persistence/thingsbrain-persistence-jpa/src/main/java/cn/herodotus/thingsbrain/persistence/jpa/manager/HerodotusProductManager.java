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

import cn.herodotus.dante.security.definition.AuthenticationManager;
import cn.herodotus.dante.security.domain.RegisteredClientTransmitter;
import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import cn.herodotus.thingsbrain.persistence.jpa.converter.FunctionsToSpecificationConverter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.HerodotusProductToAuthenticationConverter;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusProduct;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusProductService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>Description: 物联网产品 Manage </p>
 * <p>
 * 提取出 Manage 层，方便数据层面业务逻辑的视线，同时保持 Service 层代码的清爽
 *
 * @author : gengwei.zheng
 * @date : 2025/4/3 22:20
 */
public class HerodotusProductManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusProductManager.class);

    private final HerodotusProductService herodotusProductService;
    private final HerodotusTslFunctionService herodotusTslFunctionService;
    private final AuthenticationManager authenticationManager;
    private final Converter<HerodotusProduct, RegisteredClientTransmitter> toAuthentication;

    public HerodotusProductManager(HerodotusProductService herodotusProductService, HerodotusTslFunctionService herodotusTslFunctionService, AuthenticationManager authenticationManager) {
        this.herodotusProductService = herodotusProductService;
        this.herodotusTslFunctionService = herodotusTslFunctionService;
        this.authenticationManager = authenticationManager;
        this.toAuthentication = new HerodotusProductToAuthenticationConverter();
    }

    public HerodotusProductService getHerodotusProductService() {
        return herodotusProductService;
    }

    public HerodotusProduct save(HerodotusProduct domain) {
        return herodotusProductService.save(domain);
    }

    /**
     * 动态开启或关闭产品的认证功能
     *
     * @param newProduct 物联网产品 {@link HerodotusProduct}
     * @return 保存后的产品
     */
    public Optional<HerodotusProduct> switchAuthentication(HerodotusProduct newProduct) {
        Optional<HerodotusProduct> oldProduct = herodotusProductService.findById(newProduct.getProductId());
        return oldProduct.map(item -> switchAuthentication(item, newProduct));
    }

    /**
     * 将具体的开启或关闭认证操作提取为单独的方法，方便在 {@link Optional} 中使用
     *
     * @param oldProduct 数据库中已经存在的产品
     * @param newProduct 开启或关闭状态变更的产品
     * @return 如果开启或关闭状态确实变化了，则返回更新数据库后的产品。否则则返回数据库中原有的、开启或关闭状态变化之前的产品信息。
     */
    private HerodotusProduct switchAuthentication(HerodotusProduct oldProduct, HerodotusProduct newProduct) {

        log.debug("[ThingsBrain] |- [SWITCH-AUTHENTICATION] Checking switch authentication status.");

        // 和数据库中存储的 product getRegistration 值进行对比，如果不同就意味着状态产生了变化
        if (newProduct.getRegistration() != oldProduct.getRegistration()) {

            log.debug("[ThingsBrain] |- [SWITCH-AUTHENTICATION] Processing switch authentication.");

            // 如果是开启动态注册
            if (newProduct.getRegistration()) {
                // 向 oauth2_registered_client 添加与之对应的 client 数据，以开启动态注册
                authenticationManager.enable(toAuthentication.convert(oldProduct));
            } else {
                // 删除 oauth2_registered_client 表中与之对应的 client 数据，以关闭动态注册
                authenticationManager.disable(oldProduct.getProductId());
            }

            // 更新数据库中产品认证功能开启或关闭状态
            return herodotusProductService.save(newProduct);
        }

        return oldProduct;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        authenticationManager.disable(id);
        herodotusProductService.deleteById(id);
        herodotusTslFunctionService.deleteAllByProductId(id);
    }

    public Optional<Specification> generateSpecification(String productKey) {
        Converter<List<HerodotusTslFunction>, Specification> toSpecification = new FunctionsToSpecificationConverter(productKey);
        List<HerodotusTslFunction> functions = herodotusTslFunctionService.findAllByProductKey(productKey);
        return Optional.ofNullable(functions)
                .filter(CollectionUtils::isNotEmpty)
                .map(toSpecification::convert);
    }
}
