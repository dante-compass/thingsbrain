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

package org.dromara.thingsbrain.kernel.commons.domain;

import org.dromara.dante.core.constant.SymbolConstants;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: Schema 校验结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/16 17:28
 */
public class ValidationResult implements Serializable {

    private Boolean valid = Boolean.TRUE;

    private List<JsonSchemaError> errors;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<JsonSchemaError> getErrors() {
        return errors;
    }

    public void setErrors(List<JsonSchemaError> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        if (CollectionUtils.isNotEmpty(errors)) {
            return errors.getFirst().getMessage();
        } else {
            return SymbolConstants.BLANK;
        }
    }
}
