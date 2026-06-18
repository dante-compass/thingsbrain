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

package org.dromara.thingsbrain.link.commons.definition;

import cn.herodotus.thingsbrain.persistence.commons.service.ProductService;

import java.util.Map;

/**
 * <p>Description: 物模型管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/25 17:20
 */
public interface SpecificationManager {

    /**
     * 获取物联网产品 Service {@link ProductService}
     * <p>
     * 定义该方法方便调用，减少不必要的 Bean 注入。
     *
     * @return 物联网产品 Service
     */
    ProductService getProductService();

    /**
     * 产品发布
     *
     * @param productKey 物联网产品 ProductKey
     * @return 发布是否成功。True 成功，False 失败。
     */
    boolean release(String productKey);

    /**
     * 校验物模型 Service 参数
     *
     * @param productKey 物联网产品 ProductKey
     * @param identifier 物模型标识符
     * @param params     传入参数 {@link Map}
     */
    void verification(String productKey, String identifier, Map<String, Object> params);
}
