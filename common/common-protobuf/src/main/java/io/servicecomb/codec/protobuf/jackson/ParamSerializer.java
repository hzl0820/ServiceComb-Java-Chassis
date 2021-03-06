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

package io.servicecomb.codec.protobuf.jackson;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufGenerator;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufField;

public class ParamSerializer extends JsonSerializer<Object> {
  @Override
  public void serialize(Object value, JsonGenerator gen,
      SerializerProvider serializers) throws IOException, JsonProcessingException {
    gen.writeStartObject();

    ProtobufGenerator protobufGenerator = (ProtobufGenerator) gen;
    Iterator<ProtobufField> iter = protobufGenerator.getSchema().getRootType().fields().iterator();
    Object[] values = (Object[]) value;
    for (Object value1 : values) {
      gen.writeObjectField(iter.next().name, value1);
    }

    gen.writeEndObject();
  }
}
