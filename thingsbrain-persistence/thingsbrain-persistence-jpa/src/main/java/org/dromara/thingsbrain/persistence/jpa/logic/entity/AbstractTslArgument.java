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

import org.dromara.dante.data.jpa.entity.AbstractAuditEntity;
import org.dromara.thingsbrain.kernel.tsl.enums.ArgumentType;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

/**
 * <p>Description: 物模型 Attribute 公共属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/20 17:23
 */
@MappedSuperclass
public abstract class AbstractTslArgument extends AbstractAuditEntity {

    @Column(name = "identifier", length = 50)
    private String identifier;

    @Column(name = "argument_name", length = 30)
    private String name;

    @Column(name = "argument_type")
    @Enumerated(EnumType.STRING)
    private ArgumentType type;

    @Column(name = "argument_specs", columnDefinition = "TEXT")
    private String specs;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String argumentName) {
        this.name = argumentName;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType argumentType) {
        this.type = argumentType;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String argumentSpecs) {
        this.specs = argumentSpecs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("identifier", identifier)
                .add("name", name)
                .add("type", type)
                .add("specs", specs)
                .toString();
    }
}
