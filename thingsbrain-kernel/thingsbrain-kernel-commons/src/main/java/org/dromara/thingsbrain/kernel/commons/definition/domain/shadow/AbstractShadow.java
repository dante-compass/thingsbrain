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

package org.dromara.thingsbrain.kernel.commons.definition.domain.shadow;

import org.dromara.dante.core.constant.SymbolConstants;
import org.dromara.dante.core.domain.BaseEntity;
import org.dromara.thingsbrain.kernel.commons.constant.ProtocolConstants;
import org.apache.commons.lang3.Strings;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 设备影子核心数据抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/3 22:24
 */
public abstract class AbstractShadow implements BaseEntity {

    private State state;
    private Metadata metadata;
    private Long version = 0L;

    protected AbstractShadow() {
        this.state = new State();
        this.metadata = new Metadata();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private Map<String, MetadataTimestamp> toMetadata(Map<String, Object> data) {
        return data.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new MetadataTimestamp()));
    }

    protected void update(State state) {
        if (state.justReported()) {
            Map<String, Object> stateData = state.getReported();
            this.state.getReported().putAll(stateData);
            this.metadata.getReported().putAll(toMetadata(stateData));
        }

        if (state.justDesired()) {
            Map<String, Object> stateData = state.getDesired();
            this.state.getDesired().putAll(stateData);
            this.metadata.getDesired().putAll(toMetadata(stateData));
        }
    }

    private void removeReported(String key) {
        this.state.getReported().remove(key);
        this.metadata.getReported().remove(key);
    }

    private void removeAllReported() {
        this.state.setReported(Map.of());
        this.metadata.setReported(Map.of());
    }

    private void removeAllDesired() {
        this.state.setDesired(Map.of());
        this.metadata.setDesired(Map.of());
    }

    /**
     * 判断是否为删除影子全部属性。
     *
     * @param state 请求参数 {@link State}
     * @return true 删除全部属性，false 删除指定属性。
     */
    private boolean isRemoveAllReported(State state) {
        return state.isNull() && state.containsKey(ProtocolConstants.PARAMETER__REPORTED) && Strings.CS.equals((String) state.get(ProtocolConstants.PARAMETER__REPORTED), SymbolConstants.NULL);
    }

    /**
     * 判断是否为删除影子全部属性。
     *
     * @param state 请求参数 {@link State}
     * @return true 删除全部属性，false 删除指定属性。
     */
    private boolean isRemoveAllDesired(State state) {
        return state.isNull() && state.containsKey(ProtocolConstants.PARAMETER__DESIRED) && Strings.CS.equals((String) state.get(ProtocolConstants.PARAMETER__DESIRED), SymbolConstants.NULL);
    }

    protected void delete(State state) {

        if (isRemoveAllReported(state)) {
            removeAllReported();
        } else {
            state.getReported().entrySet()
                    .stream()
                    .filter(entry -> Strings.CS.equals((String) entry.getValue(), SymbolConstants.NULL))
                    .forEach(entry -> removeReported(entry.getKey()));
        }

        if (isRemoveAllDesired(state)) {
            removeAllDesired();
        }
    }
}
