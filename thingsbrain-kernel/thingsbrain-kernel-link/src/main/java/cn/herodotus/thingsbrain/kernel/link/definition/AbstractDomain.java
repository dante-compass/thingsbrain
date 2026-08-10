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

import cn.herodotus.dante.core.domain.BaseModel;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: Herodotus Link 协议数据交互数据实体基础定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/28 23:42
 */
public abstract class AbstractDomain<T> implements BaseModel {
    /**
     * 版本号。对应关系
     * 对于"设备属性、事件、服务": 为 String 类型，表示为协议版本号，目前协议版本号唯一取值为1.0
     * 对于"设备影子数据流": 为 Long 类型，如果version设置为-1时，表示清空设备影子数据，设备影子会接收设备端的请求，并将设备影子版本更新为0
     */
    private T version;

    public T getVersion() {
        return version;
    }

    public void setVersion(T version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .addValue(super.toString())
                .toString();
    }
}
