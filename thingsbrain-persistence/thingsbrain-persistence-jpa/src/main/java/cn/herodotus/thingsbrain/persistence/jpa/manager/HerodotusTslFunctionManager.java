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

package cn.herodotus.thingsbrain.persistence.jpa.manager;

import cn.herodotus.thingsbrain.kernel.tsl.enums.Dimension;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslArgument;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusTslFunction;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslArgumentService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusTslFunctionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * <p>Description: 物联网物模型功能 Manage </p>
 * <p>
 * 提取出 Manage 层，方便数据层面业务逻辑的视线，同时保持 Service 层代码的清爽
 *
 * @author : gengwei.zheng
 * @date : 2025/4/28 15:42
 */
public class HerodotusTslFunctionManager {

    private final HerodotusTslFunctionService herodotusTslFunctionService;
    private final HerodotusTslArgumentService herodotusTslArgumentService;

    public HerodotusTslFunctionManager(HerodotusTslFunctionService herodotusTslFunctionService, HerodotusTslArgumentService herodotusTslArgumentService) {
        this.herodotusTslFunctionService = herodotusTslFunctionService;
        this.herodotusTslArgumentService = herodotusTslArgumentService;
    }

    public HerodotusTslFunctionService getHerodotusTslFunctionService() {
        return herodotusTslFunctionService;
    }

    @Transactional(rollbackFor = Exception.class)
    public HerodotusTslFunction save(HerodotusTslFunction domain) {
        if (domain.getDimension() != Dimension.PROPERTY) {
            if (CollectionUtils.isNotEmpty(domain.getArguments())) {
                List<HerodotusTslArgument> attributes = herodotusTslArgumentService.saveAll(domain.getArguments());
                domain.setArguments(new HashSet<>(attributes));
            }
        }
        return herodotusTslFunctionService.save(domain);
    }
}
