/*
 * Copyright (c) 2015 Fraunhofer FOKUS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openbaton.integration.test.testers;

import org.openbaton.catalogue.mano.record.NetworkServiceRecord;
import org.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.openbaton.integration.test.utils.Tester;
import org.openbaton.integration.test.utils.Utils;
import org.openbaton.sdk.api.exception.SDKException;
import org.openbaton.sdk.api.rest.NetworkServiceRecordRestAgent;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by tbr on 15.01.16.
 */

/**
 * This class triggers one scale in on a specified VNFR.
 */
public class ScaleIn extends Tester {

  private String vnfrType = "";

  public ScaleIn(Properties properties) {
    super(properties, ScaleIn.class, "", "");
  }

  @Override
  protected Serializable prepareObject() {
    return null;
  }

  @Override
  protected Object doWork() throws Exception {
    log.info("Start ScaleIn on VNFR type " + vnfrType);
    NetworkServiceRecord nsr = (NetworkServiceRecord) getParam();

    Properties p = Utils.getProperties();
    NetworkServiceRecordRestAgent agent = requestor.getNetworkServiceRecordAgent();

    boolean found = false;
    for (VirtualNetworkFunctionRecord vnfr : nsr.getVnfr()) {
      if (vnfr.getType().equals(vnfrType)) {
        found = true;
        try {
          agent.deleteVNFCInstance(nsr.getId(), vnfr.getId());
        } catch (SDKException e) {
          log.warn("Exception while triggering the scale in: " + e.getMessage());
        }
      }
    }

    if (!found) log.warn("did not find a VNFR of type " + vnfrType);

    log.debug("--- Triggered ScaleIn on VNFR of type " + vnfrType);
    return param;
  }

  public void setVnfrType(String vnfrType) {
    this.vnfrType = vnfrType;
  }
}
