/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
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
