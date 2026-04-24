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

package org.dromara.thingsbrain.kernel.protocol.domain.ota;

import com.google.common.base.MoreObjects;
import org.dromara.dante.core.domain.BaseEntity;

/**
 * <p>Description: 多个文件的OTA升级包文件信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/19 11:27
 */
public class FileDomain implements BaseEntity {

    /**
     * 升级包文件大小
     */
    private Long fileSize;
    /**
     * 升级包文件的名称
     */
    private String fileName;
    /**
     * 升级包在对象存储（OSS）上的存储地址
     */
    private String fileUrl;
    /**
     * 升级包文件的签名
     */
    private String fileSign;
    /**
     * 当签名方法为MD5时，除了会给sign赋值外还会给md5赋值
     */
    private String fileMd5;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileSign() {
        return fileSign;
    }

    public void setFileSign(String fileSign) {
        this.fileSign = fileSign;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fileSize", fileSize)
                .add("fileName", fileName)
                .add("fileUrl", fileUrl)
                .add("fileSign", fileSign)
                .add("fileMd5", fileMd5)
                .toString();
    }
}
