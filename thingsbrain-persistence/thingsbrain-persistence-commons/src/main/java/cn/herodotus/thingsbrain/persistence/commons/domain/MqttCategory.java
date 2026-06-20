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

import cn.herodotus.dante.data.commons.entity.AbstractSysEntity;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Area;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 物联网 Mqtt 主题分类信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:52
 */
@Schema(name = "物联网 Mqtt 主题分类信息统一实体定义")
public class MqttCategory extends AbstractSysEntity {

    @Schema(name = "Mqtt主题分类ID")
    private String id;

    @Schema(name = "Mqtt主题分类名称")
    private String name;

    @Schema(name = "是否为默认分类")
    private Boolean standard = Boolean.FALSE;

    @Schema(name = "分类用途")
    private Area area = Area.DEVICE;

    /**
     * 该 Action 与 MqttAuthority 中的 Action 用途不同。
     * 1. 该 Action 用于区分 MqttCategory 中的类型。可以通过该 Action 筛选不同的 MqttAction,例如：可以通过该字段查询订阅主题，让平台动态订阅。
     * 2. MqttAuthority 中的 Action 用于定义主题的 ACL 权限。目前主要用于 Emqx 鉴权。
     */
    @Schema(name = "主题操作类型")
    private Action action;

    @Schema(name = "分类权限")
    private Set<MqttAuthority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStandard() {
        return standard;
    }

    public void setStandard(Boolean standard) {
        this.standard = standard;
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

    public Set<MqttAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<MqttAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("standard", standard)
                .add("area", area)
                .add("action", action)
                .addValue(super.toString())
                .toString();
    }
}
