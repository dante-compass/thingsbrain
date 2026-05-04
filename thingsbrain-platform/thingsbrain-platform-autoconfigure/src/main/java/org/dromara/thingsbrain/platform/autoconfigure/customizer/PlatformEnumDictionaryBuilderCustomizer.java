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

package org.dromara.thingsbrain.platform.autoconfigure.customizer;

import org.dromara.dante.core.builder.EnumDictionaryBuilder;
import org.dromara.dante.core.function.EnumDictionaryBuilderCustomizer;
import org.dromara.thingsbrain.kernel.tsl.enums.*;
import org.dromara.thingsbrain.persistence.commons.enums.*;

/**
 * <p>Description: Things Brain Platform 相关模块枚举数据字典定义器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/23 16:00
 */
public class PlatformEnumDictionaryBuilderCustomizer implements EnumDictionaryBuilderCustomizer {

    @Override
    public void customize(EnumDictionaryBuilder builder) {
        builder.append(AccessMode.getDictionaries());
        builder.append(ArgumentType.getDictionaries());
        builder.append(CallType.getDictionaries());
        builder.append(EventType.getDictionaries());
        builder.append(Dimension.getDictionaries());
        builder.append(Action.getDictionaries());
        builder.append(NetworkingMethod.getDictionaries());
        builder.append(NodeType.getDictionaries());
        builder.append(Permission.getDictionaries());
        builder.append(GatewayProtocol.getDictionaries());
        builder.append(AuthenticationMode.getDictionaries());
        builder.append(Area.getDictionaries());
    }
}
