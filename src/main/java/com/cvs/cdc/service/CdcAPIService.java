package com.cvs.cdc.service;

import com.cvs.cdc.exception.ThirdPartyRestServiceException;
import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseFromApi;
import com.cvs.cdc.model.TokenResponse;
import com.cvs.cdc.model.OperationType;
import com.cvs.cdc.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CdcAPIService {

    private static final String FIELD_ERROR_MSG = "errorMsg";

    private final TokenService tokenService;

    @Qualifier("restTemplateExternal")
    private final RestTemplate restTemplate;

    @Value("${cdc.validate.upload.url}")
    private String validateUploadUrl;

    @Value("${cdc.base-url}")
    private String baseUrl;

    /**
     *
     * Method to gettoken and validation & upload data
     * @param operationType
     * @param cdcRequestToApiList
     * @return CdcResponseFromApi
     */
    public CdcResponseFromApi postUpdateValidateData(OperationType operationType, List<CdcRequestToApi> cdcRequestToApiList) {
        ResponseEntity<CdcResponseFromApi> response = null;
        TokenResponse tokenResponse = tokenService.getTokenResponse(operationType);
        HttpHeaders httpHeaders = Utils.generateHeaders();
        httpHeaders.set("Authorization", tokenResponse.getToken());
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        CdcResponseFromApi cdcResponseFromApi = null;
        HttpEntity<List<CdcRequestToApi>> entity = new HttpEntity<>(cdcRequestToApiList, httpHeaders);
        String url = baseUrl + validateUploadUrl;
        log.debug("request for cdc validate upload url = {} , request = {}", url, cdcRequestToApiList);
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, CdcResponseFromApi.class);
            cdcResponseFromApi = response.getBody();
            log.debug("Validate upload url Api Response {}", cdcResponseFromApi);
        } catch (HttpClientErrorException e) {
            log.error("client side error calling cdc validate upload url webservice: {} {}", e.getRawStatusCode(), e.getStatusText());
            log.error("response body: {}", e.getResponseBodyAsString());
            log.error("exception:", e);
            return cdcResponseFromApi;
        } catch (HttpServerErrorException e) {
            log.error("server side error calling cdc validate upload url webservice: {} {}", e.getRawStatusCode(), e.getStatusText());
            log.error("response body: {}", e.getResponseBodyAsString());
            log.error("exception:", e);
            throw new ThirdPartyRestServiceException("server side error calling cdc validate upload url webservice", e);
        } catch (Exception e) {
            log.error("error calling cdc validate upload url webservice", e);
            throw new ThirdPartyRestServiceException("error calling cdc validate upload url webservice", e);
        }
        if (HttpStatus.OK != response.getStatusCode()) {
            log.error("non-200 status from cdc validate upload url webservice is: {}", response.getStatusCodeValue());
            throw new ThirdPartyRestServiceException(
                    "non-200 status from cdc validate upload url webservice: status = " + response.getStatusCode());
        }
        log.debug("response from cdc validate upload url {}", response);
        return cdcResponseFromApi;
    }

}


