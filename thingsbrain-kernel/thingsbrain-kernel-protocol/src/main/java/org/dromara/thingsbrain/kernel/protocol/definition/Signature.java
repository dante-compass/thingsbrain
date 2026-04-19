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

package org.dromara.thingsbrain.kernel.protocol.definition;

import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 物联网认证相关信息通用实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/18 23:54
 */
public class Signature extends Identifier {

    /**
     * 签名。
     * <p>
     * 加签算法：
     * 将所有提交给服务器的参数（sign，signMethod除外）按照字母顺序排序，然后将参数和值依次拼接（无拼接符号）。
     * 对加签内容，需通过signMethod指定的加签算法，使用设备的DeviceSecret值，进行签名计算。
     * <p>
     * 签名计算示例：
     * <pre>
     * sign= hmac_md5(deviceSecret, clientId123deviceNametestproductKey123timestamp1524448722000)
     * </pre>
     */
    private String sign;
    /**
     * 签名方法，支持hmacSha1、hmacSha256、hmacMd5、Sha256。
     */
    private String signMethod;
    /**
     * 时间戳（单位：毫秒）
     */
    private String timestamp;
    /**
     * 设备本地标记，非必填。可以设置为具体的productKey&deviceName
     */
    private String clientId;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sign", sign)
                .add("signMethod", signMethod)
                .add("timestamp", timestamp)
                .add("clientId", clientId)
                .addValue(super.toString())
                .toString();
    }
}
