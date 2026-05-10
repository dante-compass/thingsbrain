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

package org.dromara.thingsbrain.platform.rest.controller.servlet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.dromara.dante.core.domain.Result;
import org.dromara.dante.data.commons.service.BasePageableService;
import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.dromara.dante.data.rest.servlet.AbstractEntityPageableController;
import org.dromara.dante.web.annotation.AccessLimited;
import org.dromara.thingsbrain.persistence.commons.domain.TslUnit;
import org.dromara.thingsbrain.persistence.commons.service.TslUnitService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: 物联网物模型条目单位管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 21:48
 */
@RestController
@RequestMapping("/iot/tsl/unit")
@Tags({
        @Tag(name = "物联网业务功能接口"),
        @Tag(name = "ThingsBrain物联网接口"),
        @Tag(name = "物联网物模型属性单位管理接口"),
})
public class TslUnitController extends AbstractEntityPageableController<TslUnit, String, BasePageableService<TslUnit, String>> {

    private final TslUnitService tslUnitService;

    public TslUnitController(TslUnitService tslUnitService) {
        this.tslUnitService = tslUnitService;
    }

    @Override
    public BaseWriteAndPageService<TslUnit, String> getService() {
        return tslUnitService;
    }

    @AccessLimited
    @Operation(summary = "获取全部物模型单位接口", description = "获取全部物模型单位接口",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class)))})
    @GetMapping("/list")
    public Result<List<TslUnit>> findAll() {
        List<TslUnit> units = tslUnitService.findAll();
        return result(units);
    }
}
