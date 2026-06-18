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

package cn.herodotus.thingsbrain.kernel.tsl.describe;

import cn.herodotus.thingsbrain.kernel.commons.constant.SchemaConstants;
import cn.herodotus.thingsbrain.kernel.tsl.definition.AbstractDescribe;
import cn.herodotus.thingsbrain.kernel.tsl.definition.Describe;
import cn.herodotus.thingsbrain.kernel.tsl.domain.Argument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>Description: Struct 类型 JSON Schema 描述 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/4 23:55
 */
public class ObjectDescribe<T extends Argument> extends AbstractDescribe {

    private final List<String> required;

    private final Map<String, Describe> properties;

    public ObjectDescribe(Stream<T> arguments) {
        this.required = new ArrayList<>();
        this.properties = new HashMap<>();
        setType(SchemaConstants.SCHEMA_TYPE_OBJECT);
        arguments.forEach(this::append);
    }

    public List<String> getRequired() {
        return required;
    }

    public Map<String, Describe> getProperties() {
        return properties;
    }

    public void append(T argument) {
        required.add(argument.getIdentifier());
        properties.put(argument.getIdentifier(), argument.getDataType().getSpecs().getDescribe());
    }
}
