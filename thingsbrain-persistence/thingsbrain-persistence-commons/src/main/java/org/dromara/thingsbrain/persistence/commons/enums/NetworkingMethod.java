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

package org.dromara.thingsbrain.persistence.commons.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.dante.core.domain.Dictionary;
import org.dromara.dante.core.domain.DictionaryEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 联网方式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/22 21:38
 */
@Schema(name = "联网方式")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NetworkingMethod implements DictionaryEnum {

    WIFI("0", "WiFi"),
    CELLULAR("1", "蜂窝(2G/3G/4G/5G)"),
    ETHERNET("2", "以太网"),
    LORAWAN("3", "LoRaWAN"),
    OTHERS("4", "其它");

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    private static final Map<Integer, NetworkingMethod> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (NetworkingMethod networkingMethod : NetworkingMethod.values()) {
            INDEX_MAP.put(networkingMethod.ordinal(), networkingMethod);
            DICTIONARIES.add(networkingMethod.getDictionary(networkingMethod.name(), networkingMethod.ordinal()));
        }
    }

    NetworkingMethod(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public static NetworkingMethod get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
