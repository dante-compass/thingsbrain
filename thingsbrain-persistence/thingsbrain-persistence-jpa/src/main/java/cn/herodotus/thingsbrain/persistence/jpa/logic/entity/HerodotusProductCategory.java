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

package cn.herodotus.thingsbrain.persistence.jpa.logic.entity;

import cn.herodotus.thingsbrain.persistence.commons.constant.PersistenceConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.dromara.dante.data.jpa.entity.AbstractSysEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>Description: 物联网产品分类 Jpa 存储实体定义 </p>
 * <p>
 * 物联网产品分类，方便对产品进行归类。设计为树形结构。
 * <p>
 * 产品分类与产品为一对多关系，产品为主、产品分类为从关系。当前还没有形成固定的模版，如果有了模版之后，可以增加品类的类型：标准品类和自定义品类
 * · 选择标准品类不会关联预定义了物模型功能。
 * · 选择自定义品类，无需自定义物模型功能。
 *
 * @author : gengwei.zheng
 * @date : 2024/8/9 16:50
 */
@Entity
@Table(name = "iot_product_category", indexes = {@Index(name = "iot_product_category_id_idx", columnList = "category_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = PersistenceConstants.REGION_IOT_PRODUCT_CATEGORY)
public class HerodotusProductCategory extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "category_id", length = 64)
    private String categoryId;

    @Column(name = "category_name", length = 128)
    private String categoryName;

    @Column(name = "scene_id", length = 64)
    private String sceneId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
                .add("categoryId", categoryId)
                .add("categoryName", categoryName)
                .add("sceneId", sceneId)
                .toString();
    }
}
