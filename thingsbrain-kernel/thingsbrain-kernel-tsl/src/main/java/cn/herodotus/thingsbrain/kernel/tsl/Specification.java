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

package cn.herodotus.thingsbrain.kernel.tsl;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;
import cn.herodotus.thingsbrain.kernel.tsl.domain.EventDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.Profile;
import cn.herodotus.thingsbrain.kernel.tsl.domain.PropertyDimension;
import cn.herodotus.thingsbrain.kernel.tsl.domain.ServiceDimension;
import cn.herodotus.thingsbrain.kernel.tsl.jackson2.SpecificationViews;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: TSL 模型定义 </p>
 * <p>
 * 物模型TSL（Thing Specification Language）是一个JSON格式的文件，它是物理空间中的实体，
 * 如传感器、车载装置、楼宇、工厂等在云端的数字化表示，从属性、服务和事件三个维度，分别描述了该实体是什么、能做什么、可以对外提供哪些信息
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:41
 */
public class Specification {

    @JsonView(SpecificationViews.CompleteView.class)
    private String schema = "https://iotx-tsl.oss-ap-southeast-1.aliyuncs.com/schema.json";
    @JsonView(SpecificationViews.CompleteView.class)
    private Profile profile;
    @JsonView(SpecificationViews.SimpleView.class)
    private List<PropertyDimension> properties = new LinkedList<>();
    @JsonView(SpecificationViews.SimpleView.class)
    private List<EventDimension> events = new LinkedList<>();
    @JsonView(SpecificationViews.SimpleView.class)
    private List<ServiceDimension> services = new LinkedList<>();

    public List<EventDimension> getEvents() {
        return events;
    }

    public void setEvents(List<EventDimension> events) {
        this.events = events;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<PropertyDimension> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDimension> properties) {
        this.properties = properties;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<ServiceDimension> getServices() {
        return services;
    }

    public void setServices(List<ServiceDimension> services) {
        this.services = services;
    }

    public void add(PropertyDimension dimension) {
        this.properties.add(dimension);
    }

    public void add(EventDimension dimension) {
        this.events.add(dimension);
    }

    public void add(ServiceDimension dimension) {
        this.services.add(dimension);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schema", schema)
                .add("profile", profile)
                .toString();
    }
}
