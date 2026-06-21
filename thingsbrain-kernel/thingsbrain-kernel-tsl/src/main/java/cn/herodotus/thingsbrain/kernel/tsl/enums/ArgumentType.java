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

package cn.herodotus.thingsbrain.kernel.tsl.enums;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.core.domain.DictionaryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 物模型数据属性类型枚举 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/2 18:52
 */
@Schema(name = "物模型数据属性类型枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArgumentType implements DictionaryEnum {

    INTEGER(ArgumentType.INTEGER_SPECS, "int32(整数型)"),
    FLOAT(ArgumentType.FLOAT_SPECS, "float(单精度浮点型)"),
    DOUBLE(ArgumentType.DOUBLE_SPECS, "double(双精度浮点型)"),
    TEXT(ArgumentType.TEXT_SPECS, "text(字符串)"),
    DATE(ArgumentType.DATE_SPECS, "date(时间型)"),
    BOOLEAN(ArgumentType.BOOLEAN_SPECS, "bool(布尔型)"),
    ENUM(ArgumentType.ENUM_SPECS, "enum(枚举型)"),
    STRUCT(ArgumentType.STRUCT_SPECS, "struct(结构体)");

    public static final String INTEGER_SPECS = "int";
    public static final String FLOAT_SPECS = "float";
    public static final String DOUBLE_SPECS = "double";
    public static final String TEXT_SPECS = "text";
    public static final String DATE_SPECS = "date";
    public static final String BOOLEAN_SPECS = "bool";
    public static final String ENUM_SPECS = "enum";
    public static final String STRUCT_SPECS = "struct";

    private static final Map<String, ArgumentType> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (ArgumentType argumentType : ArgumentType.values()) {
            INDEX_MAP.put(argumentType.getValue(), argumentType);
            DICTIONARIES.add(argumentType.getDictionary(argumentType.name(), argumentType.ordinal()));
        }
    }

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    ArgumentType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static ArgumentType get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
