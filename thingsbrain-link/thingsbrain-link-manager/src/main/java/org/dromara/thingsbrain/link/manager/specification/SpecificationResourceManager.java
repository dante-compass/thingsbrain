/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.link.manager.specification;

import org.dromara.dante.cache.jetcache.utils.JetCacheUtils;
import org.dromara.dante.core.domain.FileAttributes;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.dante.core.support.file.JsonSchemaFileManager;
import org.dromara.thingsbrain.kernel.commons.constant.KernelConstants;
import org.dromara.thingsbrain.kernel.tsl.Specification;
import org.dromara.thingsbrain.kernel.tsl.domain.EventDimension;
import org.dromara.thingsbrain.kernel.tsl.domain.ServiceDimension;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.apache.commons.lang3.ObjectUtils;

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
