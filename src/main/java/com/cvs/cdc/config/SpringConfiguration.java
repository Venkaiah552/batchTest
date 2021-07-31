package com.cvs.cdc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class SpringConfiguration {

    private static final String KEYSTORE_NAME="javax.net.ssl.keyStore";
    private static final String KEYSTORE_PASSWORD="javax.net.ssl.keyStorePassword";

    @Value("${resttemplate.connection.request.timeout}")
    private String connectionRequestTimeout;

    @Value("${resttemplate.connection.timeout}")
    private String connectionTimeout;

    @Value("${resttemplate.read.timeout}")
    private String readTimeout;

    @Value("${resttemplate.max.connections}")
    private String maxConnections;

    @Value("${resttemplate.max.connections.route}")
    private String maxPerRoute;

    @Value("${keystore.name}")
    private String keystoreName;

    @Value("${keystore.password}")
    private String keyStorePassword;

    @Value("${keystorePath}")
    private String keystorePath;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    @Qualifier("restTemplateExternal")
    public RestTemplate restTemplateExternal() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        /*InputStream keystoreInputStream;
        InputStream keystoreInputStream2;
        HttpClient httpClient = null;
        // Logging to debug the keystore related issues on DEV and other environments
        log.info("load from classpath={}, keystorePath={}",  keystorePath);
        try {
           ClassLoader classLoader = getClass().getClassLoader();


            Resource resource = resourceLoader.getResource("classpath:dch_x509_key.pfx");

            keystoreInputStream = resource.getInputStream();
            keystoreInputStream2 = resource.getInputStream();
            char[] keystorePasswordArr = keyStorePassword.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("pkcs12", "SunJSSE");
            keyStore.load(keystoreInputStream, keystorePasswordArr);

            KeyStore trustStore = KeyStore.getInstance("pkcs12", "SunJSSE");
            trustStore.load(keystoreInputStream2, keystorePasswordArr);

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).loadKeyMaterial(keyStore, keystorePasswordArr).build(),
                    NoopHostnameVerifier.INSTANCE);
            httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        }
        catch(KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException | UnrecoverableKeyException | NoSuchProviderException e) {
            log.error("Error configuring rest client. {}", e.getMessage());
        }
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));*/

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();;
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(Integer.parseInt(connectionRequestTimeout));
        httpComponentsClientHttpRequestFactory.setConnectTimeout(Integer.parseInt(connectionTimeout));
        httpComponentsClientHttpRequestFactory.setReadTimeout(Integer.parseInt(readTimeout));

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(keystoreName).getFile());
        //File file = new File(System.getProperty(KEYSTORE_NAME));
        keyStore.load(new FileInputStream(file), System.getProperty(KEYSTORE_PASSWORD).toCharArray());
        TrustStrategy  acceptingTrustStrategy = (x509Certificates, s) -> true;

        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(acceptingTrustStrategy).loadKeyMaterial(keyStore,keyStorePassword.toCharArray()).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);

        RequestConfig config = RequestConfig.custom().setSocketTimeout(Integer.parseInt(readTimeout))
                                            .setConnectTimeout(Integer.parseInt(connectionTimeout))
                                            .setConnectionRequestTimeout(Integer.parseInt(connectionRequestTimeout)).build();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("https",sslConnectionSocketFactory)
                                                   .register("http",new PlainConnectionSocketFactory()).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setMaxTotal(Integer.parseInt(maxConnections));
        connManager.setDefaultMaxPerRoute(Integer.parseInt(maxPerRoute));

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).setSSLSocketFactory(sslConnectionSocketFactory)
                .setConnectionManager(connManager).build();
        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);

    }


}
