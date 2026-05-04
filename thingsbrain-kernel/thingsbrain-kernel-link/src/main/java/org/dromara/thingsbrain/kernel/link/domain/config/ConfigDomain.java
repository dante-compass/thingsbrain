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

package org.dromara.thingsbrain.kernel.link.domain.config;

import com.google.common.base.MoreObjects;
import org.dromara.thingsbrain.kernel.link.definition.config.AbstractConfig;
import org.dromara.thingsbrain.kernel.link.enums.ConfigGetType;

/**
 * <p>Description: 配置信息响应内容实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 12:30
 */
public class ConfigDomain extends AbstractConfig {

    /**
     * 配置的ID
     */
    private String configId;
    /**
     * 配置大小，按字节计算。
     */
    private Long configSize;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名方法，仅支持sha256
     */
    private String signMethod = "Sha256";
    /**
     * 存储配置文件的对象存储（OSS）地址。
     */
    private String url;

    public ConfigDomain() {
        this.setGetType(ConfigGetType.file.name());
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Long getConfigSize() {
        return configSize;
    }

    public void setConfigSize(Long configSize) {
        this.configSize = configSize;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("configId", configId)
                .add("configSize", configSize)
                .add("sign", sign)
                .add("signMethod", signMethod)
                .add("url", url)
                .addValue(super.toString())
                .toString();
    }
}
