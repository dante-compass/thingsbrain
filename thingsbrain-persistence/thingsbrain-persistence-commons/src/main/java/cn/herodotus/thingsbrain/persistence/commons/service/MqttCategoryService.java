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

package cn.herodotus.thingsbrain.persistence.commons.service;

import cn.herodotus.dante.data.commons.service.BaseWriteAndPageService;
import cn.herodotus.thingsbrain.persistence.commons.domain.MqttCategory;
import cn.herodotus.thingsbrain.persistence.commons.enums.Action;
import cn.herodotus.thingsbrain.persistence.commons.enums.Area;
import cn.herodotus.thingsbrain.persistence.commons.enums.Purpose;
import org.springframework.data.domain.Page;

/**
 * <p>Description: 物联网 Mqtt 主题类别管理统一定义 Service</p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:51
 */
public interface MqttCategoryService extends BaseWriteAndPageService<MqttCategory, String> {

    /**
     * 分页条件查询 {@link MqttCategory}
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示数量
     * @param area       Mqtt 主题使用区域 {@link Area}
     * @param action     Mqtt 主题操作 {@link Action}
     * @param purpose    Mqtt 主题用途 {@link Purpose}
     * @return 分页数据 {@link Page}
     */
    Page<MqttCategory> findByCondition(int pageNumber, int pageSize, Area area, Action action, Purpose purpose);

}
