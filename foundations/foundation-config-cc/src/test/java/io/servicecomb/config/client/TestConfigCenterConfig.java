/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 */

package io.servicecomb.config.client;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.servicecomb.config.ConfigUtil;

public class TestConfigCenterConfig {
  @BeforeClass
  public static void setUpClass() {
    ConfigCenterConfig.setConcurrentCompositeConfiguration(ConfigUtil.createLocalConfig());
  }

  @Test
  public void getServerUri() {
    List<String> servers = ConfigCenterConfig.INSTANCE.getServerUri();
    Assert.assertEquals("https://172.16.8.7:30103", servers.get(0));
    Assert.assertEquals("https://172.16.8.7:30103", servers.get(1));
  }
}
