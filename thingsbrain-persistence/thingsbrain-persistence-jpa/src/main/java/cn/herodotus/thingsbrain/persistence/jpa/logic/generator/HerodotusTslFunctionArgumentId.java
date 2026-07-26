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

package cn.herodotus.thingsbrain.persistence.jpa.logic.generator;

import cn.herodotus.thingsbrain.persistence.commons.enums.TslArgumentCategory;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Description: 物模型功能和参数关联联合主键 </p>
 * <p>
 * 复合主键类必须满足：
 * 1. 实现Serializable接口;
 * 2. 有默认的public无参数的构造方法;
 * 3. 重写equals和hashCode方法。equals方法用于判断两个对象是否相同，
 *
 * @author : gengwei_zheng
 * @date : 2026/7/19 15:30
 */
@Embeddable
public class HerodotusTslFunctionArgumentId implements Serializable {

    private String functionId;

    private String argumentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "argument_category", nullable = false, length = 50)
    private TslArgumentCategory category;

    public HerodotusTslFunctionArgumentId() {
    }

    public HerodotusTslFunctionArgumentId(String functionId, String argumentId, TslArgumentCategory category) {
        this.functionId = functionId;
        this.argumentId = argumentId;
        this.category = category;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getArgumentId() {
        return argumentId;
    }

    public void setArgumentId(String argumentId) {
        this.argumentId = argumentId;
    }

    public TslArgumentCategory getCategory() {
        return category;
    }

    public void setCategory(TslArgumentCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusTslFunctionArgumentId that = (HerodotusTslFunctionArgumentId) o;
        return Objects.equals(functionId, that.functionId) && Objects.equals(argumentId, that.argumentId) && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, argumentId, category);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("functionId", functionId)
                .add("argumentId", argumentId)
                .add("category", category)
                .toString();
    }
}
