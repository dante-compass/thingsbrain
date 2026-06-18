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

package cn.herodotus.thingsbrain.persistence.commons.domain;

import cn.herodotus.thingsbrain.kernel.commons.definition.domain.SubscribeTopic;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Permission;
import cn.herodotus.thingsbrain.persistence.commons.enums.Qos;
import cn.herodotus.thingsbrain.persistence.commons.enums.Retain;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.data.commons.entity.AbstractSysEntity;

/**
 * <p>Description: 物联网 Mqtt 权限信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:52
 */
@Schema(name = "物联网 Mqtt 权限信息统一实体定义")
public class MqttAuthority extends AbstractSysEntity implements SubscribeTopic {

    @Schema(name = "Mqtt权限ID")
    private String id;

    @Schema(name = "Mqtt主题")
    private String topic;

    @Schema(name = "权限")
    private Permission permission = Permission.allow;

    @Schema(name = "操作")
    private Action action;

    @Schema(name = "QOS")
    private Qos qos;

    @Schema(name = "是否为保留数据")
    private Retain retain = Retain.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public int getQuality() {
        if (ObjectUtils.isNotEmpty(getQos())) {
            return getQos().ordinal();
        }
        return Qos.QOS_1.ordinal();
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("topic", topic)
                .add("permission", permission)
                .add("action", action)
                .add("qos", qos)
                .add("retain", retain)
                .addValue(super.toString())
                .toString();
    }
}
