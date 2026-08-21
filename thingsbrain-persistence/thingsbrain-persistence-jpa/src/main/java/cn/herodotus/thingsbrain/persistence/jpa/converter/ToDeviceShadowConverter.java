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

package cn.herodotus.thingsbrain.persistence.jpa.converter;

import cn.herodotus.dante.data.jpa.converter.AbstractToAuditEntityConverter;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceShadow;

/**
 * <p>Description: {@link HerodotusDeviceShadow} 转 {@link DeviceShadow} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/8 16:09
 */
public class ToDeviceShadowConverter extends AbstractToAuditEntityConverter<HerodotusDeviceShadow, DeviceShadow> {

    @Override
    public DeviceShadow getInstance() {
        return new DeviceShadow();
    }

    @Override
    public void prepare(HerodotusDeviceShadow source, DeviceShadow target) {
        target.setId(source.getShadowId());
        target.setProductKey(source.getProductKey());
        target.setDeviceName(source.getDeviceName());
        target.setContent(source.getContent());
        target.setVersion(source.getVersion());
    }
}
