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

package org.dromara.thingsbrain.link.manager.subset;

import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import org.dromara.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import org.dromara.thingsbrain.kernel.link.definition.Signature;
import org.dromara.thingsbrain.link.commons.definition.SubsetTopoManager;

import java.util.List;

/**
 * <p>Description: 管理拓扑关系管理器 </p>
 * <p>
 * 子设备身份注册后，需由网关向物联网平台上报网关与子设备的拓扑关系，然后进行子设备上线。
 * <p>
 * 子设备上线过程中，物联网平台会校验子设备的身份和与网关的拓扑关系。所有校验通过，才会建立并绑定子设备逻辑通道至网关物理通道上。子设备与物联网平台的数据上下行通信与直连设备的通信协议一致，协议上不需要露出网关信息。
 * <p>
 * 删除拓扑关系后，子设备不能再通过网关上线。系统将提示拓扑关系不存在，认证不通过等错误
 *
 * @author : gengwei.zheng
 * @date : 2025/6/16 16:32
 */
public class DefaultSubsetTopoManager implements SubsetTopoManager {

    @Override
    public List<Identifier> add(String productKey, String deviceName, List<Signature> data) throws InboundMessageProcessingException {
        return List.of();
    }

    @Override
    public List<Identifier> delete(String productKey, String deviceName, List<Identifier> data) throws InboundMessageProcessingException {
        return List.of();
    }

    @Override
    public List<Identifier> get(String productKey, String deviceName) throws InboundMessageProcessingException {
        return List.of();
    }
}
