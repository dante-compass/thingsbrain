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

package cn.herodotus.thingsbrain.mqtt.autoconfigure.processor;

import cn.herodotus.dante.cache.utils.JetCacheUtils;
import cn.herodotus.thingsbrain.mqtt.commons.constant.MqttConstants;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessageDuplicateInspector;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

/**
 * <p>Description: Mqtt 重复消息保护器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/6/30 14:45
 */
public class DefaultMqttMessageDuplicateInspector implements MqttMessageDuplicateInspector {

    /**
     * 当前缓存值使用 topic，只是为了防止缓存空值。后续可结合业务调整为需要的数据。
     */
    private final Cache<String, String> cache;

    public DefaultMqttMessageDuplicateInspector() {
        this.cache = JetCacheUtils.create(MqttConstants.CACHE_THINGSBRAIN_MESSAGE_ID, CacheType.BOTH, Duration.ofHours(1), true);
    }

    /**
     * 判断缓存中是否已经存在指定的 MessageId
     *
     * @param messageId 消息 ID
     * @return true 包含，false 不包含或者 messageId 为空
     */
    private boolean containMessageId(String messageId) {
        if (StringUtils.isNotBlank(messageId)) {
            String value = cache.get(messageId);
            return StringUtils.isNotBlank(value);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDuplicate(MqttMessageDetails details) {
        return containMessageId(details.getMessageId()) || details.isDuplicate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void record(MqttMessageDetails details) {
        if (StringUtils.isNotBlank(details.getMessageId())) {
            cache.put(details.getMessageId(), details.getTopic());
        }
    }
}
