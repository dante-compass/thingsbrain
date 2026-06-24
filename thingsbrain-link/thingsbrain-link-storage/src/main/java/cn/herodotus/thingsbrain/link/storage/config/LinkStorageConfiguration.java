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

package cn.herodotus.thingsbrain.link.storage.config;

import cn.herodotus.dante.core.support.factory.StrategyFactory;
import cn.herodotus.thingsbrain.link.commons.definition.DataStorageHandler;
import cn.herodotus.thingsbrain.link.storage.constant.StorageConstants;
import cn.herodotus.thingsbrain.link.storage.factory.DataStorageStrategyFactory;
import cn.herodotus.thingsbrain.link.storage.handler.InfluxDBColumnStorageHandler;
import cn.herodotus.thingsbrain.link.storage.handler.InfluxDBRowStorageHandler;
import cn.herodotus.thingsbrain.link.storage.properties.StorageProperties;
import cn.herodotus.thingsbrain.nosql.influxdb3.condition.ConditionalOnInfluxDB3;
import cn.herodotus.thingsbrain.nosql.influxdb3.pool.InfluxDB3ClientObjectPool;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * <p>Description: 自定义 Link 协议上报数据存储模块配置 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/29 16:35
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(StorageProperties.class)
public class LinkStorageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LinkStorageConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Link Storage] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnInfluxDB3
    static class InfluxDBStorageConfiguration {

        @Bean(StorageConstants.INFLUXDB_COLUMN_STORAGE)
        public DataStorageHandler influxDBColumnStorageHandler(InfluxDB3ClientObjectPool influxDB3ClientObjectPool) {
            return new InfluxDBColumnStorageHandler(influxDB3ClientObjectPool);
        }

        @Bean(StorageConstants.INFLUXDB_ROW_STORAGE)
        public DataStorageHandler influxDBRowStorageHandler(InfluxDB3ClientObjectPool influxDB3ClientObjectPool) {
            return new InfluxDBRowStorageHandler(influxDB3ClientObjectPool);
        }
    }

    @Bean
    public StrategyFactory<DataStorageHandler> iotDataStorageStrategyFactory(Map<String, DataStorageHandler> handlers, StorageProperties properties) {
        DataStorageStrategyFactory factory = new DataStorageStrategyFactory(handlers, properties.getStorage().getValue());
        log.trace("[ThingsBrain] |- Bean [Data Storage Factory] Configure.");
        return factory;
    }

}
