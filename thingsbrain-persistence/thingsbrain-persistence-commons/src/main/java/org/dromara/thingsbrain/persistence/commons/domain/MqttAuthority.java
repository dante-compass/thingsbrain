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
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.data.commons.entity.AbstractSysEntity;
import org.dromara.thingsbrain.kernel.commons.definition.domain.SubscribeTopic;
import org.dromara.thingsbrain.persistence.commons.enums.Action;
import org.dromara.thingsbrain.persistence.commons.enums.Permission;
import org.dromara.thingsbrain.persistence.commons.enums.Qos;
import org.dromara.thingsbrain.persistence.commons.enums.Retain;

/**
 * <p>Description: 物联网 Mqtt 权限信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:52
 */
@Schema(name = "物联网 Mqtt 权限信息统一实体定义")
public class MqttAuthority extends AbstractSysEntity implements SubscribeTopic {

    @Schema(name = "Mqtt权限ID")
    private String id;

    @Schema(name = "Mqtt主题")
    private String topic;

    @Schema(name = "权限")
    private Permission permission = Permission.allow;

    @Schema(name = "操作")
    private Action action;

    @Schema(name = "QOS")
    private Qos qos;

    @Schema(name = "是否为保留数据")
    private Retain retain = Retain.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public int getQuality() {
        if (ObjectUtils.isNotEmpty(getQos())) {
            return getQos().ordinal();
        }
        return Qos.QOS_1.ordinal();
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Qos getQos() {
        return qos;
    }

    public void setQos(Qos qos) {
        this.qos = qos;
    }

    public Retain getRetain() {
        return retain;
    }

    public void setRetain(Retain retain) {
        this.retain = retain;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("topic", topic)
                .add("permission", permission)
                .add("action", action)
                .add("qos", qos)
                .add("retain", retain)
                .addValue(super.toString())
                .toString();
    }
}
