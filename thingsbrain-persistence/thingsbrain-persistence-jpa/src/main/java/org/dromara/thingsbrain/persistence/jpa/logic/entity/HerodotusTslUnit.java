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
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package org.dromara.thingsbrain.persistence.jpa.logic.entity;

import org.dromara.dante.data.jpa.entity.AbstractEntity;
import org.dromara.thingsbrain.persistence.commons.constant.PersistenceConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 物联网物模型单位 Jpa 存储实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 15:33
 */
@Entity
@Table(name = "iot_tsl_unit", indexes = {@Index(name = "iot_tsl_unit_id_idx", columnList = "unit_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_UNIT)
public class HerodotusTslUnit extends AbstractEntity {

    @Id
    @UuidGenerator
    @Column(name = "unit_id", length = 64)
    private String unitId;

    @Column(name = "unit_symbol", length = 20)
    private String unitSymbol;

    @Column(name = "unit_name", length = 30)
    private String unitName;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("unitId", unitId)
                .add("unitSymbol", unitSymbol)
                .add("unitName", unitName)
                .toString();
    }
}
