/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
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
