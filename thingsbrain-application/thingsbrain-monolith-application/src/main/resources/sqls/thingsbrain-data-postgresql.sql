/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

-- ----------------------------
-- Table data for sys_element
-- ----------------------------
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('d9cd164d-2d4f-4532-9bdb-53bc824b65bf', '2024-08-16 11:54:08.353', '2024-08-16 11:58:19.951', 'system', 0, 'system', NULL, 1, 't', 0, 'views/pages/iot/product/Content.vue', 't', 'f', 'f', 'mdi-cube-scan', 'f', 'IotProductContent', 't', '017ca0e8-1f30-4230-8602-12b6e95b502d', '/iot/product/content', NULL, '产品详情', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('81c90743-f49a-44ff-bed8-f05f7d8ccde7', '2024-08-16 11:56:20.359', '2024-08-16 11:57:34.79', 'system', 0, 'system', NULL, 1, 't', 0, 'views/pages/iot/device/Content.vue', 't', 'f', 'f', 'mdi-folder-network', 'f', 'IotDeviceContent', 't', 'b827346f-494d-4044-8bc9-0ef33f7e9d45', '/iot/device/content', NULL, '设备详情', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('22fd4a47-a12c-49a8-b041-3c5f4b6b788a', '2024-08-16 11:50:30.373', '2024-08-16 11:57:51.287', 'system', 0, 'system', NULL, 1, 't', 0, 'views/pages/iot/product-category/Index.vue', 'f', 't', 't', 'mdi-format-list-checks', 'f', 'IotProductCategory', 't', '7f51cd60-3b17-4f0f-a46a-19028d0c2294', '/iot/product-category', NULL, '品类管理', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('f52e614a-b466-4958-889a-e037495d925c', '2024-08-16 11:51:49.706', '2024-08-16 11:58:06.262', 'system', 0, 'system', NULL, 1, 't', 0, 'views/pages/iot/product-category/Content.vue', 't', 'f', 'f', 'mdi-clipboard-list', 'f', 'IotProductCategoryContent', 't', '22fd4a47-a12c-49a8-b041-3c5f4b6b788a', '/iot/product-category/content', NULL, '分类详情', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('017ca0e8-1f30-4230-8602-12b6e95b502d', '2024-08-16 11:53:08.993', '2024-08-16 12:01:27.068', 'system', 0, 'system', NULL, 2, 't', 0, 'views/pages/iot/product/Index.vue', 'f', 't', 't', 'mdi-cube', 'f', 'IotProduct', 't', '7f51cd60-3b17-4f0f-a46a-19028d0c2294', '/iot/product', NULL, '产品管理', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('7f51cd60-3b17-4f0f-a46a-19028d0c2294', '2024-08-16 11:46:38.873', '2024-08-16 12:02:22.513', 'system', 0, 'system', NULL, 9, 't', 0, 'views/layouts/Index.vue', 'f', 't', 'f', 'mdi-drag-variant', 'f', '', 't', NULL, '/iot', '/iot/product-category', '万物互联管理', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('52be4bb7-9d4c-42fd-8ae7-9efc4ce1c029', '2024-08-30 22:58:28.829', '2024-08-30 22:58:43.313', 'system', 0, 'system', NULL, 0, 't', 0, 'views/pages/iot/product/Info.vue', 't', 'f', 'f', 'mdi-cube-send', 'f', 'IotProductInfo', 't', '017ca0e8-1f30-4230-8602-12b6e95b502d', '/iot/product/info', NULL, '产品信息', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('3e88d6e3-b97d-48eb-b4d4-c2ac1cf444b8', '2024-08-30 22:59:41.313', '2024-08-30 23:00:01.473', 'system', 0, 'system', NULL, 0, 't', 0, 'views/pages/iot/device/Info.vue', 't', 'f', 'f', 'mdi-network-pos', 'f', 'IotDeviceInfo', 't', 'b827346f-494d-4044-8bc9-0ef33f7e9d45', '/iot/device/info', NULL, '设备信息', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('310a9900-400d-43cf-9e3b-e262c61a5cd5', '2024-11-12 22:13:21.626', '2024-11-12 22:17:00.299', 'system', 0, 'system', NULL, 3, 't', 0, 'views/pages/iot/device/Setup.vue', 't', 'f', 'f', 'mdi-cog-transfer-outline', 'f', 'IotDeviceSetup', 't', 'b827346f-494d-4044-8bc9-0ef33f7e9d45', '/iot/device/setup', NULL, '设置属性', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('0ada5e0f-fc28-4bef-adf1-388629dc6aa7', '2024-11-12 22:14:54.955', '2024-11-12 22:27:53.897', 'system', 0, 'system', NULL, 0, 't', 0, 'views/pages/iot/device/Invoke.vue', 't', 'f', 'f', 'mdi-television-play', 'f', 'IotDeviceInvoke', 't', 'b827346f-494d-4044-8bc9-0ef33f7e9d45', '/iot/device/invoke', NULL, '调用服务', 'WEB', 'MENU', 'APP');
INSERT INTO "sys_element" ("element_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "component", "detail_content", "have_child", "hide_all_child", "icon", "ignore_auth", "name", "not_keep_alive", "parent_id", "path", "redirect", "title", "application_type", "element_category", "menu_scenario") VALUES ('b827346f-494d-4044-8bc9-0ef33f7e9d45', '2024-08-16 11:55:17.109', '2025-02-12 21:50:43.673', 'system', 0, 'system', NULL, 3, 't', 0, 'views/pages/iot/device/Index.vue', 'f', 't', 't', 'mdi-router-network', 'f', 'IotDevice', 't', '7f51cd60-3b17-4f0f-a46a-19028d0c2294', '/iot/device', NULL, '设备管理', 'WEB', 'MENU', 'APP');

-- ----------------------------
-- Table data for sys_element_role
-- ----------------------------
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('d9cd164d-2d4f-4532-9bdb-53bc824b65bf', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('81c90743-f49a-44ff-bed8-f05f7d8ccde7', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('22fd4a47-a12c-49a8-b041-3c5f4b6b788a', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('f52e614a-b466-4958-889a-e037495d925c', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('017ca0e8-1f30-4230-8602-12b6e95b502d', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('7f51cd60-3b17-4f0f-a46a-19028d0c2294', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('52be4bb7-9d4c-42fd-8ae7-9efc4ce1c029', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('3e88d6e3-b97d-48eb-b4d4-c2ac1cf444b8', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('b827346f-494d-4044-8bc9-0ef33f7e9d45', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('310a9900-400d-43cf-9e3b-e262c61a5cd5', '1');
INSERT INTO "sys_element_role" ("element_id", "role_id") VALUES ('0ada5e0f-fc28-4bef-adf1-388629dc6aa7', '1');

-- ----------------------------
-- Table data for iot_tsl_unit
-- ----------------------------
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('1', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '无', '无');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('2', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '升每分钟', 'L/min');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('3', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫克每千克', 'mg/kg');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('4', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '浊度', 'NTU');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('5', '2024-09-08 17:10:08', '2024-09-08 17:10:13', 'PH值', 'pH');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('6', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '土壤EC值', 'dS/m');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('7', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '太阳总辐射', 'W/㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('8', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '降雨量', 'mm/hour');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('9', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '乏', 'var');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('10', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '厘泊', 'cP');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('11', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '饱和度', 'aw');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('12', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微西每厘米', 'μS/cm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('13', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '牛顿每库仑', 'N/C');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('14', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '伏特每米', 'V/m');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('15', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '滴速', 'ml/min');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('16', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫米汞柱', 'mmHg');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('17', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '血糖', 'mmol/L');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('18', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫米每秒', 'mm/s');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('19', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '转每分钟', 'turn/m');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('20', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '次', 'count');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('21', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '档', 'gear');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('22', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '步', 'stepCount');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('23', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '标准立方米每小时', 'Nm³/h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('24', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千伏', 'kV');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('25', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千伏安', 'kVA');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('26', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千乏', 'kVar');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('27', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微瓦每平方厘米', 'uw/c㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('28', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '只', '只');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('29', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '相对湿度', '%RH');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('30', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方米每秒', 'm³/s');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('31', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '公斤每秒', 'kg/s');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('32', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '转每分钟', 'r/min');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('33', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '吨每小时', 't/h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('34', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千卡每小时', 'KCL/h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('35', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '升每秒', 'L/s');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('36', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '兆帕', 'Mpa');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('37', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方米每小时', 'm³/h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('38', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千乏时', 'kvarh');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('39', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微克每升', 'μg/L');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('40', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千卡路里', 'kcal');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('41', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '吉字节', 'GB');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('42', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '兆字节', 'MB');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('43', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千字节', 'KB');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('44', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '字节', 'B');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('45', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微克每平方分米每天', 'μg/(d㎡·d)');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('46', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '百万分率', 'ppm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('47', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '像素', 'pixel');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('48', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '照度', 'Lux');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('49', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '重力加速度', 'grav');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('50', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '分贝', 'dB');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('51', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '百分比', '%');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('52', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '流明', 'lm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('53', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '比特', 'bit');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('54', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '克每毫升', 'g/mL');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('55', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '克每升', 'g/L');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('56', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫克每升', 'mg/L');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('57', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微克每立方米', 'μg/m³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('58', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫克每立方米', 'mg/m³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('59', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '克每立方米', 'g/m³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('60', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千克每立方米', 'kg/m³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('61', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '纳法', 'nF');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('62', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '皮法', 'pF');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('63', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微法', 'μF');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('64', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '法拉', 'F');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('65', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '欧姆', 'Ω');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('66', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微安', 'μA');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('67', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千安', 'kA');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('68', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '安培', 'A');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('69', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫伏', 'mV');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('70', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '伏特', 'V');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('71', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫秒', 'ms');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('72', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '秒', 's');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('73', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '分钟', 'min');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('74', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '小时', 'h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('75', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '日', 'day');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('76', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '周', 'week');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('77', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '月', 'month');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('78', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '年', 'year');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('79', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '节', 'kn');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('80', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千米每小时', 'km/h');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('81', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '米每秒', 'm/s');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('82', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '秒', '″');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('83', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '分', '′');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('84', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '度', '°');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('85', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '弧度', 'rad');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('86', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '赫兹', 'Hz');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('87', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微瓦', 'μW');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('88', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫瓦', 'mW');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('89', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千瓦特', 'kW');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('90', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '瓦特', 'W');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('91', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '吨', 't');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('92', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '摄氏度', '℃');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('93', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '豪帕', 'mPa');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('94', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '百帕', 'hPa');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('95', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千帕', 'kPa');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('96', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '帕斯卡', 'Pa');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('97', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫克', 'mg');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('98', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '克', 'g');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('99', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千克', 'kg');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('100', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '牛', 'N');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('101', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫升', 'mL');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('102', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '升', 'L');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('103', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方毫米', 'mm³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('104', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方厘米', 'cm³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('105', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方千米', 'km³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('106', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '立方米', 'm³');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('107', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '公顷', 'h㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('108', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '平方厘米', 'c㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('109', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '平方毫米', 'm㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('110', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '平方千米', 'k㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('111', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '平方米', '㎡');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('112', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '纳米', 'nm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('113', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '微米', 'μm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('114', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '毫米', 'mm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('115', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '厘米', 'cm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('116', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '分米', 'dm');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('117', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '千米', 'km');
INSERT INTO "iot_tsl_unit" ("unit_id", "create_time", "update_time", "unit_name", "unit_symbol") VALUES ('118', '2024-09-08 17:10:08', '2024-09-08 17:10:13', '米', 'm');

-- ----------------------------
-- Table data for iot_product_category
-- ----------------------------
INSERT INTO "iot_product_category" ("category_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "category_name", "scene_id") VALUES ('2bfa5467-b9f5-46dd-acec-f05aaaa00522', '2025-04-24 16:16:11.724', '2025-04-24 16:17:04.647', 'system', 0, 'system', '物联网平台测试数据', 1, 't', 0, '测试品类', NULL);

-- ----------------------------
-- Table data for iot_product
-- ----------------------------
INSERT INTO "iot_product" ("product_id", "create_time", "update_time", "create_by", "reversion", "update_by", "description", "ranking", "is_reserved", "status", "authentication", "protocol", "networking", "node", "photo_url", "product_key", "product_name", "productsecret", "quantity", "registration", "is_release", "verification", "category_id") VALUES ('8452271e-e42e-4a59-af80-3ea3c83247ea', '2025-04-28 21:20:27.432', '2025-04-28 21:20:27.432', 'system', 0, 'system', NULL, 1, 't', 0, NULL, NULL, 2, 0, NULL, 'apktestadd', '测试物联网产品', '9f3026f4beddf8d29f3026f4beddf8d2', 0, 'f', 'f', 'f', NULL);

-- ----------------------------
-- Table data for sys_interface_authority
-- ----------------------------
INSERT INTO "sys_attribute_permission" ("attribute_id", "permission_id")
SELECT st.attribute_id, st.permission_id
FROM (SELECT '9' AS permission_id, sa.attribute_id FROM "sys_attribute" sa WHERE sa.url LIKE '%iot%') AS st;