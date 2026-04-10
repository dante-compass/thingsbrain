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

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 物联网 Mqtt 连接账号信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 21:43
 */
@Schema(name = "物联网 Mqtt 连接账号信息统一实体定义")
public class MqttAccount extends AbstractSysEntity {

    @Schema(name = "Mqtt账号ID")
    private String id;

    @Schema(name = "Mqtt客户端ID")
    private String clientId;

    @Schema(name = "Mqtt用户名")
    private String username;

    @Schema(name = "Mqtt密码")
    private String password;

    @Schema(name = "主题分类")
    private Set<MqttCategory> categories = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<MqttCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<MqttCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("clientId", clientId)
                .add("username", username)
                .add("password", password)
                .addValue(super.toString())
                .toString();
    }
}
