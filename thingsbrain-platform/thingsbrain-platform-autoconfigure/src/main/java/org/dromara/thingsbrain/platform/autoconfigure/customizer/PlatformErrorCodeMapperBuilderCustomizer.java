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

import org.dromara.dante.core.builder.ErrorCodeMapperBuilder;
import org.dromara.dante.core.constant.BuilderCustomizerOrdered;
import org.dromara.dante.core.function.ErrorCodeMapperBuilderCustomizer;
import org.dromara.thingsbrain.kernel.commons.constant.KernelErrorCodes;
import org.springframework.core.Ordered;

/**
 * <p>Description: 物联网 Facility 错误代码映射定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 16:09
 */
public class PlatformErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {


    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder
                .preconditionFailed(KernelErrorCodes.JSON_SCHEMA_VALIDATE_ERROR_EXCEPTION)
                .internalServerError(KernelErrorCodes.INBOUND_MESSAGE_PROCESSING_EXCEPTION);
    }

    @Override
    public int getOrder() {
        return BuilderCustomizerOrdered.ERROR_CODE__IOT_FACILITY;
    }
}
