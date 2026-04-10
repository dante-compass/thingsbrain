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

package org.dromara.thingsbrain.persistence.commons.domain;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.dante.data.commons.entity.AbstractSysEntity;
import org.dromara.thingsbrain.persistence.commons.enums.Action;
import org.dromara.thingsbrain.persistence.commons.enums.Area;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 物联网 Mqtt 主题分类信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:52
 */
@Schema(name = "物联网 Mqtt 主题分类信息统一实体定义")
public class MqttCategory extends AbstractSysEntity {

    @Schema(name = "Mqtt主题分类ID")
    private String id;

    @Schema(name = "Mqtt主题分类名称")
    private String name;

    @Schema(name = "是否为默认分类")
    private Boolean standard = Boolean.FALSE;

    @Schema(name = "分类用途")
    private Area area = Area.DEVICE;

    /**
     * 该 Action 与 MqttAuthority 中的 Action 用途不同。
     * 1. 该 Action 用于区分 MqttCategory 中的类型。可以通过该 Action 筛选不同的 MqttAction,例如：可以通过该字段查询订阅主题，让平台动态订阅。
     * 2. MqttAuthority 中的 Action 用于定义主题的 ACL 权限。目前主要用于 Emqx 鉴权。
     */
    @Schema(name = "主题操作类型")
    private Action action;

    @Schema(name = "分类权限")
    private Set<MqttAuthority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStandard() {
        return standard;
    }

    public void setStandard(Boolean standard) {
        this.standard = standard;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Set<MqttAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<MqttAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("standard", standard)
                .add("area", area)
                .add("action", action)
                .addValue(super.toString())
                .toString();
    }
}
