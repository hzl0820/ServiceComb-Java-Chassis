/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.foundation.vertx.part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class FilePart extends AbstractPart {
  private File file;

  public FilePart(String name, String file) {
    this(name, new File(file));
  }

  public FilePart(String name, File file) {
    this.name = name;
    this.file = file;
    this.submittedFileName = this.file.getName();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new FileInputStream(file);
  }

  @Override
  public long getSize() {
    return file.length();
  }

  @Override
  public void write(String fileName) throws IOException {
    FileUtils.copyFile(file, new File(fileName));
  }
}
