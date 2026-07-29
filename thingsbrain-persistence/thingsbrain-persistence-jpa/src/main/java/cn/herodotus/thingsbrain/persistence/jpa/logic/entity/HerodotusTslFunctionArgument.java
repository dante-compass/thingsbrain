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

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.herodotus.thingsbrain.persistence.commons.enums.TslArgumentCategory;
import cn.herodotus.thingsbrain.persistence.jpa.logic.generator.HerodotusTslFunctionArgumentId;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

/**
 * <p>Description: 物模型功能与参数多对多关联关系表 </p>
 * <p>
 * 采用联合主键方式实现多对多关联，function_id、argument_id 和 argument_category 共同组成联合主键
 *
 * @author : gengwei_zheng
 * @date : 2026/7/19 15:41
 */
@Entity
@Table(name = "iot_tsl_function_argument",
        indexes = {
                @Index(name = "iot_tsl_function_argument_fid_idx", columnList = "function_id"),
                @Index(name = "iot_tsl_function_argument_aid_idx", columnList = "argument_id"),
                @Index(name = "iot_tsl_function_argument_c_idx", columnList = "argument_category"),
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"function_id", "argument_id", "argument_category"})})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_FUNCTION_ARGUMENT)
public class HerodotusTslFunctionArgument extends AbstractTslEntity {

    @EmbeddedId
    private HerodotusTslFunctionArgumentId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("functionId")
    @JoinColumn(name = "function_id", nullable = false, insertable = false, updatable = false)
    private HerodotusTslFunction function;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("argumentId")
    @JoinColumn(name = "argument_id", nullable = false, insertable = false, updatable = false)
    private HerodotusTslArgument argument;

    public HerodotusTslFunctionArgument() {
    }

    public HerodotusTslFunctionArgument(HerodotusTslFunction function, HerodotusTslArgument argument, TslArgumentCategory category) {
        this.function = function;
        this.argument = argument;
        this.id = new HerodotusTslFunctionArgumentId(function.getFunctionId(), argument.getArgumentId(), category);
        setProductId(function.getProductId());
    }

    public HerodotusTslFunctionArgumentId getId() {
        return id;
    }

    public void setId(HerodotusTslFunctionArgumentId id) {
        this.id = id;
    }

    public HerodotusTslFunction getFunction() {
        return function;
    }

    public void setFunction(HerodotusTslFunction function) {
        this.function = function;
    }

    public HerodotusTslArgument getArgument() {
        return argument;
    }

    public void setArgument(HerodotusTslArgument argument) {
        this.argument = argument;
    }

    public TslArgumentCategory getCategory() {
        if (ObjectUtils.isNotEmpty(this.id)) {
            return this.id.getCategory();
        }

        return TslArgumentCategory.PROPERTIES;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusTslFunctionArgument that = (HerodotusTslFunctionArgument) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("function", function)
                .add("argument", argument)
                .toString();
    }
}
