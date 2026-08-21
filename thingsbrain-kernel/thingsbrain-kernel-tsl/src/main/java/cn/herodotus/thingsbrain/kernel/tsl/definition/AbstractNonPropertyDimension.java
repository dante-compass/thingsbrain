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
