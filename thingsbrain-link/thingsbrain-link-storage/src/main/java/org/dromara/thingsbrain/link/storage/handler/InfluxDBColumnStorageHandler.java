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

package org.dromara.thingsbrain.link.storage.handler;

import cn.herodotus.thingsbrain.kernel.link.domain.specification.*;
import com.influxdb.v3.client.InfluxDBClient;
import com.influxdb.v3.client.Point;
import com.influxdb.v3.client.write.WritePrecision;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.nosql.influxdb3.definition.AbstractInfluxDB3Service;
import org.dromara.dante.nosql.influxdb3.pool.InfluxDB3ClientObjectPool;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import org.dromara.thingsbrain.link.commons.definition.DataStorageHandler;
import org.dromara.thingsbrain.link.commons.exception.DataStorageException;
import org.dromara.thingsbrain.link.storage.constant.StorageConstants;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: InfluxDB 列式存储处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/11/1 17:14
 */

public class InfluxDBColumnStorageHandler extends AbstractInfluxDB3Service implements DataStorageHandler {

    public InfluxDBColumnStorageHandler(InfluxDB3ClientObjectPool objectPool) {
        super(objectPool);
    }

    @Override
    public Map<String, Object> property(String productKey, String deviceName, EventPropertyPost request) throws DataStorageException {

        InfluxDBClient client = this.getClient();

        String measurement = MessageFormat.format(StorageConstants.INFLUXDB_MEASUREMENT_PROPERTY, productKey);

        List<Point> points = request
                .entrySet()
                .stream()
                .map(entry -> Point
                        .measurement(measurement)
                        .setTag(KernelConstants.KEY__PRODUCT_KEY, productKey)
                        .setTag(KernelConstants.KEY__DEVICE_NAME, deviceName)
                        .setField(ProtocolConstants.VARIABLE__PROPERTY, entry.getKey())
                        .setField(ProtocolConstants.PARAMETER__VALUE, String.valueOf(entry.getValue().getValue()))
                        .setTimestamp(ObjectUtils.isNotEmpty(entry.getValue().getTime()) ? entry.getValue().getTime() : Instant.now().toEpochMilli(), WritePrecision.MS))
                .toList();

        try {
            client.writePoints(points);
        } catch (Exception e) {
            throw new DataStorageException("Post property data as column store catch error", e);
        } finally {
            this.close(client);
        }

        return Map.of();
    }

    @Override
    public Map<String, Object> event(String productKey, String deviceName, String identifier, EventIdentifierPost request) throws DataStorageException {
        InfluxDBClient client = this.getClient();

        String measurement = MessageFormat.format(StorageConstants.INFLUXDB_MEASUREMENT_EVENT, productKey, identifier);

        List<Point> points = request.getValue()
                .entrySet()
                .stream()
                .map(entry -> Point
                        .measurement(measurement)
                        .setTag(KernelConstants.KEY__PRODUCT_KEY, productKey)
                        .setTag(KernelConstants.KEY__DEVICE_NAME, deviceName)
                        .setTag(ProtocolConstants.VARIABLE__IDENTIFIER, identifier)
                        .setField(ProtocolConstants.VARIABLE__PROPERTY, entry.getKey())
                        .setField(ProtocolConstants.PARAMETER__VALUE, String.valueOf(entry.getValue()))
                        .setTimestamp(ObjectUtils.isNotEmpty(request.getTime()) ? request.getTime() : Instant.now().toEpochMilli(), WritePrecision.MS))
                .toList();

        try {
            client.writePoints(points);
        } catch (Exception e) {
            throw new DataStorageException("Post event data as column store catch error", e);
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
