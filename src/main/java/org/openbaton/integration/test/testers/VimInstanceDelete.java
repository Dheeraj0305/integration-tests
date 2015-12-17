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

import org.openbaton.catalogue.nfvo.VimInstance;
import org.openbaton.integration.test.utils.Tester;
import org.openbaton.sdk.api.exception.SDKException;

import java.util.Properties;

/**
 * Created by mob on 04.08.15.
 */
public class VimInstanceDelete extends Tester<VimInstance> {

    /**
     * @param properties : IntegrationTest properties containing:
     *                   nfvo-usr
     *                   nfvo-pwd
     *                   nfvo-ip
     *                   nfvo-port
     * @param aClass     : example VimInstance.class
     * @param filePath   : example "/etc/json_file/vim_instances/vim-instance.json"
     * @param basePath
     */
    public VimInstanceDelete(Properties properties) {
        super(properties, VimInstance.class, "", "/datacenters");
    }

    @Override
    protected VimInstance prepareObject() {
        return null;
    }

    @Override
    protected Object doWork() throws SDKException {
        VimInstance vi = (VimInstance) param;
        try {
            delete(vi.getId());
        } catch (SDKException sdkEx) {
            log.error("Exception during deleting of VimInstance with id: "+vi.getId(), sdkEx);
            throw sdkEx;
        }
        //log.debug(" --- VimInstanceDelete has deleted the vimInstance:"+vi.getId());
        return null;
    }
}
