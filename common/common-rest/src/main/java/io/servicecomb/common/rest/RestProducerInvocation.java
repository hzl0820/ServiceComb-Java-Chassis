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

package io.servicecomb.common.rest;

import java.util.List;

import io.servicecomb.common.rest.filter.HttpServerFilter;
import io.servicecomb.common.rest.locator.OperationLocator;
import io.servicecomb.common.rest.locator.ServicePathManager;
import io.servicecomb.core.Const;
import io.servicecomb.core.CseContext;
import io.servicecomb.core.Transport;
import io.servicecomb.core.definition.MicroserviceMeta;
import io.servicecomb.core.invocation.InvocationFactory;
import io.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import io.servicecomb.foundation.vertx.http.HttpServletResponseEx;
import io.servicecomb.serviceregistry.RegistryUtils;
import io.servicecomb.swagger.invocation.exception.InvocationException;

public class RestProducerInvocation extends AbstractRestInvocation {
  protected Transport transport;

  public void invoke(Transport transport, HttpServletRequestEx requestEx, HttpServletResponseEx responseEx,
      List<HttpServerFilter> httpServerFilters) {
    this.transport = transport;
    this.requestEx = requestEx;
    this.responseEx = responseEx;
    this.httpServerFilters = httpServerFilters;
    requestEx.setAttribute(RestConst.REST_REQUEST, requestEx);

    try {
      findRestOperation();
    } catch (InvocationException e) {
      sendFailResponse(e);
      return;
    }

    scheduleInvocation();
  }

  protected void findRestOperation() {
    String targetMicroserviceName = requestEx.getHeader(Const.TARGET_MICROSERVICE);
    if (targetMicroserviceName == null) {
      // for compatible
      targetMicroserviceName = RegistryUtils.getMicroservice().getServiceName();
    }
    MicroserviceMeta selfMicroserviceMeta =
        CseContext.getInstance().getMicroserviceMetaManager().ensureFindValue(targetMicroserviceName);
    findRestOperation(selfMicroserviceMeta);
  }

  @Override
  protected OperationLocator locateOperation(ServicePathManager servicePathManager) {
    return servicePathManager.producerLocateOperation(requestEx.getRequestURI(), requestEx.getMethod());
  }

  @Override
  protected void createInvocation(Object[] args) {
    this.invocation = InvocationFactory.forProvider(transport.getEndpoint(),
        restOperationMeta.getOperationMeta(),
        args);
  }
}
