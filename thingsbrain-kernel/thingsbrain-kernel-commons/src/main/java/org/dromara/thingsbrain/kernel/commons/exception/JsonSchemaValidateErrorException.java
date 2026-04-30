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

package org.dromara.thingsbrain.kernel.commons.exception;

import org.dromara.dante.core.domain.Feedback;
import org.dromara.dante.core.exception.PlatformRuntimeException;
import org.dromara.thingsbrain.kernel.commons.constant.KernelErrorCodes;

/**
 * <p>Description: Json Schema 校验错误 Exception </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/5 16:09
 */
public class JsonSchemaValidateErrorException extends PlatformRuntimeException {

    public JsonSchemaValidateErrorException() {
        super();
    }

    public JsonSchemaValidateErrorException(String message) {
        super(message);
    }

    public JsonSchemaValidateErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSchemaValidateErrorException(Throwable cause) {
        super(cause);
    }

    @Override
    public Feedback getFeedback() {
        return KernelErrorCodes.JSON_SCHEMA_VALIDATE_ERROR_EXCEPTION;
    }
}
