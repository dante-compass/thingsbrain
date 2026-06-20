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

package cn.herodotus.thingsbrain.link.storage.enums;

import cn.herodotus.dante.core.domain.Dictionary;
import cn.herodotus.dante.core.domain.DictionaryEnum;
import cn.herodotus.thingsbrain.link.storage.constant.StorageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 时序数据存储策略 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/6 23:55
 */
@Schema(name = "Tsdb 数据存储策略")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StoragePolicy implements DictionaryEnum {

    INFLUXDB_ROW(StorageConstants.INFLUXDB_ROW_STORAGE, "InfluxDB 行式存储"),
    INFLUXDB_COLUMN(StorageConstants.INFLUXDB_ROW_STORAGE, "InfluxDB 列式存储"),
    TDENGINE_ROW(StorageConstants.TDENGINE_ROW_STORAGE, "TDengine 行式存储"),
    TDENGINE_COLUMN(StorageConstants.TDENGINE_ROW_STORAGE, "TDengine 列式存储"),
    CLICKHOUSE_ROW(StorageConstants.CLICKHOUSE_ROW_STORAGE, "ClickHouse 行式存储"),
    CLICKHOUSE_COLUMN(StorageConstants.CLICKHOUSE_ROW_STORAGE, "ClickHouse 列式存储"),
    CASSANDRA_ROW(StorageConstants.CASSANDRA_ROW_STORAGE, "Cassandra 行式存储"),
    CASSANDRA_COLUMN(StorageConstants.CASSANDRA_ROW_STORAGE, "Cassandra 列式存储");

    private static final Map<Integer, StoragePolicy> INDEX_MAP = new HashMap<>();
    private static final List<Dictionary> DICTIONARIES = new ArrayList<>();

    static {
        for (StoragePolicy policy : StoragePolicy.values()) {
            INDEX_MAP.put(policy.ordinal(), policy);
            DICTIONARIES.add(policy.getDictionary(policy.name(), policy.ordinal()));
        }
    }

    StoragePolicy(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Schema(name = "枚举值")
    private final String value;
    @Schema(name = "文字")
    private final String label;

    public static StoragePolicy get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Dictionary> getDictionaries() {
        return DICTIONARIES;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
