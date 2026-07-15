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
import cn.herodotus.dante.web.annotation.Idempotent;
import cn.herodotus.thingsbrain.link.commons.definition.SpecificationManager;
import cn.herodotus.thingsbrain.persistence.commons.domain.Product;
import cn.herodotus.thingsbrain.persistence.commons.enums.NodeType;
import cn.herodotus.thingsbrain.persistence.commons.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 物联网产品详情接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/16 16:19
 */
@RestController
@RequestMapping("/iot/product")
@Tags({
        @Tag(name = "ThingsBrain物联网平台 REST 接口"),
        @Tag(name = "物联网平台业务功能接口"),
        @Tag(name = "物联网产品管理接口"),
})
public class ProductController extends AbstractEntityWriteAndPageController<Product, String, BaseWriteAndPageService<Product, String>> {

    private final ProductService productService;
    private final SpecificationManager specificationManager;

    public ProductController(SpecificationManager specificationManager) {
        this.productService = specificationManager.getProductService();
        this.specificationManager = specificationManager;
    }

    @Override
    public BaseWriteAndPageService<Product, String> getService() {
        return productService;
    }

    @AccessLimited
    @Operation(summary = "模糊条件查询产品", description = "根据动态输入的字段模糊查询产品信息",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "productKey", description = "物联网 ProductKey"),
            @Parameter(name = "productName", description = "产品名称"),
            @Parameter(name = "nodeType", description = "节点类型"),
            @Parameter(name = "release", description = "是否已发布"),
            @Parameter(name = "categoryName", description = "产品分类名称"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "productKey", required = false) String productKey,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "nodeType", required = false) Integer nodeType,
            @RequestParam(value = "release", required = false) Boolean release,
            @RequestParam(value = "categoryName", required = false) String categoryName) {
        Page<Product> pages = productService.findByCondition(pageNumber, pageSize, productKey, productName, NodeType.parse(nodeType), release, categoryName);
        return resultFromPage(pages);
    }

    @AccessLimited
    @Operation(summary = "根据ProductKey模糊查询产品", description = "根据ProductKey模糊查询产品",
            responses = {
                    @ApiResponse(description = "产品列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")

            })
    @Parameters({
            @Parameter(name = "productKey", required = true, description = "物联网 ProductKey"),
    })
    @GetMapping("/list")
    public Result<List<Product>> findAllByProductKey(@RequestParam(value = "productKey") String productKey) {
        List<Product> products = productService.findAllByProductKey(productKey);
        return result(products);
    }

    @AccessLimited
    @Operation(summary = "根据ProductKey查询物联网产品", description = "通过ProductKey查询物联网产品",
            responses = {
                    @ApiResponse(description = "查询到的物联网产品", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "productKey", required = true, in = ParameterIn.PATH, description = "ProductKey"),
    })
    @GetMapping("/validation/{productKey}")
    public Result<Product> validate(@PathVariable("productKey") String productKey) {
        Optional<Product> optional = productService.findByProductKey(productKey);
        return optional.map(this::result).orElse(Result.empty());
    }

    @Idempotent
    @Operation(summary = "开启或关闭产品动态注册", description = "开启或关闭产品动态注册",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/toggle")
    public Result<Product> toggle(@RequestBody Product domain) {
        Product product = productService.switchAuthentication(domain);
        return result(product);
    }

    @Idempotent
    @Operation(summary = "发布产品", description = "发布产品将其 Release 状态修改为 True",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PutMapping("/release")
    public Result<Boolean> release(@RequestBody Product domain) {
        boolean result = specificationManager.release(domain.getProductKey());
        return result(result);
    }
}
