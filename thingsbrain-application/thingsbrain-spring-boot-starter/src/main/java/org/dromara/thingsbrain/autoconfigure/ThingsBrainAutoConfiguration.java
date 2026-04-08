package org.dromara.thingsbrain.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p>Description: ThingsBrain 物联网自动配置 Starter </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/8 23:59
 */
@AutoConfiguration
public class ThingsBrainAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ThingsBrainAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Starter [ThingsBrain] Configure.");
    }
}
