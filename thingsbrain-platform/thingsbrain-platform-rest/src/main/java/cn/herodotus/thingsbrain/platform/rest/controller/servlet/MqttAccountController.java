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
import cn.herodotus.thingsbrain.persistence.commons.domain.MqttAccount;
import cn.herodotus.thingsbrain.persistence.commons.service.MqttAccountService;
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
 * <p>Description: 物联网Mqtt账号接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/16 14:54
 */
@RestController
@RequestMapping("/iot/mqtt/account")
@Tags({
        @Tag(name = "ThingsMesh物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网Mqtt账户接口"),
})
public class MqttAccountController extends AbstractEntityWriteAndPageController<MqttAccount, String, BaseWriteAndPageService<MqttAccount, String>> {

    private final MqttAccountService mqttAccountService;

    public MqttAccountController(MqttAccountService mqttAccountService) {
        this.mqttAccountService = mqttAccountService;
    }

    @Override
    public BaseWriteAndPageService<MqttAccount, String> getService() {
        return mqttAccountService;
    }

    @AccessLimited
    @Operation(summary = "模糊条件查询Mqtt账号", description = "根据动态输入的字段模糊查询Mqtt账号信息",
            responses = {@ApiResponse(description = "Mqtt账号分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "clientId", description = "Mqtt 客户端ID"),
            @Parameter(name = "username", description = "Mqtt 用户名"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "clientId", required = false) String clientId,
            @RequestParam(value = "username", required = false) String username) {
        Page<MqttAccount> pages = mqttAccountService.findByCondition(pageNumber, pageSize, clientId, username);
        return resultFromPage(pages);
    }

    @Operation(summary = "给Mqtt账户分配类别", description = "给Mqtt账户分配类别",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)),
            responses = {@ApiResponse(description = "已分配类别的Mqtt账户数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "id", required = true, description = "Mqtt账户ID"),
            @Parameter(name = "categories[]", required = true, description = "Mqtt主题类别对象组成的数组")
    })
    @PutMapping
    public Result<MqttAccount> assign(@RequestParam(name = "id") String id, @RequestParam(name = "categories[]") String[] categories) {
        MqttAccount mqttAccount = mqttAccountService.assign(id, categories);
        return result(mqttAccount);
    }
}
