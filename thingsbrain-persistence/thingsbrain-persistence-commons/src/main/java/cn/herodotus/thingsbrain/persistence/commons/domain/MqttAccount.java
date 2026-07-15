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
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 物联网 Mqtt 连接账号信息统一实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/2 21:43
 */
@Schema(name = "物联网 Mqtt 连接账号信息统一实体定义")
public class MqttAccount extends AbstractSysEntity {

    @Schema(name = "Mqtt账号ID")
    private String id;

    @Schema(name = "Mqtt客户端ID")
    private String clientId;

    @Schema(name = "Mqtt用户名")
    private String username;

    @Schema(name = "Mqtt密码")
    private String password;

    @Schema(name = "是否为超级用户", title = "目前主要为 Emqx 使用该字段")
    private Boolean superUser = Boolean.FALSE;

    @Schema(name = "主题类别")
    private Set<MqttCategory> categories = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSuperUser() {
        return superUser;
    }

    public void setSuperUser(Boolean superUser) {
        this.superUser = superUser;
    }

    public Set<MqttCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<MqttCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("clientId", clientId)
                .add("username", username)
                .add("password", password)
                .add("superUser", superUser)
                .addValue(super.toString())
                .toString();
    }
}
