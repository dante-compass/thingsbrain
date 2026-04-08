package org.dromara.thingsbrain.monolith.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.FunctionConfiguration;

/**
 * <p>Description: ThingsBrain 单体版本应用 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/4/8 23:52
 */
@SpringBootApplication(exclude = {FunctionConfiguration.class})
public class ThingsBrainApplication {

    static void main(String[] args) {
        SpringApplication.run(ThingsBrainApplication.class, args);
    }
}
