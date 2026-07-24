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

package cn.herodotus.thingsbrain.persistence.commons.domain;

import cn.herodotus.dante.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 物联网物模型功能参数参数统一实体定义 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/20 13:09
 */
@Schema(name = "物联网物模型功能参数参数统一实体定义")
public class TslFunctionArgument implements BaseEntity {

    @Schema(name = "Property 对应参数")
    private TslArgument property;

    @Schema(name = "Event 输出数据对应参数")
    private List<TslArgument> eventOutputData = new ArrayList<>();

    @Schema(name = "Service 输出数据对应参数")
    private List<TslArgument> serviceOutputData = new ArrayList<>();

    @Schema(name = "参数类别")
    private List<TslArgument> serviceInputData = new ArrayList<>();

    public TslArgument getProperty() {
        return property;
    }

    public void setProperty(TslArgument property) {
        this.property = property;
    }

    public List<TslArgument> getEventOutputData() {
        return eventOutputData;
    }

    public void setEventOutputData(List<TslArgument> eventOutputData) {
        this.eventOutputData = eventOutputData;
    }

    public List<TslArgument> getServiceOutputData() {
        return serviceOutputData;
    }

    public void setServiceOutputData(List<TslArgument> serviceOutputData) {
        this.serviceOutputData = serviceOutputData;
    }

    public List<TslArgument> getServiceInputData() {
        return serviceInputData;
    }

    public void setServiceInputData(List<TslArgument> serviceInputData) {
        this.serviceInputData = serviceInputData;
    }

    public void appendEventOutputData(TslArgument tslArgument) {
        this.eventOutputData.add(tslArgument);
    }

    public void appendServiceOutputData(TslArgument tslArgument) {
        this.serviceOutputData.add(tslArgument);
    }

    public void appendServiceInputData(TslArgument tslArgument) {
        this.serviceInputData.add(tslArgument);
    }
}
