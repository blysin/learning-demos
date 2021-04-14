package com.blysin;

import org.junit.jupiter.api.Test;

/**
 * @author daishaokun
 * @date 2021/1/25
 */
public class PulsarAdminTest {
    @Test
    public void initAdmin() {
        String url = "http://localhost:8080";
        // Pass auth-plugin class fully-qualified name if Pulsar-security enabled
        String authPluginClassName = "com.org.MyAuthPluginClass";
        // Pass auth-param if auth-plugin class requires it
        String authParams = "param1=value1";
        boolean useTls = false;
        boolean tlsAllowInsecureConnection = false;
        String tlsTrustCertsFilePath = null;
    }
}
