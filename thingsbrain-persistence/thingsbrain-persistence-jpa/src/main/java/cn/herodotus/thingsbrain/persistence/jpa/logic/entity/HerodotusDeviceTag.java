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
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.dromara.dante.data.commons.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: {@link HerodotusDevice} 和 {@link HerodotusTag} 中间关系表</p>
 * <p>
 * 这里没有采用 @ManyToMany 的方式，而是改为拆分为两个 @OneToMany 的方式。
 * 之所以这样做：
 * · 一方面：支持双向查询
 * · 另一方面：规避 @ManyToMany 自身双向带来的性能问题
 *
 * @author : gengwei.zheng
 * @date : 2025/6/10 23:44
 */
@Entity
@Table(name = "iot_device_tag", indexes = {
        @Index(name = "iot_device_tag_id_idx", columnList = "id"),
        @Index(name = "iot_device_tag_did_idx", columnList = "device_id"),
        @Index(name = "iot_device_tag_tid_idx", columnList = "tag_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TAG)
public class HerodotusDeviceTag extends AbstractEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", length = 64)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private HerodotusDevice device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private HerodotusTag tag;

    public HerodotusDeviceTag() {
    }

    public HerodotusDeviceTag(HerodotusDevice device, HerodotusTag tag) {
        this.device = device;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HerodotusDevice getDevice() {
        return device;
    }

    public void setDevice(HerodotusDevice device) {
        this.device = device;
    }

    public HerodotusTag getTag() {
        return tag;
    }

    public void setTag(HerodotusTag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("device", device)
                .add("tag", tag)
                .addValue(super.toString())
                .toString();
    }
}
