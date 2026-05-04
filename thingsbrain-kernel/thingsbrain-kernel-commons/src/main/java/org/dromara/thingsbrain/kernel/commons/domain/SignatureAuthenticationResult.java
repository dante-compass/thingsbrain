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

package org.dromara.thingsbrain.kernel.commons.domain;

import org.dromara.dante.core.domain.BaseModel;

/**
 * <p>Description: Emqx 认证 Webhook 结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/26 22:04
 */
public class SignatureAuthenticationResult implements BaseModel {

    public enum Status {
        allow, deny, ignore;
    }

    /**
     * 认证结果
     */
    private final Status status;

    /**
     * 错误信息
     */
    private final String message;

    private SignatureAuthenticationResult(Status status) {
        this(status, null);
    }

    private SignatureAuthenticationResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SignatureAuthenticationResult allow() {
        return new SignatureAuthenticationResult(Status.allow);
    }

    public static SignatureAuthenticationResult deny(String message) {
        return new SignatureAuthenticationResult(Status.deny, message);
    }

    public static SignatureAuthenticationResult ignore(String message) {
        return new SignatureAuthenticationResult(Status.ignore, message);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
