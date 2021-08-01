package com.cvs.cdc.utils;

import com.cvs.cdc.exception.BadRequestException;
import com.cvs.cdc.model.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public static boolean isNullOrEmpty( final Collection< ? > c ) {
        return c == null || c.isEmpty();
    }

    public static boolean isNullOrEmpty( final Map< ?, ? > m ) {
        return m == null || m.isEmpty();
    }

    public static boolean isOperationValidation(OperationType operationType) {
        return operationType.name().equals(OperationType.VALIDATION);
    }

    public static boolean isOperationUpload(OperationType operationType) {
        return operationType.name().equals(OperationType.UPLOAD);
    }

}
