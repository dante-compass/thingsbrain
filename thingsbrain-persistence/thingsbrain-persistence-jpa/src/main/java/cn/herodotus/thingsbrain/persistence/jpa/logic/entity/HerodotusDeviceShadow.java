/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsMesh.
 *
 * Herodotus ThingsMesh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsMesh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.dante.data.jpa.entity.AbstractAuditEntity;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 设备影子数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:22
 */
@Entity
@Table(name = "iot_device_shadow", indexes = {
        @Index(name = "iot_device_shadow_id_idx", columnList = "shadow_id"),
        @Index(name = "iot_device_shadow_pdk_idx", columnList = "product_key"),
        @Index(name = "iot_device_shadow_dn_idx", columnList = "device_name"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_SHADOW)
public class HerodotusDeviceShadow extends AbstractAuditEntity {

    @Id
    @UuidGenerator
    @Column(name = "shadow_id", length = 64)
    private String shadowId;

    @Column(name = "product_key", length = 32)
    private String productKey = IdUtil.fastSimpleUUID();

    @Column(name = "device_name", length = 32)
    private String deviceName = IdUtil.fastSimpleUUID();

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public String getShadowId() {
        return shadowId;
    }

    public void setShadowId(String shadowId) {
        this.shadowId = shadowId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("shadowId", shadowId)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .add("content", content)
                .addValue(super.toString())
                .toString();
    }
}
