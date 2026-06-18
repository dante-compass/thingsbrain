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

package cn.herodotus.thingsbrain.kernel.link.definition;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 扩展功能的参数，其下包含各功能字段。 </p>
 * <p>
 * 使用设备端SDK开发时，如果未设置扩展功能，则无此参数，相关功能保持默认配置。
 *
 * @author : gengwei.zheng
 * @date : 2024/8/31 12:12
 */
public class SysDomain implements Serializable {

    /**
     * 1：云端返回响应数据。
     * 0：云端不返回响应数据。
     */
    private Integer ack = 0;

    public SysDomain() {
    }

    public SysDomain(Integer ack) {
        this.ack = ack;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ack", ack)
                .toString();
    }
}
