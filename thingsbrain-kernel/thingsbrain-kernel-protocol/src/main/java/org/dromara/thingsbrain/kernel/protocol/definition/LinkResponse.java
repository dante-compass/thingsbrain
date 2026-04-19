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

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 物联网通用响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/3 18:17
 */
public class LinkResponse<T> extends AbstractResponse<T> {

    private static <T> LinkResponse<T> response(String id, String method, Integer code, String message, T data) {
        LinkResponse<T> response = new LinkResponse<>();
        response.setId(id);
        response.setMethod(method);
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> LinkResponse<?> success(String id, String method, T data) {
        if (ObjectUtils.isNotEmpty(data)) {
            return response(id, method, 200, "success", data);
        } else {
            Map<String, Object> empty = new HashMap<>();
            return response(id, method, 200, "success", empty);
        }
    }

    public static <T> LinkResponse<?> failure(String id, String method, Integer code, String message, T data) {
        if (ObjectUtils.isNotEmpty(data)) {
            return response(id, method, code, message, data);
        } else {
            Map<String, Object> empty = new HashMap<>();
            return response(id, method, code, message, empty);
        }
    }

    public static <T> LinkResponse<?> requestError(String id, String method, T data) {
        return failure(id, method, 400, "request error", data);
    }

    public static LinkResponse<?> requestError(String id, String method) {
        return requestError(id, method, null);
    }

    public static <T> LinkResponse<?> requestParameterError(String id, String method, T data) {
        return failure(id, method, 406, "request parameter error", data);
    }

    public static LinkResponse<?> requestParameterError(String id, String method) {
        return requestParameterError(id, method, null);
    }

    public static <T> LinkResponse<?> tooManyRequests(String id, String method, T data) {
        return failure(id, method, 429, "too many requests", data);
    }

    public static LinkResponse<?> tooManyRequests(String id, String method) {
        return tooManyRequests(id, method, null);
    }

    public static <T> LinkResponse<?> internalServerError(String id, String method, T data) {
        return failure(id, method, 500, "internal server error", data);
    }

    public static LinkResponse<?> internalServerError(String id, String method) {
        return internalServerError(id, method, null);
    }

    public static LinkResponse<?> failure(String id, String method, Integer code, String message) {
        return failure(id, method, code, message, null);
    }
}
