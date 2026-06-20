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
import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.core.domain.DictionaryEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 节点类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/8/22 16:56
 */
@Schema(name = "节点类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NodeType implements DictionaryEnum {

    /**
     * 具有IP地址，可直接连接物联网平台，且不能挂载子设备，但可作为子设备挂载到网关下的设备
     */
    DIRECTLY_CONNECTED_DEVICE("0", "直连设备"),
    /**
     * 不直接连接物联网平台，而是通过网关设备接入物联网平台的设备。网关与子设备说明，请参见网关与子设备
     */
    GATEWAY_SUB_DEVICE("1", "网关子设备"),
    /**
     * 可以挂载子设备的直连设备。网关具有子设备管理模块，可以维持子设备的拓扑关系，将与子设备的拓扑关系同步到云端
     */
    GATEWAY_DEVICE("2", "网关设备");

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "说明")
    private final String label;

    private static final Map<Integer, NodeType> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (NodeType nodeType : NodeType.values()) {
            INDEX_MAP.put(nodeType.ordinal(), nodeType);
            DICTIONARIES.add(nodeType.getDictionary(nodeType.name(), nodeType.ordinal()));
        }
    }

    NodeType(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public static NodeType get(Integer index) {
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
