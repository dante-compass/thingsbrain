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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.dante.core.domain.Result;
import org.dromara.dante.security.domain.UserPrincipal;
import org.dromara.dante.security.utils.ServletSecurityUtils;
import org.dromara.dante.web.annotation.Idempotent;
import cn.herodotus.thingsbrain.mqtt.outbound.service.DeviceConfigService;
import cn.herodotus.thingsbrain.platform.rest.dto.DeviceConfigLogPushRequest;
import cn.herodotus.thingsbrain.platform.rest.dto.DeviceConfigPushRequest;
import cn.herodotus.thingsbrain.platform.rest.dto.TslSetPropertyRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/19 21:02
 */
@RestController
@RequestMapping("/iot/device/config")
@Tags({
        @Tag(name = "ThingsBrain物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网设备配置管理接口"),
})
public class DeviceConfigController {

    private final DeviceConfigService deviceConfigService;

    public DeviceConfigController(DeviceConfigService deviceConfigService) {
        this.deviceConfigService = deviceConfigService;
    }

    @Idempotent
    @Operation(summary = "设备接收订阅云端推送日志配置", description = "设备接收订阅云端推送日志配置",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TslSetPropertyRequest.class))),
            responses = {@ApiResponse(description = "操作执行成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/log/push")
    public Result<String> logPush(@Validated @RequestBody DeviceConfigLogPushRequest domain) {

        deviceConfigService.logPush(domain.getProductKey(), domain.getDeviceName(), domain.getEnabled());
        return Result.success("发送设置设备属性请求成功");
    }

    @Idempotent
    @Operation(summary = "设备远程配置推送", description = "设备远程配置推送",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TslSetPropertyRequest.class))),
            responses = {@ApiResponse(description = "操作执行成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/push")
    public Result<String> logPush(@Validated @RequestBody DeviceConfigPushRequest domain, HttpServletRequest request) {

        UserPrincipal userPrincipal = ServletSecurityUtils.getUserPrincipal(request);

        deviceConfigService.push(domain.getProductKey(), domain.getDeviceName(), domain.getParams(), userPrincipal);
        return Result.success("发送设置设备属性请求成功");
    }
}
