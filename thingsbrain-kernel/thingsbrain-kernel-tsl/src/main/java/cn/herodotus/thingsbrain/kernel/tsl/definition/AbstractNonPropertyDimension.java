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

package cn.herodotus.thingsbrain.kernel.tsl.definition;

import cn.herodotus.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: 非属性维度通用属性抽象定义 </p>
 * <p>
 * Property 维度没有，Service 和 Event 有的共性属性
 *
 * @author : gengwei_zheng
 * @date : 2026/7/22 15:02
 */
public abstract class AbstractNonPropertyDimension extends AbstractDimension {

    /**
     * 描述
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private String desc;
    /**
     * 服务对应的方法名称（根据identifier生成）
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private String method;
    /**
     * 输出数据
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private List<Argument> outputData = new LinkedList<>();

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Argument> getOutputData() {
        return outputData;
    }

    public void setOutputData(List<Argument> outputData) {
        this.outputData = outputData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("desc", desc)
                .add("method", method)
                .addValue(super.toString())
                .toString();
    }
}
