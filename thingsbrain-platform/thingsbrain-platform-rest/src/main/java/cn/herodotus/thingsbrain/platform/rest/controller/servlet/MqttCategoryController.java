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

package cn.herodotus.thingsbrain.platform.rest.controller.servlet;

import cn.herodotus.dante.core.domain.Result;
import cn.herodotus.dante.data.commons.service.BaseWriteAndPageService;
import cn.herodotus.dante.data.rest.servlet.AbstractEntityWriteAndPageController;
import cn.herodotus.dante.web.annotation.AccessLimited;
import cn.herodotus.thingsbrain.persistence.commons.domain.MqttCategory;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Area;
import cn.herodotus.thingsbrain.persistence.commons.enums.Purpose;
import cn.herodotus.thingsbrain.persistence.commons.service.MqttCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物联网Mqtt主题类别接口 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/3 12:29
 */
@RestController
@RequestMapping("/iot/mqtt/category")
@Tags({
        @Tag(name = "ThingsBrain物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网Mqtt主题类别接口"),
})
public class MqttCategoryController extends AbstractEntityWriteAndPageController<MqttCategory, String, BaseWriteAndPageService<MqttCategory, String>> {

    private final MqttCategoryService mqttCategoryService;

    public MqttCategoryController(MqttCategoryService mqttCategoryService) {
        this.mqttCategoryService = mqttCategoryService;
    }

    @Override
    public BaseWriteAndPageService<MqttCategory, String> getService() {
        return mqttCategoryService;
    }

    @AccessLimited
    @Operation(summary = "模糊条件查询Mqtt主题类别", description = "根据动态输入的字段模糊查询主题类别信息",
            responses = {@ApiResponse(description = "主题类别分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "area", description = "Mqtt主题使用区域(索引数字值)"),
            @Parameter(name = "action", description = "Mqtt主题操作(索引数字值)"),
            @Parameter(name = "purpose", description = "Mqtt主题用途(索引数字值)"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "area", required = false) Integer area,
            @RequestParam(value = "action", required = false) Integer action,
            @RequestParam(value = "purpose", required = false) Integer purpose) {

        Page<MqttCategory> pages = mqttCategoryService.findByCondition(pageNumber, pageSize, Area.parse(area), Action.parse(action), Purpose.parse(purpose));
        return resultFromPage(pages);
    }

    @AccessLimited
    @Operation(summary = "获取全部Mqtt主题类别", description = "获取全部Mqtt主题类别数据列表",
            responses = {
                    @ApiResponse(description = "全部数据列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @GetMapping("/list")
    public Result<List<MqttCategory>> findAll() {
        List<MqttCategory> sysAuthorities = mqttCategoryService.findAll();
        return result(sysAuthorities);
    }
}
