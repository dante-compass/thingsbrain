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

package cn.herodotus.thingsbrain.link.storage.handler;

import cn.herodotus.dante.core.utils.StringTemplateUtils;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.link.definition.specification.PropertyParamEntry;
import cn.herodotus.thingsbrain.kernel.link.domain.specification.*;
import cn.herodotus.thingsbrain.link.commons.definition.DataStorageHandler;
import cn.herodotus.thingsbrain.link.commons.exception.DataStorageException;
import cn.herodotus.thingsbrain.link.storage.constant.StorageConstants;
import cn.herodotus.thingsbrain.nosql.influxdb3.definition.AbstractInfluxDB3Service;
import cn.herodotus.thingsbrain.nosql.influxdb3.pool.InfluxDB3ClientObjectPool;
import com.influxdb.v3.client.InfluxDBClient;
import com.influxdb.v3.client.Point;
import com.influxdb.v3.client.write.WritePrecision;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: InfluxDB 行式存储处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/1 17:14
 */
public class InfluxDBRowStorageHandler extends AbstractInfluxDB3Service implements DataStorageHandler {

    public InfluxDBRowStorageHandler(InfluxDB3ClientObjectPool objectPool) {
        super(objectPool);
    }

    @Override
    public Map<String, Object> property(String productKey, String deviceName, EventPropertyPost request) throws DataStorageException {

        InfluxDBClient client = this.getClient();

        String measurement = StringTemplateUtils.replace(StorageConstants.INFLUXDB_MEASUREMENT_PROPERTY, Map.of(KernelConstants.KEY__PRODUCT_KEY, productKey));

        ParamWrapper paramWrapper = new ParamWrapper(request);

        Point point = Point.measurement(measurement)
                .setTag(KernelConstants.KEY__PRODUCT_KEY, productKey)
                .setTag(KernelConstants.KEY__DEVICE_NAME, deviceName)
                .setFields(paramWrapper.getParams())
                .setTimestamp(paramWrapper.getTime(), WritePrecision.MS);

        try {
            client.writePoint(point);
        } catch (Exception e) {
            throw new DataStorageException("Post property data as row store catch error", e);
        } finally {
            this.close(client);
        }

        return Map.of();
    }

    static class ParamWrapper {

        private Long time;
        private final Map<String, Object> params;

        public ParamWrapper(EventPropertyPost data) {

            params = new HashMap<>();

            for (Map.Entry<String, PropertyParamEntry> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue().getValue());

                Long time = entry.getValue().getTime();
                if (ObjectUtils.isNotEmpty(time)) {
                    this.time = time;
                }
            }

            if (ObjectUtils.isEmpty(this.time)) {
                this.time = Instant.now().toEpochMilli();
            }
        }

        public Long getTime() {
            return time;
        }

        public Map<String, Object> getParams() {
            return params;
        }
    }

    @Override
    public Map<String, Object> event(String productKey, String deviceName, String identifier, EventIdentifierPost request) throws DataStorageException {

        InfluxDBClient client = this.getClient();

        String measurement = StringTemplateUtils.replace(StorageConstants.INFLUXDB_MEASUREMENT_EVENT, Map.of(ProtocolConstants.VARIABLE__IDENTIFIER, identifier));

        Point point = Point.measurement(measurement)
                .setTag(KernelConstants.KEY__PRODUCT_KEY, productKey)
                .setTag(KernelConstants.KEY__DEVICE_NAME, deviceName)
                .setTag(ProtocolConstants.VARIABLE__IDENTIFIER, identifier)
                .setFields(request.getValue())
                .setTimestamp(request.getTime(), WritePrecision.MS);

        try {
            client.writePoint(point);
        } catch (Exception e) {
            throw new DataStorageException("Post property data as row store catch error", e);
        } finally {
            this.close(client);
        }

        return Map.of();
    }

    @Override
    public Map<String, Object> batch(String productKey, String deviceName, EventPropertyBatchPost request) throws DataStorageException {
        return Map.of();
    }

    @Override
    public Map<String, Object> history(String productKey, String deviceName, EventPropertyHistoryPost request) throws DataStorageException {
        return Map.of();
    }

    @Override
    public Map<String, Object> pack(String productKey, String deviceName, EventPropertyPackPost request) throws DataStorageException {
        return Map.of();
    }
}
