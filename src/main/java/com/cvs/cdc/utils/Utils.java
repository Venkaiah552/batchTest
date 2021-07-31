package com.cvs.cdc.utils;

import com.cvs.cdc.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class Utils {

    private Utils(){
        throw new IllegalStateException("This is Utility class and can't be instantiated");
    }

    public static URI createURI(String serviceUrl) throws BadRequestException {
        URI cdcUri;
        try {
            cdcUri = new URI(serviceUrl);
        } catch (URISyntaxException exception) {
            String errorMessage = String.format("%s - %s", "URISyntaxExceptipon occured: ", exception.getMessage());
            log.error(errorMessage);
            throw new BadRequestException(errorMessage,exception);
        }
        return  cdcUri;
    }

    public static HttpHeaders generateHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return  httpHeaders;
    }
}
