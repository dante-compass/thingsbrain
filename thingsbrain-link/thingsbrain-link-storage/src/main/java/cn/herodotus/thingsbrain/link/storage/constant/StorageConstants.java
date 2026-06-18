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

package cn.herodotus.thingsbrain.link.storage.constant;

import org.dromara.dante.core.constant.SymbolConstants;
import cn.herodotus.thingsbrain.link.commons.constant.LinkConstants;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/29 16:40
 */
public interface StorageConstants extends LinkConstants {

    String PROPERTY_LINK_STORAGE = PROPERTY_IOT_LINK + ".storage";

    String STORAGE_CASSANDRA = "CASSANDRA";
    String STORAGE_INFLUXDB = "INFLUXDB";
    String STORAGE_CLICKHOUSE = "CLICKHOUSE";
    String STORAGE_TDENGINE = "TDENGINE";

    String STORAGE_MODE_ROW = "ROW";
    String STORAGE_MODE_COLUMN = "COLUMN";

    String INFLUXDB_ROW_STORAGE = STORAGE_INFLUXDB + SymbolConstants.UNDERLINE + STORAGE_MODE_ROW;
    String INFLUXDB_COLUMN_STORAGE = STORAGE_INFLUXDB + SymbolConstants.UNDERLINE + STORAGE_MODE_COLUMN;
    String TDENGINE_ROW_STORAGE = STORAGE_TDENGINE + SymbolConstants.UNDERLINE + STORAGE_MODE_ROW;
    String TDENGINE_COLUMN_STORAGE = STORAGE_TDENGINE + SymbolConstants.UNDERLINE + STORAGE_MODE_COLUMN;
    String CLICKHOUSE_ROW_STORAGE = STORAGE_CLICKHOUSE + SymbolConstants.UNDERLINE + STORAGE_MODE_ROW;
    String CLICKHOUSE_COLUMN_STORAGE = STORAGE_CLICKHOUSE + SymbolConstants.UNDERLINE + STORAGE_MODE_COLUMN;
    String CASSANDRA_ROW_STORAGE = STORAGE_CASSANDRA + SymbolConstants.UNDERLINE + STORAGE_MODE_ROW;
    String CASSANDRA_COLUMN_STORAGE = STORAGE_CASSANDRA + SymbolConstants.UNDERLINE + STORAGE_MODE_COLUMN;

    String INFLUXDB_MEASUREMENT_PROPERTY = "properties_{productKey}";
    String INFLUXDB_MEASUREMENT_EVENT = "event_{identifier}";
}
