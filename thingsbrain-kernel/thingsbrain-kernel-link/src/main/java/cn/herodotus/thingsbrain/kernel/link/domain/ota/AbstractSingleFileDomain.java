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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 单个文件的OTA升级包信息通用属性抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 11:00
 */
public abstract class AbstractSingleFileDomain extends AbstractDataDomain {

    /**
     * 升级包文件大小，单位：字节。
     * OTA升级包中仅有一个升级包文件时，包含该参数。
     */
    private Long size;
    /**
     * OTA升级包文件安全升级后的签名。仅当OTA升级包开启安全升级功能，才有此参数。开启OTA升级包安全升级功能的方法
     */
    @JsonProperty(value = "digestsign")
    private String digestSign;

    /**
     * 当签名方法为MD5时，除了会给sign赋值外还会给md5赋值。
     * OTA升级包中仅有一个升级包文件时，包含该参数。
     */
    private String md5;
    /**
     * OTA升级包文件的签名。
     * OTA升级包中仅有一个升级包文件时，包含该参数。
     */
    private String sign;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDigestSign() {
        return digestSign;
    }

    public void setDigestSign(String digestSign) {
        this.digestSign = digestSign;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("digestSign", digestSign)
                .add("md5", md5)
                .add("sign", sign)
                .addValue(super.toString())
                .toString();
    }
}
