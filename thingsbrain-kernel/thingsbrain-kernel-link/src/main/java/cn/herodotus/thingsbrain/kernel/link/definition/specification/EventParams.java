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

package cn.herodotus.thingsbrain.kernel.link.definition.specification;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Event 上报参数标准定义 </p>
 * <p>
 * 对应格式：
 * <pre>
 * {
 * 	"alarmEvent": {
 * 		"value": {
 * 			"Power": "on",
 * 			"WF": "2"
 *      },
 * 		"time": 1524448722000
 *    },
 * 	"alertEvent": {
 * 		"value": {
 * 			"Power": "off",
 * 			"WF": "3"
 *      },
 * 		"time": 1524448722000
 *    }
 * }
 * </pre>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/15 22:07
 */
public class EventParams extends HashMap<String, EventParamEntry> {

    public EventParams() {

    }

    public EventParams(String key, Map<String, Object> value) {
        this.add(key, value);
    }

    public EventParams(String key, Map<String, Object> value, Long time) {
        this.add(key, value, time);
    }

    public void add(String key, Map<String, Object> value) {
        if (StringUtils.isNotBlank(key) && MapUtils.isNotEmpty(value)) {
            this.put(key, new EventParamEntry(value));
        }
    }

    public void add(String key, Map<String, Object> value, Long time) {
        if (StringUtils.isNotBlank(key) && MapUtils.isNotEmpty(value) && ObjectUtils.isNotEmpty(time)) {
            this.put(key, new EventParamEntry(value, time));
        }
    }
}
