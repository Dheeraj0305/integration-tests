/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
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

import java.io.FileNotFoundException;
import java.util.Properties;
import org.ini4j.Profile;
import org.openbaton.catalogue.mano.descriptor.NetworkServiceDescriptor;
import org.openbaton.integration.test.utils.Tester;
import org.openbaton.sdk.api.exception.SDKException;

/**
 * Created by mob on 29.07.15.
 *
 * <p>Tester used to delete a NetworkServiceDescriptor.
 */
public class NetworkServiceDescriptorDelete extends Tester<NetworkServiceDescriptor> {
  /** @param p : IntegrationTest properties containing: nfvo-usr nfvo-pwd nfvo-ip nfvo-port */
  public NetworkServiceDescriptorDelete(Properties p) throws FileNotFoundException {
    super(p, NetworkServiceDescriptor.class);
  }

  @Override
  protected NetworkServiceDescriptor prepareObject() {
    return null;
  }

  @Override
  protected Object doWork() throws SDKException, FileNotFoundException {
    this.setAbstractRestAgent(requestor.getNetworkServiceDescriptorAgent());
    NetworkServiceDescriptor nsd = (NetworkServiceDescriptor) param;
    log.info("Delete NSD " + nsd.getName());
    try {
      delete(nsd.getId());
    } catch (SDKException sdkEx) {
      log.error(
          "Exception during deletion of NetworkServiceDescription with id: " + nsd.getId(), sdkEx);
      throw sdkEx;
    }
    log.debug(" --- NetworkServiceDescriptorDelete has deleted the nsd:" + nsd.getId());
    return nsd;
  }

  @Override
  public void configureSubTask(Profile.Section currentSection) {}
}
