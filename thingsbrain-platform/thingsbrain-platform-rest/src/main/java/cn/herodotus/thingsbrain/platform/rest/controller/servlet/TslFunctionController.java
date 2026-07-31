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
import cn.herodotus.dante.security.domain.UserPrincipal;
import cn.herodotus.dante.security.utils.ServletSecurityUtils;
import cn.herodotus.dante.web.annotation.AccessLimited;
import cn.herodotus.dante.web.annotation.Idempotent;
import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.mqtt.outbound.service.TslServiceService;
import cn.herodotus.thingsbrain.persistence.commons.domain.TslFunction;
import cn.herodotus.thingsbrain.persistence.commons.service.TslFunctionService;
import cn.herodotus.thingsbrain.platform.rest.dto.TslInvokeServiceRequest;
import cn.herodotus.thingsbrain.platform.rest.dto.TslSetPropertyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Description: 物联网物模型功能管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/7 23:02
 */
@RestController
@RequestMapping("/iot/tsl/function")
@Tags({
        @Tag(name = "ThingsBrain物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网物模型功能管理接口"),
})
public class TslFunctionController extends AbstractEntityWriteAndPageController<TslFunction, String, BaseWriteAndPageService<TslFunction, String>> {

    private final TslFunctionService tslFunctionService;
    private final TslServiceService tslServiceService;

    public TslFunctionController(TslFunctionService tslFunctionService, TslServiceService tslServiceService) {
        this.tslFunctionService = tslFunctionService;
        this.tslServiceService = tslServiceService;
    }

    @Override
    public BaseWriteAndPageService<TslFunction, String> getService() {
        return tslFunctionService;
    }

    @AccessLimited
    @Operation(summary = "模糊条件查询物模型功能", description = "根据动态输入的字段模糊查询物模型功能信息",
            responses = {@ApiResponse(description = "模型功能列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "productId", description = "物联网 ProductId"),
            @Parameter(name = "productKey", description = "物联网 ProductKey"),
            @Parameter(name = "dimension", description = "物模型维度"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "productId", required = false) String productId,
            @RequestParam(value = "productKey", required = false) String productKey,
            @RequestParam(value = "dimension", required = false) String dimension) {
        Page<TslFunction> pages = tslFunctionService.findByCondition(pageNumber, pageSize, productId, productKey, Dimension.parse(dimension));
        return resultFromPage(pages);
    }

//    @Operation(summary = "查询物模型中可以设置的属性", description = "查询物模型中可以设置的属性",
//            responses = {@ApiResponse(description = "可以设置属性列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))})
//    @Parameters({
//            @Parameter(name = "productKey", required = true, description = "产品KEY"),
//    })
//    @GetMapping("/settable")
//    public Result<List<TslFunction>> settable(@NotNull @RequestParam("productKey") String productKey) {
//        List<TslFunction> functions = iotTslFunctionService.findAllSettableProperties(productKey);
//        return result(functions);
//    }
//
//    @Operation(summary = "查询物模型中可以调用的服务", description = "查询物模型中可以调用的服务",
//            responses = {@ApiResponse(description = "可以设置属性列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))})
//    @Parameters({
//            @Parameter(name = "productKey", required = true, description = "产品KEY"),
//    })
//    @GetMapping("/callable")
//    public Result<List<TslFunction>> callable(@NotNull @RequestParam("productKey") String productKey) {
//        List<TslFunction> functions = iotTslFunctionService.findAllCallableServices(productKey);
//        return result(functions);
//    }

    @Idempotent
    @Operation(summary = "设置设备属性", description = "设置设备属性",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TslSetPropertyRequest.class))),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/property")
    public Result<String> setProperty(@Validated @RequestBody TslSetPropertyRequest domain, HttpServletRequest request) {

        UserPrincipal userPrincipal = ServletSecurityUtils.getUserPrincipal(request);

        tslServiceService.set(domain.getProductKey(), domain.getDeviceName(), domain.getParams(), userPrincipal);
        return Result.success("发送设置设备属性请求成功");
    }


    @Idempotent
    @Operation(summary = "设置设备属性", description = "设置设备属性",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TslInvokeServiceRequest.class))),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/invoke")
    public Result<String> invokeService(@Validated @RequestBody TslInvokeServiceRequest domain, HttpServletRequest request) {
        UserPrincipal userPrincipal = ServletSecurityUtils.getUserPrincipal(request);
        tslServiceService.invoke(domain.getProductKey(), domain.getDeviceName(), domain.getIdentifier(), domain.getParams(), userPrincipal);
        return Result.success("设置设备属性操作成功");
    }
}
