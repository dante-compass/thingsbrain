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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.dante.core.domain.Dictionary;
import org.dromara.dante.core.domain.DictionaryEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 网关协议 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/22 17:06
 */
@Schema(name = "接入网关协议")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatewayProtocol implements DictionaryEnum {
    /**
     * 表示子设备和网关之间是其它标准或私有协议
     */
    CUSTOM("0", "自定义"),
    /**
     * 表示子设备和网关之间的通讯协议是Modbus
     */
    MODBUS("1", "Modbus"),
    /**
     * 表示子设备和网关之间的通讯协议是OPC UA
     */
    OPC_UA("2", "OPC UA"),
    /**
     * 表示子设备和网关之间的通讯协议是ZigBee
     */
    ZIGBEE("3", "ZigBee"),
    /**
     * 表示子设备和网关之间的通讯协议是BLE
     */
    BLE("4", "BLE");

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    private static final Map<Integer, GatewayProtocol> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (GatewayProtocol gatewayProtocol : GatewayProtocol.values()) {
            INDEX_MAP.put(gatewayProtocol.ordinal(), gatewayProtocol);
            DICTIONARIES.add(gatewayProtocol.getDictionary(gatewayProtocol.name(), gatewayProtocol.ordinal()));
        }
    }

    GatewayProtocol(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static GatewayProtocol get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getValue() {
        return value;
    }
}
