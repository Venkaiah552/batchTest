package com.cvs.cdc.service;

import com.cvs.cdc.exception.BadRequestException;
import com.cvs.cdc.exception.CdcRuntimeException;
import com.cvs.cdc.model.TokenRequest;
import com.cvs.cdc.model.TokenResponse;
import com.cvs.cdc.utils.Constants;
import com.cvs.cdc.model.OperationType;
import com.cvs.cdc.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Qualifier("restTemplateExternal")
    private final RestTemplate restTemplate;

    @Value("${cdc.token.url}")
    private String tokenUrl;

    @Value("${cdc.token.refresh.url}")
    private String tokenRefreshUrl;

    @Override
    public TokenResponse getTokenResponse(OperationType operationType) {

        TokenResponse tokenResponse = null;
        try {
            URI cdcTokenUri = Utils.createURI(tokenUrl);
            HttpHeaders httpHeaders = Utils.generateHeaders();
            List<String> scopeArrayList = new ArrayList<>(Arrays.asList(Constants.SCOPE));
            TokenRequest tokenRequest = TokenRequest.builder()
                    .clientID(operationType.name().equalsIgnoreCase(OperationType.VALIDATION.name()) ? Constants.CLIENT_ID_VALIDATE : Constants.CLIENT_ID_UPLOAD)
                    .clientSecret(Constants.CLIENT_SECRET)
                    .scopes(scopeArrayList).build();
            HttpEntity<TokenRequest> tokenRequestHttpEntity = new HttpEntity<>(tokenRequest, httpHeaders);
            log.debug("request for cdc token url = {} , request = {}", cdcTokenUri, tokenRequestHttpEntity);
            tokenResponse = restTemplate.postForEntity(cdcTokenUri, tokenRequestHttpEntity, TokenResponse.class).getBody();
            log.debug(" Response from CDC is " + tokenResponse);
        } catch (HttpStatusCodeException e) {
            log.error("HttpStatusCodeException occuring while connection to CDC", e.getResponseBodyAsString());
            if (e.getRawStatusCode() == HttpStatus.GATEWAY_TIMEOUT.value() || e.getRawStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                log.error("HttpStatusCodeException occuring while connection to CDC", e.getResponseBodyAsString());
                //throw e;
            }
        } catch (ResourceAccessException e) {
            String errorMessage = String.format("Connectivity exception with CDC token service, %s", e.getMessage());
            log.error(errorMessage);
            throw new CdcRuntimeException(errorMessage, e);
        } catch (BadRequestException e) {
            String errorMessage = String.format("Connectivity exception with CDC token service, %s", e.getMessage());
            log.error(errorMessage);
        }
        return tokenResponse;
    }
}
