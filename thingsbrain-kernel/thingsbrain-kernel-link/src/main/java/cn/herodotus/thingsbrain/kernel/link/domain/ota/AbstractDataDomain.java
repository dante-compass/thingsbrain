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

package cn.herodotus.thingsbrain.kernel.link.domain.ota;

import java.util.Map;

/**
 * <p>Description: 升级包数据通用属性抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 11:00
 */
public abstract class AbstractDataDomain extends DeviceInformParam {

    /**
     * 仅当升级包类型为差分时，消息包含此参数。
     * 取值为1，表示仅包含新版本升级包与之前版本的差异部分，需要设备进行差分还原。
     */
    private Long isDiff;
    /**
     * 签名方法。取值：SHA256,MD5
     * 对于Android差分升级包类型，仅支持MD5签名方法。
     */
    private String signMethod;

    private Map<String, String> extData;

    public Long getIsDiff() {
        return isDiff;
    }

    public void setIsDiff(Long isDiff) {
        this.isDiff = isDiff;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public Map<String, String> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, String> extData) {
        this.extData = extData;
    }
}
