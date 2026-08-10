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

import cn.herodotus.dante.data.commons.entity.AbstractAuditEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 物联网设备影子统一实体定义 </p>
 * <p>
 * 设备影子为 JSON 数据，针对每一个设备是唯一。
 * 之所以将设备影子单独定义，而不是将其合并至 {@link Device} 主要考虑到：
 * · {@link Device} 更偏重于"概念"设备，肯能存在一个 {@link Device} 对应实际多个设备
 * · {@link DeviceShadow} 对应"物理"设备，即一个 {@link DeviceShadow} 对应一个实际设备
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:29
 */
@Schema(name = "物联网设备影子统一实体定义")
public class DeviceShadow extends AbstractAuditEntity {

    @Schema(name = "设备影子ID")
    private String id;

    @Schema(name = "产品KEY")
    private String productKey;

    @Schema(name = "设备名称")
    private String deviceName;

    @Schema(name = "设备影子版本", title = "便于对版本进行比较")
    private Integer version;

    @Schema(name = "设备影子 JSON 内容")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                .add("id", id)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .add("version", version)
                .add("content", content)
                .addValue(super.toString())
                .toString();
    }
}
