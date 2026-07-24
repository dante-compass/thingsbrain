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
