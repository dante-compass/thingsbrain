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

package org.dromara.thingsbrain.link.manager.specification;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.cache.jetcache.utils.JetCacheUtils;
import org.dromara.dante.core.domain.FileAttributes;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.dante.core.support.file.JsonSchemaFileManager;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.tsl.Specification;
import cn.herodotus.thingsbrain.kernel.tsl.domain.EventDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;

import java.io.IOException;
import java.util.Optional;

/**
 * <p>Description: 默认的物模型声明资源管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/20 15:59
 */
public class SpecificationResourceManager {

    private final SpecificationCacheManager specificationCacheManager;
    private final JsonSchemaFileManager jsonSchemaFileManager;

    public SpecificationResourceManager(JsonSchemaFileManager jsonSchemaFileManager) {
        this.specificationCacheManager = new SpecificationCacheManager();
        this.jsonSchemaFileManager = jsonSchemaFileManager;
    }

    /**
     * 从将物模型声明对象 {@link Specification} 以 JSON 文件形式存储，并放入缓存中进行备用。
     *
     * @param productKey    物模型 ProductKey
     * @param specification 声明对象 {@link Specification}
     */
    public boolean put(String productKey, Specification specification) {
        try {
            FileAttributes isSuccess = jsonSchemaFileManager.writeString(productKey, () -> JacksonUtils.toJson(specification));
            if (ObjectUtils.isNotEmpty(isSuccess)) {
                specificationCacheManager.put(productKey, specification);
                return true;
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    /**
     * 根据 JSON 文件或者数据库内容，重新加载物模型声明对象 {@link Specification}
     *
     * @param productKey 物联网 ProductKey
     * @return 物模型声明对象 {@link Optional}
     */
    private Optional<Specification> reload(String productKey) {
        // 如果缓存中没有对应 Specification。则去读取对应的 JSON 文件。
        try {
            String json = jsonSchemaFileManager.readString(productKey);
            return Optional.ofNullable(json)
                    .map(data -> JacksonUtils.toObject(json, Specification.class))
                    .map(specification -> {
                        specificationCacheManager.put(productKey, specification);
                        return specification;
                    });
        } catch (IOException e) {
            return Optional.empty();
        }


    }

    public Optional<Specification> get(String productKey) {
        // 根据 productKey 从缓存中读取对应 Specification
        // 如果缓存中不存在则重新从资源中载入
        return specificationCacheManager.get(productKey).or(() -> reload(productKey));
    }

    public Optional<ServiceDimension> findService(String productKey, String identifier) {
        return get(productKey)
                .flatMap(specification -> specification.getServices().stream()
                        .filter(service -> service.getIdentifier().equals(identifier))
                        .findFirst());
    }

    public Optional<EventDimension> findEvent(String productKey, String identifier) {
        return get(productKey)
                .flatMap(specification -> specification.getEvents().stream()
                        .filter(service -> service.getIdentifier().equals(identifier))
                        .findFirst());
    }

    static class SpecificationCacheManager {

        private final Cache<String, Specification> cache;

        public SpecificationCacheManager() {
            this.cache = JetCacheUtils.create(KernelConstants.CACHE_NAME_IOT_TSL_SPECIFICATION, CacheType.BOTH, null, true);
        }

        public Optional<Specification> get(String key) {
            return Optional.ofNullable(this.cache.get(key));
        }

        public void put(String key, Specification specification) {
            this.cache.put(key, specification);
        }

        public void remove(String key) {
            this.cache.remove(key);
        }
    }
}
