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
import org.openbaton.catalogue.nfvo.EndpointType;
import org.openbaton.catalogue.nfvo.EventEndpoint;
import org.openbaton.integration.test.exceptions.SubscriptionException;
import org.openbaton.integration.test.interfaces.Waiter;
import org.openbaton.sdk.api.exception.SDKException;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by mob on 02.10.15.
 */
public class VirtualNetworkFunctionRecordWait extends Waiter {

    private String name = VirtualNetworkFunctionRecordWait.class.getSimpleName();
    private String vnfrName;

    public VirtualNetworkFunctionRecordWait(Properties properties) {
        super(properties, VirtualNetworkFunctionRecordWait.class, "", "");
    }

    @Override
    protected Serializable prepareObject() {
        return null;
    }

    @Override
    protected Object doWork() throws SDKException, SubscriptionException, InterruptedException {

        NetworkServiceRecord nsr = (NetworkServiceRecord) getParam();
        EventEndpoint eventEndpoint = createEventEndpoint(name, EndpointType.REST);
        String vnfrId=getVnfrIdFromNsr(nsr, getVnfrName());
        eventEndpoint.setVirtualNetworkFunctionId(vnfrId);
        //The eventEndpoint param of EventEndpoint will be set in the RestWaiter or JMSWaiter

        try {
            subscribe(eventEndpoint);
            log.debug(name + ": --- registration complete, start waiting for " + getAction().toString() + " of vnfr with name:" + getVnfrName() + " and id:" + vnfrId + "....");
            if(waitForEvent())
                log.debug(name + ": --- waiting complete for " + getAction().toString() + " of vnfr with name:"+getVnfrName()+" and id:" + vnfrId);
            else log.debug(name + ": --- timeout elapsed for " + getAction().toString() + " of vnfr with name:"+getVnfrName()+" and id:" + vnfrId);
            unSubscribe();

        } catch (SubscriptionException e) {
            log.error("Subscription failed for event " + getAction() + " of vnfr with name:"+getVnfrName()+" and id:" + vnfrId);
            throw e;
        } catch (InterruptedException | SDKException e) {
            log.error("Wait failed for event " + getAction() + " of vnfr with name:"+getVnfrName()+" and id:" + vnfrId);
            throw e;
        }
        //I can choose what to return:
        //-nsr
        //-vnfr received and available invoking getPayload()
        return nsr;
    }

    public void setVnfrName(String name){
        vnfrName=name;
    }

    public String getVnfrName(){
        return vnfrName;
    }

    private String getVnfrIdFromNsr(NetworkServiceRecord networkServiceRecord, String vnfrName){
        if(networkServiceRecord==null)
            throw new NullPointerException("NetworkServiceRecord is null");
        if(vnfrName==null || vnfrName.isEmpty())
            throw new NullPointerException("vnfrName is null or empty");
        log.debug(""+networkServiceRecord);
        for(VirtualNetworkFunctionRecord vnfr : networkServiceRecord.getVnfr()) {
            log.debug("VNFR name found: " + vnfr.getName());
            if (vnfr.getName().equalsIgnoreCase(vnfrName))
                return vnfr.getId();
        }
        throw new NullPointerException("No vnfr found for name: "+vnfrName);
    }
}
