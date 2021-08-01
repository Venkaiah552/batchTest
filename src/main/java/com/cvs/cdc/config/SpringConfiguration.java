package com.cvs.cdc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


@Configuration
@Slf4j
public class SpringConfiguration {

    private static final String KEYSTORE_NAME = "javax.net.ssl.keyStore";
    private static final String KEYSTORE_PASSWORD = "javax.net.ssl.keyStorePassword";

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
    private Resource keystoreName;

    @Value("${keystore.password}")
    private String keyStorePassword;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    @Qualifier("restTemplateExternal")
    public RestTemplate restTemplateExternal(RestTemplateBuilder builder) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(resourceLoader.getResource("classpath:"+keystoreName).getURL(), keyStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();

        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }


}
