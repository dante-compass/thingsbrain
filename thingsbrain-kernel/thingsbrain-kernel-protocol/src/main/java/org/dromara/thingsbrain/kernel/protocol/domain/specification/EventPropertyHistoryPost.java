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

package org.dromara.thingsbrain.kernel.protocol.domain.specification;

import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import org.dromara.thingsbrain.kernel.protocol.definition.specification.EventParams;
import org.dromara.thingsbrain.kernel.protocol.definition.specification.PropertyParams;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 物模型历史数据请求实体 </p>
 * <p>
 * 参考格式：
 * <pre>
 * {
 *     "id": "123",
 *     "version": "1.0",
 *     "sys":{
 *         "ack":0
 *     },
 *     "method": "thing.event.property.history.post",
 *     "params": [
 *         {
 *             "identity": {
 *                 "productKey": "",
 *                 "deviceName": ""
 *             },
 *             "properties": [
 *                 {
 *                     "Power": {
 *                         "value": "on",
 *                         "time": 1524448722000
 *                     },
 *                     "WF": {
 *                         "value": "3",
 *                         "time": 1524448722000
 *                     }
 *                 },
 *                 {
 *                     "Power": {
 *                         "value": "on",
 *                         "time": 1524448722000
 *                     },
 *                     "WF": {
 *                         "value": "3",
 *                         "time": 1524448722000
 *                     }
 *                 }
 *             ],
 *             "events": [
 *                 {
 *                     "alarmEvent": {
 *                         "value": {
 *                             "Power": "on",
 *                             "WF": "2"
 *                         },
 *                         "time": 1524448722000
 *                     },
 *                     "alertEvent": {
 *                         "value": {
 *                             "Power": "off",
 *                             "WF": "3"
 *                         },
 *                         "time": 1524448722000
 *                     }
 *                 }
 *             ]
 *         },
 *         {
 *             "identity": {
 *                 "productKey": "",
 *                 "deviceName": ""
 *             },
 *             "properties": [
 *                 {
 *                     "Power": {
 *                         "value": "on",
 *                         "time": 1524448722000
 *                     },
 *                     "WF": {
 *                         "value": "3",
 *                         "time": 1524448722000
 *                     }
 *                 }
 *             ],
 *             "events": [
 *                 {
 *                     "alarmEvent": {
 *                         "value": {
 *                             "Power": "on",
 *                             "WF": "2"
 *                         },
 *                         "time": 1524448722000
 *                     },
 *                     "alertEvent": {
 *                         "value": {
 *                             "Power": "off",
 *                             "WF": "3"
 *                         },
 *                         "time": 1524448722000
 *                     }
 *                 }
 *             ]
 *         }
 *     ]
 * }
 * </pre>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/1 23:53
 */
public class EventPropertyHistoryPost implements Serializable {

    private Identifier identity;

    private List<PropertyParams> properties;

    private List<EventParams> events;

    public Identifier getIdentity() {
        return identity;
    }

    public void setIdentity(Identifier identity) {
        this.identity = identity;
    }

    public List<PropertyParams> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyParams> properties) {
        this.properties = properties;
    }

    public List<EventParams> getEvents() {
        return events;
    }

    public void setEvents(List<EventParams> events) {
        this.events = events;
    }
}
