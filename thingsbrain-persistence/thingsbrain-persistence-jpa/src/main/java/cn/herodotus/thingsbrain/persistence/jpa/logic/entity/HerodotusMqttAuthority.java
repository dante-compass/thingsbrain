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
import cn.herodotus.thingsbrain.kernel.commons.enums.Qos;
import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Permission;
import cn.herodotus.thingsbrain.persistence.commons.enums.Retain;
import cn.herodotus.thingsbrain.persistence.jpa.logic.generator.HerodotusMqttAuthorityUuidGenerator;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: Mqtt 主题权限 </p>
 * <p>
 * 类似于 Emqx ACL
 *
 * @author : gengwei.zheng
 * @date : 2025/5/8 14:35
 */
@Entity
@Table(name = "iot_mqtt_authority", indexes = {
        @Index(name = "iot_mqtt_authority_id_idx", columnList = "authority_id"),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_MQTT_AUTHORITY)
public class HerodotusMqttAuthority extends AbstractSysEntity {

    @Id
    @HerodotusMqttAuthorityUuidGenerator
    @Column(name = "authority_id", length = 64)
    private String authorityId;

    @Column(name = "topic", length = 128)
    private String topic;

    @Column(name = "permission", length = 50)
    @Enumerated(EnumType.STRING)
    private Permission permission = Permission.allow;

    @Column(name = "action", length = 50)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(name = "qos")
    @Enumerated(EnumType.ORDINAL)
    private Qos qos;

    @Column(name = "retain")
    @Enumerated(EnumType.ORDINAL)
    private Retain retain = Retain.TRUE;

    /**
     * 用户 - 角色关系定义:
     * (1) 加上fetch=FetchType.LAZY  或 @Fetch(FetchMode.SELECT), 输出结果与上面相同，说明默认设置是fetch=FetchType.LAZY 和 @Fetch(FetchMode.SELECT) 下面四种配置等效，都是N+1条sql的懒加载
     * (2) 加上fetch=FetchType.Eager 和 @Fetch(FetchMode.SELECT), 同样是N+1条sql，不过和上面情况不同的是，N条sql会在criteria.list()时执行
     * (3) 加上@Fetch(FetchMode.JOIN), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效
     * 从输出可看出，在执行criteria.list()时通过一条sql 获取了所有的City和Hotel。
     * 使用@Fetch(FetchMode.JOIN)需要注意的是：它在Join查询时是Full Join, 所以会有重复City出现
     * (4) 加上@Fetch(FetchMode.SUBSELECT), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效 从输出可看出，在执行criteria.list()时通过两条sql分别获取City和Hotel
     * <p>
     *
     * @see <a href=https://www.jianshu.com/p/23bd82a7b96e>参考文档</a>
     */
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_MQTT_CATEGORY)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "iot_mqtt_authority_category",
            joinColumns = {@JoinColumn(name = "authority_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"authority_id", "category_id"})},
            indexes = {@Index(name = "iot_mqtt_authority_category_aid_idx", columnList = "authority_id"), @Index(name = "iot_mqtt_authority_category_cid_idx", columnList = "category_id")})
    private Set<HerodotusMqttCategory> categories = new HashSet<>();

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Qos getQos() {
        return qos;
    }

    public void setQos(Qos qos) {
        this.qos = qos;
    }

    public Retain getRetain() {
        return retain;
    }

    public void setRetain(Retain retain) {
        this.retain = retain;
    }

    public Set<HerodotusMqttCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<HerodotusMqttCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authorityId", authorityId)
                .add("topic", topic)
                .add("permission", permission)
                .add("action", action)
                .add("qos", qos)
                .add("retain", retain)
                .addValue(super.toString())
                .toString();
    }
}
