/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsMesh.
 *
 * Herodotus ThingsMesh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsMesh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
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
        @Tag(name = "ThingsMesh物联网平台 REST 接口"),
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
}
