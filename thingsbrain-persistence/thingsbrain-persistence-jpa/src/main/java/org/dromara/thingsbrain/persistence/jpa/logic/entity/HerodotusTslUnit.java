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

package org.dromara.thingsbrain.persistence.jpa.logic.entity;

import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.dromara.dante.data.jpa.entity.AbstractEntity;
import org.dromara.thingsbrain.persistence.commons.constant.PersistenceConstants;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 物联网物模型单位 Jpa 存储实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 15:33
 */
@Entity
@Table(name = "iot_tsl_unit", indexes = {@Index(name = "iot_tsl_unit_id_idx", columnList = "unit_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TSL_UNIT)
public class HerodotusTslUnit extends AbstractEntity {

    @Id
    @UuidGenerator
    @Column(name = "unit_id", length = 64)
    private String unitId;

    @Column(name = "unit_symbol", length = 20)
    private String unitSymbol;

    @Column(name = "unit_name", length = 30)
    private String unitName;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("unitId", unitId)
                .add("unitSymbol", unitSymbol)
                .add("unitName", unitName)
                .toString();
    }
}
