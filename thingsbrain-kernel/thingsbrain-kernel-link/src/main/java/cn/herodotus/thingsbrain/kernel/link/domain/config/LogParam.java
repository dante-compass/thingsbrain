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

package cn.herodotus.thingsbrain.kernel.link.domain.config;

import cn.herodotus.thingsbrain.kernel.link.definition.DeviceModule;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 设备上报日志内容 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 13:27
 */
public class LogParam extends DeviceModule {

    public LogParam() {
    }

    /**
     * 日志的采集时间，为设备本地UTC时间，包含时区信息，以毫秒计，格式为“yyyy-MM-dd'T'HH:mm:ss.SSSZ”。 可上报其它字符串格式，但不利于问题排查，不推荐使用。
     */
    private String utcTime;
    /**
     * 日志级别。可以使用默认日志级别，也可以自定义日志级别。默认日志级别从高到低为：
     * · FATAL
     * · ERROR
     * · WARN
     * · INFO
     * · DEBUG
     */
    private String logLevel;
    /**
     * 结果状态码，Sting类型的数字。
     */
    private String code;
    /**
     * 可选参数，上下文跟踪内容，设备端使用Alink协议消息的id，App端使用TraceId（追踪ID）。
     */
    private String traceContext;
    /**
     * 日志内容详情
     */
    private String logContent;

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTraceContext() {
        return traceContext;
    }

    public void setTraceContext(String traceContext) {
        this.traceContext = traceContext;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("utcTime", utcTime)
                .add("logLevel", logLevel)
                .add("code", code)
                .add("traceContext", traceContext)
                .add("logContent", logContent)
                .addValue(super.toString())
                .toString();
    }
}
