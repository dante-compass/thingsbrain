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

package cn.herodotus.thingsbrain.kernel.tsl.domain;

import cn.herodotus.thingsbrain.kernel.tsl.definition.AbstractDimension;
import cn.herodotus.thingsbrain.kernel.tsl.enums.CallType;
import cn.herodotus.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: 物模型 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 20:35
 */
public class ServiceDimension extends AbstractDimension {
    /**
     * async（异步调用）或sync（同步调用）
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private CallType callType;

    @JsonView(SpecificationViews.CompleteView.class)
    private List<Argument> inputData = new LinkedList<>();

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public List<Argument> getInputData() {
        return inputData;
    }

    public void setInputData(List<Argument> inputData) {
        this.inputData = inputData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("callType", callType)
                .toString();
    }
}
