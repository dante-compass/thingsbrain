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

import cn.herodotus.thingsbrain.kernel.tsl.enums.ArgumentType;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

/**
 * <p>Description: 物联网物模型参数 Jpa 存储实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/6 8:05
 */
@Entity
@Table(name = "iot_tsl_argument", indexes = {@Index(name = "iot_tsl_argument_id_idx", columnList = "argument_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_ARGUMENT)
public class HerodotusTslArgument extends AbstractTslCharacteristic {

    @Id
    @UuidGenerator
    @Column(name = "argument_id", length = 64)
    private String argumentId;

    @Column(name = "argument_type", length = 50)
    @Enumerated(EnumType.STRING)
    private ArgumentType type;

    @Column(name = "argument_specs", columnDefinition = "TEXT")
    private String specs;

    public String getArgumentId() {
        return argumentId;
    }

    public void setArgumentId(String argumentId) {
        this.argumentId = argumentId;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusTslArgument argument = (HerodotusTslArgument) o;
        return Objects.equals(argumentId, argument.argumentId) && Objects.equals(getProductId(), argument.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(argumentId, getProductId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("argumentId", argumentId)
                .add("type", type)
                .add("specs", specs)
                .addValue(super.toString())
                .toString();
    }
}
