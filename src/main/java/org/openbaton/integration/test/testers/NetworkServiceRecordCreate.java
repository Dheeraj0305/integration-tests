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

import org.openbaton.catalogue.mano.descriptor.NetworkServiceDescriptor;
import org.openbaton.catalogue.mano.record.NetworkServiceRecord;
import org.openbaton.integration.test.utils.Tester;
import org.openbaton.sdk.api.exception.SDKException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class NetworkServiceRecordCreate extends Tester<NetworkServiceRecord> {

	private static Logger log = LoggerFactory.getLogger(NetworkServiceRecordCreate.class);

	/**
	 * @param properties : IntegrationTest properties containing:
	 *                   nfvo-usr
	 *                   nfvo-pwd
	 *                   nfvo-ip
	 *                   nfvo-port
	 */
	public NetworkServiceRecordCreate(Properties properties) {
		super(properties, NetworkServiceRecord.class, "", "/ns-records");
	}

	@Override
	protected Object doWork() throws SDKException {
		return create();
	}


	@Override
	public NetworkServiceRecord create() throws SDKException {

		NetworkServiceDescriptor nsd = (NetworkServiceDescriptor) this.param;
        log.info("Launch NSR from NSD "+nsd.getName()+" with id "+nsd.getId());
		NetworkServiceRecord networkServiceRecord = null;
		try {
			networkServiceRecord = this.requestor.getNetworkServiceRecordAgent().create(nsd.getId());
		} catch (SDKException sdkEx) {
			log.error("Exception during the instantiation of NetworkServiceRecord from nsd of id: "+nsd.getId());
			throw sdkEx;
		}
		log.debug(" --- Creating nsr with id: " + networkServiceRecord.getId()+" from nsd with id: "+ nsd.getId());

		return networkServiceRecord;
	}

	@Override
	protected NetworkServiceRecord prepareObject() {
		return null;
	}
}