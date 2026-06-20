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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import cn.herodotus.dante.core.domain.Result;
import cn.herodotus.dante.data.commons.service.BaseWriteAndPageService;
import cn.herodotus.dante.data.rest.servlet.AbstractEntityWriteAndPageController;
import cn.herodotus.dante.web.annotation.AccessLimited;
import cn.herodotus.thingsbrain.persistence.commons.domain.Device;
import cn.herodotus.thingsbrain.persistence.commons.service.DeviceService;
import cn.herodotus.thingsbrain.platform.commons.definition.MqttSignatureGenerator;
import cn.herodotus.thingsbrain.platform.commons.domain.SignatureGenerationResult;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 物联网设备接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/16 16:19
 */
@RestController
@RequestMapping("/iot/device")
@Tags({
        @Tag(name = "ThingsBrain物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网产品管理接口"),
})
public class DeviceController extends AbstractEntityWriteAndPageController<Device, String, BaseWriteAndPageService<Device, String>> {

    private final DeviceService deviceService;
    private final MqttSignatureGenerator mqttSignatureGenerator;

    public DeviceController(DeviceService deviceService, MqttSignatureGenerator mqttSignatureGenerator) {
        this.deviceService = deviceService;
        this.mqttSignatureGenerator = mqttSignatureGenerator;
    }

    @Override
    public BaseWriteAndPageService<Device, String> getService() {
        return deviceService;
    }

    @Operation(summary = "模糊条件查询设备", description = "根据动态输入的字段模糊查询设备信息",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "productKey", description = "物联网 ProductKey"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "productKey", required = false) String productKey) {
        Page<Device> pages = deviceService.findByCondition(pageNumber, pageSize, productKey);
        return resultFromPage(pages);
    }

    @AccessLimited
    @Operation(summary = "获取设备签名信息", description = "获取MQTT连接签名参数值",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "clientId", required = true, description = "设备 ClientID"),
    })
    @GetMapping("/signature")
    public Result<SignatureGenerationResult> signature(@NotBlank @RequestParam("clientId") String clientId) {
        Optional<Device> optional = deviceService.findByClientId(clientId);
        SignatureGenerationResult result = optional.map(device -> mqttSignatureGenerator.process(device.getProduct().getProductKey(), device.getDeviceName(), device.getDeviceSecret())).orElse(new SignatureGenerationResult());
        return result(result);
    }
}
