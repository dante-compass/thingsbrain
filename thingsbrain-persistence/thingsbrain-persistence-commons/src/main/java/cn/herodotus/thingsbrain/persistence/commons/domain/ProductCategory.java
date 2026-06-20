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

package cn.herodotus.thingsbrain.persistence.commons.domain;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.herodotus.dante.data.commons.entity.AbstractSysEntity;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: 物联网产品品类统一实体定义 </p>
 * <p>
 * 定义统一的产品分类实体，方便不同类型 Spring Data Module 间的切换。
 * <p>
 * 注意：Spring Data 有几种方式来区分不同的 Spring Data Module 以实现 Entity 和 Repository 的匹配。
 * 其中将不同 Spring Data Module 的注解放入在同一个实体的方式，例如 MongoDB @Document 注解和 JPA 的 @Entity 注解放入在同一个实体上，是最“坏”的一种方式，Spring Data 无法支持这种方式的识别。
 * 所以最好的方式，就是为不同的 Spring Data Module 定义不同的 Entity 和 Repository，即使 Entity 和 Repository 的内容都是一致的
 * <p>
 * 因为上面的原因，想要实现不同 Spring Data Module 的可切换，就需要定义统一的实体以及接口，通过 {@link Converter} 的转换适配到最终使用的 Spring Data Module。
 *
 * @author : gengwei.zheng
 * @date : 2025/3/31 12:50
 */
@Schema(name = "物联网产品品类统一实体定义")
public class ProductCategory extends AbstractSysEntity {

    @Schema(name = "产品品类ID")
    private String id;

    @Schema(name = "产品品类名称")
    private String name;

    @Schema(name = "所属场景ID", title = "预留属性", description = "为后续支持类似于阿里云物模型库预留字段")
    private String sceneId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("sceneId", sceneId)
                .toString();
    }
}
