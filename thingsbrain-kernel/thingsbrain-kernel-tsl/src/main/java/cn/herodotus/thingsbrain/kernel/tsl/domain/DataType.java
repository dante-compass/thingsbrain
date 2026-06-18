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

package cn.herodotus.thingsbrain.kernel.tsl.domain;

import cn.herodotus.thingsbrain.kernel.tsl.specs.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;
import cn.herodotus.thingsbrain.kernel.tsl.definition.Specs;
import cn.herodotus.thingsbrain.kernel.tsl.enums.ArgumentType;
import cn.herodotus.thingsbrain.kernel.tsl.jackson2.SpecificationViews;
import org.dromara.thingsbrain.kernel.tsl.specs.*;

/**
 * <p>Description: 物模型数据类型对象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 17:58
 */
public class DataType {

    /**
     * 参数类型
     */
    @JsonView(SpecificationViews.SimpleView.class)
    private ArgumentType type;
    /**
     * 类型说明
     */
    @JsonView(SpecificationViews.CompleteView.class)
    private Specs specs;

    public DataType() {
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public Specs getSpecs() {
        return specs;
    }

    /**
     * 这里的type 对应的是枚举类 AttributeType。
     * AttributeType 类被标记为 @JsonFormat(shape = JsonFormat.Shape.OBJECT)，意味着序列化成一个对象。所以 Jackson 只会根据 AttributeType 中 @JsonValue 标记的属性值作为转换依据。
     * 如果 AttributeType 就是普通的枚举，那么就使用 Enum.name() 的值作为转换依据。
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = true, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = IntegerSpecs.class, name = ArgumentType.INTEGER_SPECS),
            @JsonSubTypes.Type(value = FloatSpecs.class, name = ArgumentType.FLOAT_SPECS),
            @JsonSubTypes.Type(value = DoubleSpecs.class, name = ArgumentType.DOUBLE_SPECS),
            @JsonSubTypes.Type(value = DateSpecs.class, name = ArgumentType.DATE_SPECS),
            @JsonSubTypes.Type(value = TextSpecs.class, name = ArgumentType.TEXT_SPECS),
            @JsonSubTypes.Type(value = EnumSpecs.class, name = ArgumentType.ENUM_SPECS),
            @JsonSubTypes.Type(value = BooleanSpecs.class, name = ArgumentType.BOOLEAN_SPECS),
            @JsonSubTypes.Type(value = StructSpecs.class, name = ArgumentType.STRUCT_SPECS),
    })
    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("specs", specs)
                .add("type", type)
                .toString();
    }
}
