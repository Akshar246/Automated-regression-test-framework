package com.akshar.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.*;
import java.util.List;

@SpringBootApplication
public class AutomatedTestFrameworkApplication {

    public static void main(String[] args) {
        disableBrokenProxySettings();
        SpringApplication.run(AutomatedTestFrameworkApplication.class, args);
    }

    private static void disableBrokenProxySettings() {
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
        System.clearProperty("proxyHost");
        System.clearProperty("proxyPort");
        System.clearProperty("socksProxyHost");
        System.clearProperty("socksProxyPort");
        System.clearProperty("java.net.useSystemProxies");

        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                return List.of(Proxy.NO_PROXY);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            }
        });
    }
}