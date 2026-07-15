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

import cn.herodotus.dante.data.jpa.entity.AbstractSysEntity;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Area;
import cn.herodotus.thingsbrain.persistence.commons.enums.Purpose;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: Mqtt 主题类别 </p>
 * <p>
 * 类似于 RBAC 权限模型的 Role。方便主题的管理。
 *
 * @author : gengwei.zheng
 * @date : 2025/9/29 22:39
 */
@Entity
@Table(name = "iot_mqtt_category", indexes = {
        @Index(name = "iot_mqtt_category_id_idx", columnList = "category_id"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_MQTT_CATEGORY)
public class HerodotusMqttCategory extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "category_id", length = 64)
    private String categoryId;

    @Column(name = "category_name", length = 128)
    private String categoryName;

    @Column(name = "area", length = 50)
    @Enumerated(EnumType.STRING)
    private Area area = Area.DEVICE;

    @Column(name = "action", length = 50)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(name = "purpose", length = 50)
    @Enumerated(EnumType.STRING)
    private Purpose purpose = Purpose.LINK;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("categoryId", categoryId)
                .add("categoryName", categoryName)
                .add("area", area)
                .add("action", action)
                .add("purpose", purpose)
                .addValue(super.toString())
                .toString();
    }
}
