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
import cn.herodotus.thingsbrain.kernel.commons.enums.Qos;
import cn.herodotus.thingsbrain.persistence.commons.domain.MqttAuthority;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Permission;
import cn.herodotus.thingsbrain.persistence.commons.enums.Retain;
import cn.herodotus.thingsbrain.persistence.commons.service.MqttAuthorityService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Description: 物联网Mqtt主题权限接口 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/7/3 12:29
 */
@RestController
@RequestMapping("/iot/mqtt/authority")
@Tags({
        @Tag(name = "ThingsMesh物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网Mqtt主题权限接口"),
})
public class MqttAuthorityController extends AbstractEntityWriteAndPageController<MqttAuthority, String, BaseWriteAndPageService<MqttAuthority, String>> {

    private final MqttAuthorityService mqttAuthorityService;

    public MqttAuthorityController(MqttAuthorityService mqttAuthorityService) {
        this.mqttAuthorityService = mqttAuthorityService;
    }

    @Override
    public BaseWriteAndPageService<MqttAuthority, String> getService() {
        return mqttAuthorityService;
    }

    @AccessLimited
    @Operation(summary = "模糊条件查询Mqtt主题权限", description = "根据动态输入的字段模糊查询主题权限信息",
            responses = {@ApiResponse(description = "主题权限分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "topic", description = "Mqtt主题"),
            @Parameter(name = "action", description = "Mqtt主题操作(索引数字值)"),
            @Parameter(name = "permission", description = "Mqtt主题权限(索引数字值)"),
            @Parameter(name = "qos", description = "Mqtt 主题 Qos(索引数字值)"),
            @Parameter(name = "retain", description = "是否为保留主题"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "topic", required = false) String topic,
            @RequestParam(value = "action", required = false) Integer action,
            @RequestParam(value = "permission", required = false) Integer permission,
            @RequestParam(value = "qos", required = false) Integer qos,
            @RequestParam(value = "retain", required = false) Integer retain) {

        Page<MqttAuthority> pages = mqttAuthorityService.findByCondition(pageNumber, pageSize, topic, Action.parse(action), Permission.parse(permission), Qos.parse(qos), Retain.parse(retain));
        return resultFromPage(pages);
    }

    @Operation(summary = "给Mqtt主题权限分配类别", description = "给Mqtt主题权限分配类别",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "已分配权限的Mqtt主题权限数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "id", required = true, description = "Mqtt主题权限ID"),
            @Parameter(name = "categories[]", required = true, description = "Mqtt主题类别组成的数组")
    })
    @PutMapping
    public Result<MqttAuthority> assign(@RequestParam(name = "id") String id, @RequestParam(name = "categories[]") String[] categories) {
        MqttAuthority mqttAuthority = mqttAuthorityService.assign(id, categories);
        return result(mqttAuthority);
    }
}
