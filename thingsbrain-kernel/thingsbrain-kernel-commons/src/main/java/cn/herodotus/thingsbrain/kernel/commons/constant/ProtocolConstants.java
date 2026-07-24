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

package cn.herodotus.thingsbrain.kernel.commons.constant;

import cn.herodotus.dante.core.constant.BaseConstants;
import cn.herodotus.dante.core.constant.SymbolConstants;

/**
 * <p>Description: Herodotus Link 通用基础常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/29 23:05
 */
public interface ProtocolConstants extends BaseConstants {

    String TOPIC_WILDCARDS_SINGLE = "/+";

    String PREFIX__EVENT = "thing.event";
    String PREFIX__SERVICE = "thing.service";

    String VARIABLE__IDENTIFIER = "identifier";
    String VARIABLE__PROPERTY = "property";
    String VARIABLE__EVENT_IDENTIFIER = "tsl.event.identifier";
    String VARIABLE__SERVICE_IDENTIFIER = "tsl.service.identifier";

    String ACTION__POST = "post";
    String ACTION__SET = "set";
    String ACTION__GET = "get";
    String ACTION__REPLY = "_reply";

    String DESCRIPTION__PROPERTY_POST = "属性上报";
    String DESCRIPTION__PROPERTY_SET = "属性设置";
    String DESCRIPTION__PROPERTY_GET = "属性获取";

    String PARAMETER__VALUE = "value";

    String PARAMETER__REPORTED = "reported";
    String PARAMETER__DESIRED = "desired";

    /**
     * 格式：${productKey}
     */
    String FORMAT_PLACEHOLDER__PRODUCT_KEY = PLACEHOLDER_PREFIX + KernelConstants.KEY__PRODUCT_KEY + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${deviceName}
     */
    String FORMAT_PLACEHOLDER__DEVICE_NAME = PLACEHOLDER_PREFIX + KernelConstants.KEY__DEVICE_NAME + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${tsl.event.identifier}
     */
    String FORMAT_PLACEHOLDER__IDENTIFIER_EVENT = PLACEHOLDER_PREFIX + VARIABLE__EVENT_IDENTIFIER + PLACEHOLDER_SUFFIX;
    /**
     * 格式：${tsl.service.identifier}
     */
    String FORMAT_PLACEHOLDER__IDENTIFIER_SERVICE = PLACEHOLDER_PREFIX + VARIABLE__SERVICE_IDENTIFIER + PLACEHOLDER_SUFFIX;
    /**
     * 格式：/${productKey}/${deviceName}
     */
    String FORMAT_LEVEL__COMMON = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__PRODUCT_KEY + SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__DEVICE_NAME;
    /**
     * 格式：/${tsl.event.identifier}
     */
    String FORMAT_LEVEL__EVENT = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__IDENTIFIER_EVENT;
    /**
     * 格式：/${tsl.service.identifier}
     */
    String FORMAT_LEVEL__SERVICE = SymbolConstants.FORWARD_SLASH + FORMAT_PLACEHOLDER__IDENTIFIER_SERVICE;
    /**
     * 格式：/post
     */
    String FORMAT_LEVEL__POST = SymbolConstants.SLASH + ProtocolConstants.ACTION__POST;
}
