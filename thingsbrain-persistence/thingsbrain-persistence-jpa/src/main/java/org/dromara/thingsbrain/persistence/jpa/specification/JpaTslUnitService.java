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

package org.dromara.thingsbrain.persistence.jpa.specification;

import org.dromara.thingsbrain.persistence.commons.domain.TslUnit;
import org.dromara.thingsbrain.persistence.commons.service.TslUnitService;
import org.dromara.thingsbrain.persistence.jpa.converter.ToTslUnitConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslUnit;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslUnitService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * <p>Description: 物联网物模型单位 Service Jpa 实现 </p>
 * <p>
 * 目前进需要可以查询物模型单位即可，用于前端显示物模型单位列表
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 16:07
 */
public class JpaTslUnitService implements TslUnitService {

    private final HerodotusTslUnitService delegate;
    private final Converter<HerodotusTslUnit, TslUnit> toTslUnit;

    public JpaTslUnitService(HerodotusTslUnitService herodotusTslUnitService) {
        this.delegate = herodotusTslUnitService;
        this.toTslUnit = new ToTslUnitConverter();
    }

    @Override
    public Page<TslUnit> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusTslUnit> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toTslUnit::convert);
    }

    @Override
    public Page<TslUnit> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusTslUnit> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toTslUnit::convert);
    }
}
