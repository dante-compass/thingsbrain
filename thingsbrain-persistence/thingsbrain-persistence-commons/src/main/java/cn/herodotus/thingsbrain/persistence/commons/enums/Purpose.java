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

package cn.herodotus.thingsbrain.persistence.commons.enums;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.core.domain.DictionaryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Mqtt 主题用途 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/6/27 12:25
 */
@Schema(name = "Mqtt 主题用途")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Purpose implements DictionaryEnum {

    KERNEL("0", "物联网平台核心主题"),
    CUSTOMIZE("1", "用户自定义主题"),
    LINK("2", "自定义 Link 协议主题");

    private static final Map<Integer, Purpose> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (Purpose purpose : Purpose.values()) {
            INDEX_MAP.put(purpose.ordinal(), purpose);
            DICTIONARIES.add(purpose.getDictionary(purpose.name(), purpose.ordinal()));
        }
    }

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    Purpose(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Purpose get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    public static Purpose parse(Integer index) {
        if (ObjectUtils.isNotEmpty(index)) {
            return Purpose.get(index);
        }
        return null;
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
