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
import cn.herodotus.thingsbrain.persistence.jpa.logic.generator.HerodotusTagUuidGenerator;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import cn.herodotus.dante.data.commons.entity.AbstractAuditEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 设备标签 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/10 23:43
 */
@Entity
@NamedEntityGraph(
        name = "Tag.devices",
        attributeNodes = {
                @NamedAttributeNode(value = "deviceTags", subgraph = "deviceTags.device")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "deviceTags.device",
                        attributeNodes = @NamedAttributeNode("device")
                )
        }
)
@Table(name = "iot_tag", indexes = {@Index(name = "iot_tag_id_idx", columnList = "tag_id"), @Index(name = "iot_tag_key_idx", columnList = "tag_key")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_TAG)
public class HerodotusTag extends AbstractAuditEntity {

    @Id
    @HerodotusTagUuidGenerator
    @Column(name = "tag_id", length = 64)
    private String tagId;

    @Column(name = "tag_key", length = 32, unique = true)
    private String tagKey;

    @Column(name = "tag_value", length = 100)
    private String tagValue;

    // 避免级联删除，由关联方管理关系
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_DEVICE_TAG)
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HerodotusDeviceTag> deviceTags = new HashSet<>();

    /**
     * 便捷方法
     *
     * @return 标签集合
     */
    @Transient
    public Set<HerodotusDevice> getDevices() {
        return deviceTags.stream()
                .map(HerodotusDeviceTag::getDevice)
                .collect(Collectors.toSet());
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public Set<HerodotusDeviceTag> getDeviceTags() {
        return deviceTags;
    }

    public void setDeviceTags(Set<HerodotusDeviceTag> deviceTags) {
        this.deviceTags = deviceTags;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tagId", tagId)
                .add("tagKey", tagKey)
                .add("tagValue", tagValue)
                .addValue(super.toString())
                .toString();
    }
}
