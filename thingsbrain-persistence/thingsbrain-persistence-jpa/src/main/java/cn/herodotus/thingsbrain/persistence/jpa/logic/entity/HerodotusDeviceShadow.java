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

import cn.herodotus.dante.data.jpa.entity.AbstractAuditEntity;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.hutool.v7.core.data.id.IdUtil;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 设备影子数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:22
 */
@Entity
@Table(name = "iot_device_shadow", indexes = {
        @Index(name = "iot_device_shadow_id_idx", columnList = "shadow_id"),
        @Index(name = "iot_device_shadow_pdk_idx", columnList = "product_key"),
        @Index(name = "iot_device_shadow_dn_idx", columnList = "device_name"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_SHADOW)
public class HerodotusDeviceShadow extends AbstractAuditEntity {

    @Id
    @UuidGenerator
    @Column(name = "shadow_id", length = 64)
    private String shadowId;

    @Column(name = "product_key", length = 32)
    private String productKey = IdUtil.fastSimpleUUID();

    @Column(name = "device_name", length = 32)
    private String deviceName = IdUtil.fastSimpleUUID();

    @Column(name = "version")
    private Integer version = KernelConstants.VALUE__SHADOW_CLEAR_RESULT;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content = KernelConstants.SHADOW__EMPTY;

    public String getShadowId() {
        return shadowId;
    }

    public void setShadowId(String shadowId) {
        this.shadowId = shadowId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("shadowId", shadowId)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .add("version", version)
                .add("content", content)
                .toString();
    }
}
