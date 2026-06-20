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

package cn.herodotus.thingsbrain.kernel.commons.constant;

import cn.herodotus.dante.core.constant.SymbolConstants;

/**
 * <p>Description: 物联网 Link 协议主题常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/27 22:34
 */
public interface MethodConstants {

    /**
     * 格式：thing.event.${tsl.event.identifier}.post
     */
    String METHOD_FORMAT__POST_EVENT = ProtocolConstants.PREFIX__EVENT + SymbolConstants.PERIOD + ProtocolConstants.FORMAT_PLACEHOLDER__IDENTIFIER_EVENT + SymbolConstants.PERIOD + ProtocolConstants.ACTION__POST;
    /**
     * 格式：thing.event.${tsl.service.identifier}
     */
    String METHOD_FORMAT__INVOKE_SERVICE = ProtocolConstants.PREFIX__SERVICE + SymbolConstants.PERIOD + ProtocolConstants.FORMAT_PLACEHOLDER__IDENTIFIER_SERVICE;


    /* -------------------- 管理拓扑关系 -------------------- */

    /**
     * 添加设备拓扑关系（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/topo/add
     */
    String METHOD__THING_TOPO_ADD = "thing.topo.add";

    /**
     * 删除设备的拓扑关系（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/topo/delete
     */
    String METHOD__THING_TOPO_DELETE = "thing.topo.delete";

    /**
     * 获取设备的拓扑关系（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/topo/get
     */
    String METHOD__THING_TOPO_GET = "thing.topo.get";

    /**
     * 通知网关添加设备拓扑关系（数据下行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/topo/add/notify
     */
    String METHOD__THING_TOPO_ADD_NOTIFY = "thing.topo.add.notify";

    /**
     * 通知网关拓扑关系变化（数据下行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/topo/change
     */
    String METHOD__THING_TOPO_CHANGE = "thing.topo.change";


    /* -------------------- 子设备上下线 -------------------- */

    /**
     * 子设备上线（数据上行）
     * <p>
     * ext/session/${productKey}/${deviceName}/combine/login
     */
    String METHOD__COMBINE_LOGIN = "combine.login";
    /**
     * 子设备批量上线（数据上行）
     * <p>
     * ext/session/${productKey}/${deviceName}/combine/batch_login
     */
    String METHOD__COMBINE_BATCH_LOGIN = "combine.batch_login";
    /**
     * 子设备下线（数据上行）
     * <p>
     * ext/session/${productKey}/${deviceName}/combine/logout
     */
    String METHOD__COMBINE_LOGOUT = "combine.logout";
    /**
     * 子设备批量下线（数据上行）
     * <p>
     * ext/session/${productKey}/${deviceName}/combine/batch_login
     */
    String METHOD__COMBINE_BATCH_LOGOUT = "combine.batch_logout";


    /* -------------------- 物模型通信Topic -------------------- */

    /**
     * 设备上报属性（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/event/property/post
     */
    String METHOD__THING_EVENT_PROPERTY_POST = "thing.event.property.post";

    /**
     * 设备上报事件（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/event/${tsl.event.identifier}/post
     */
    String METHOD__THING_EVENT_IDENTIFIER = "thing.event";

    /**
     * 设置设备属性（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/service/property/set
     */
    String METHOD__THING_SERVICE_PROPERTY_SET = "thing.service.property.set";

    /**
     * 设置设备属性（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/service/property/get
     */
    String METHOD__THING_SERVICE_PROPERTY_GET = "thing.service.property.get";

    /**
     * 设备服务调用（异步调用）（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/service/${tsl.service.identifier}
     */
    String METHOD__THING_SERVICE_IDENTIFIER = "thing.service";

    /**
     * 网关批量上报数据（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/event/property/pack/post
     */
    String METHOD__THING_EVENT_PROPERTY_PACK_POST = "thing.event.property.pack.post";

    /**
     * 物模型历史数据上报（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/event/property/history/post
     */
    String METHOD__THING_EVENT_PROPERTY_HISTORY_POST = "thing.event.property.history.post";

    /**
     * 设备批量上报属性、事件（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/event/property/batch/post
     */
    String METHOD__THING_EVENT_PROPERTY_BATCH_POST = "thing.event.property.batch.post";


    /* -------------------- 子设备禁用、启用、删除 -------------------- */

    /**
     * 禁用子设备（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/disable
     */
    String METHOD__THING_DISABLE = "thing.disable";

    /**
     * 启用被禁用的子设备（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/enable
     */
    String METHOD__THING_ENABLE = "thing.enable";

    /**
     * 删除子设备（数据下行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/delete
     */
    String METHOD__THING_DELETE = "thing.delete";

    /* -------------------- 设备标签 -------------------- */

    /**
     * 上报标签信息（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/deviceinfo/update
     */
    String METHOD__THING_DEVICEINFO_UPDATE = "thing.deviceinfo.update";

    /**
     * 查询标签信息（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/deviceinfo/get
     */
    String METHOD__THING_DEVICEINFO_GET = "thing.deviceinfo.get";

    /**
     * 删除标签信息（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/deviceinfo/delete
     */
    String METHOD__THING_DEVICEINFO_DELETE = "thing.deviceinfo.delete";


    /* -------------------- OTA 升级 -------------------- */

    /**
     * 设备上报OTA模块版本（数据上行）
     * <p>
     * 设备通过这个Topic上报当前的OTA模块版本信息。
     * <p>
     * 阿里云标准：/ota/device/inform/${productKey}/${deviceName}
     * 改为：/ota/${productKey}/${deviceName}/device/inform
     */
    String METHOD__OTA_DEVICE_INFORM = "device.inform";

    /**
     * 物联网平台推送OTA升级包信息（数据下行）
     * <p>
     * 物联网平台通过这个Topic推送OTA升级包信息， 设备订阅该Topic可以获得升级包信息。
     * <p>
     * 阿里云标准：/ota/device/upgrade/${productKey}/${deviceName}
     * 改为：/ota/${productKey}/${deviceName}/device/upgrade
     */
    String METHOD__OTA_DEVICE_UPGRADE = "device.upgrade";

    /**
     * 设备上报升级进度（数据上行）
     * <p>
     * OTA升级过程中，设备可以通过这个Topic上报OTA升级的进度百分比。
     * <p>
     * 阿里云标准：/ota/device/progress/${productKey}/${deviceName}
     * 改为：/ota/${productKey}/${deviceName}/device/progress
     */
    String METHOD__OTA_DEVICE_PROGRESS = "device.progress";

    /**
     * 设备请求OTA升级包信息（数据上行）
     * <p>
     * /sys/${productKey}/${deviceName}/thing/ota/firmware/get
     */
    String METHOD__OTA_FIRMWARE_GET = "thing.ota.firmware.get";

    /**
     * 设备请求下载文件分片（数据下行）
     * <p>
     * 升级包下载协议为MQTT时，设备端获取OTA升级包信息后，可通过以下Topic分片下载OTA升级包文件。
     * <p>
     * /sys/${productKey}/${deviceName}/thing/file/download
     */
    String METHOD__FILE_DOWNLOAD = "thing.file.download";


    /* -------------------- 远程配置 -------------------- */

    /**
     * 设备主动请求配置信息（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/config/get
     */
    String METHOD__THING_CONFIG_GET = "thing.config.get";
    /**
     * 配置推送（数据下行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/config/push
     */
    String METHOD__THING_CONFIG_PUSH = "thing.config.push";


    /* -------------------- 设备日志上报 -------------------- */

    /**
     * 设备获取日志配置（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/config/log/get
     */
    String METHOD__THING_CONFIG_LOG_GET = "thing.config.log.get";
    /**
     * 设备接收订阅云端推送日志配置（数据下行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/config/log/push
     */
    String METHOD__THING_CONFIG_LOG_PUSH = "thing.config.log.push";
    /**
     * 设备上报日志内容（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/config/log/post
     */
    String METHOD__THING_LOG_POST = "thing.log.post";


    /* -------------------- 设备影子 -------------------- */

    /**
     * 设备和应用程序发布消息到此Topic。物联网平台收到该Topic的消息后，将消息中的状态更新到设备影子中。
     */
    String METHOD__SHADOW__UPDATE = "update";
    String METHOD__SHADOW__DELETE = "delete";
    String METHOD__SHADOW__GET = ProtocolConstants.ACTION__GET;
    String METHOD__SHADOW__REPLY = "reply";


    /* -------------------- 设备任务 -------------------- */

    /**
     * 设备任务状态更新通知（数据下行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/job/notify
     */
    String METHOD__THING_JOB_NOTIFY = "thing.job.notify";

    /**
     * 获取设备任务详情（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/job/get
     */
    String METHOD__THING_JOB_GET = "thing.jot.get";

    /**
     * 更新任务下作业状态（数据上行）
     * <p>
     * sys/${productKey}/${deviceName}/thing/job/update
     */
    String METHOD__THING_JOB_UPDATE = "thing.jot.update";
}