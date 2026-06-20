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

package cn.herodotus.thingsbrain.platform.rest.dto;

import cn.herodotus.dante.core.domain.BaseDto;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

/**
 * <p>Description: 设置设备属性请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/26 14:02
 */
@Schema(name = "设置设备属性请求实体")
public class TslSetPropertyRequest implements BaseDto {

    @NotBlank(message = "ProductKey不能为空")
    @Schema(name = "物联网 ProductKey")
    private String productKey;

    @NotBlank(message = "DeviceName不能为空")
    @Schema(name = "物联网 DeviceName")
    private String deviceName;

    @NotEmpty(message = "必须要传递所需的参数")
    @Schema(name = "发送参数值", description = "从物模型中读取的 Properties 或 Service 出站参数。由前端拼凑属性和值")
    private Map<String, Object> params;

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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productKey", productKey)
                .add("deviceName", deviceName)
                .toString();
    }
}
