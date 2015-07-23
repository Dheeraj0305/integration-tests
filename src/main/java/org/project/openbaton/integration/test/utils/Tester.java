package org.project.openbaton.integration.test.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.project.openbaton.sdk.NFVORequestor;
import org.project.openbaton.sdk.api.exception.SDKException;
import org.project.openbaton.sdk.api.util.AbstractRestAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by lto on 15/07/15.
 */
public class Tester<T extends Serializable> {
    private static String FILE_NAME;
    protected static final Logger log = LoggerFactory.getLogger(Tester.class);
    private final Class<T> aClass;
    private final AbstractRestAgent abstractRestAgent;
    protected NFVORequestor requestor ;
    protected Gson mapper;

    /**
     *
     * @param properties: IntegrationTest properties containing:
     *                  nfvo-usr
     *                  nfvo-pwd
     *                  nfvo-ip
     *                  nfvo-port
     * @param aClass: example VimInstance.class
     * @param filePath: example "/etc/json_file/vim_instances/vim-instance.json"
     */
    public Tester(Properties properties, Class<T> aClass, String filePath, String basePath) {
        this.FILE_NAME = filePath;
        GsonBuilder builder = new GsonBuilder();
        mapper = builder.create();
        log.debug("using properties: " + properties.getProperty("nfvo-usr") + properties.getProperty("nfvo-pwd") + properties.getProperty("nfvo-ip") + properties.getProperty("nfvo-port") + "1");
        requestor = new NFVORequestor(properties.getProperty("nfvo-usr"),properties.getProperty("nfvo-pwd"),properties.getProperty("nfvo-ip"),properties.getProperty("nfvo-port"),"1");
        this.aClass = aClass;
        abstractRestAgent = requestor.abstractRestAgent(aClass, basePath);
    }

    public T create() throws SDKException {
        String body = Utils.getStringFromInputStream(Tester.class.getResourceAsStream(FILE_NAME));

        T expected = mapper.fromJson(body, aClass);

        T obtained;
        obtained = (T) abstractRestAgent.create(expected);


        log.trace("Received: " + obtained.toString());

        return obtained;
    }

    public void delete(String id) throws SDKException {
        abstractRestAgent.delete(id);
    }
}