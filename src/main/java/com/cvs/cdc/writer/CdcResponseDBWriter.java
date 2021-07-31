package com.cvs.cdc.writer;

import com.cvs.cdc.exception.BadRequestException;
import com.cvs.cdc.exception.CdcRuntimeException;
import com.cvs.cdc.model.CdcRequestToApi;
//import com.cvs.cdc.model.CdcResponseToDb;
//import com.cvs.cdc.repo.CdcRespRepo;
import com.cvs.cdc.model.CdcResponseToDb;
import com.cvs.cdc.model.TokenRequest;
import com.cvs.cdc.model.TokenResponse;
import com.cvs.cdc.repo.CdcRespRepo;
import com.cvs.cdc.utils.Constants;
import com.cvs.cdc.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("cdcdbresponsewriter")
//@Transactional
@Slf4j
public class CdcResponseDBWriter implements ItemWriter<CdcRequestToApi> {
    @Autowired
    private CdcRespRepo cdcRespRepo;

    @Value("${cdc.token.url}")
    private String tokenUrl;

    @Value("${cdc.token.refresh.url}")
    private String tokenRefreshUrl;

    @Value("${cdc.validate.upload.url}")
    private String validateUploadUrl;

    @Autowired
    @Qualifier("restTemplateExternal")
    private RestTemplate restTemplate;



   /* @Override
    public void write(List<? extends CdcResponseFromApi> cdcResponseFromApis) throws Exception {
        List<CdcResponseToDb> cdcResponseToDbs = cdcResponseFromApis.stream().map(cdcResponseFromApi -> {

            String vaxEventId = cdcResponseFromApi.getCdcRequestToApi().getVaxEventId();

            return CdcResponseToDb.builder().cdcId(1).cdcStatusMessage(cdcResponseFromApi.getStorageResult().getREDACTED_DB().getStatus()).validationErrors("")
                                  .createAt("createdAt").createdBy("srini").status("1").processingErrors("").udpatedBy("srini").updatedAt("updatedAt").
            cdcRequestToApi(new CdcRequestToApi(vaxEventId)).build();
        }).collect(Collectors.toList());
       // CdcResponseToDb cdcResponseToDb = cdcResponseToDbs.get(0);
        cdcRespRepo.saveAll(cdcResponseToDbs);
        System.out.println("{} employees saved in database " + cdcResponseToDbs.size());
    }*/

    @Override
    //@Transactional
    public void write(List<? extends CdcRequestToApi> listCdcResponseToDb) throws Exception {

        TokenResponse tokenResponseForValidate = getTokenResponseForValidate();
        //getCDCRespone(cdcRequestToApi);


        //cdcRespRepo.saveAll(listCdcResponseToDb);
       /* CdcResponseToDb cdcResponseToDb = listCdcResponseToDb.stream().findFirst().get();
        //cdcResponseToDb.setCdcResponseKey(CompositeKey.builder().extrDt("2000-09-20").jobNm("3").rxcImmId("3").vaxEventId("test").build());
        cdcResponseToDb.setExtrDt("2000-09-20")*/
        /*CdcResponseToDb cdcResponseToDb = CdcResponseToDb.builder().valStatusMsg("test").updtTs("2023-01-09").statusCd("stat").rxcImmId("3").resultCd("tets").jobNm("3")
                                               .insrtTs("2020-01-04").extrDt("2020-03-04").activityTypCd("test").vaxEventId("test13").uploadStatusMsg("test").build();*/
        //cdcRespRepo.save(listCdcResponseToDb.stream().findFirst().get());
        listCdcResponseToDb.stream().forEach(x -> System.out.println("Record::: extract dt::" + x.getExtrDt() + " vaxEventId::" +x.getVaxEventId() +
                " jobnm::"+x.getJobNm()));
        List<? extends CdcRequestToApi> list1CdcResponseToDb = listCdcResponseToDb.stream().filter(x -> !(x.getVaxEventId().equalsIgnoreCase("vax_event_id"))).collect(Collectors.toList());
        System.out.println("count of records getting updated to db::: "+list1CdcResponseToDb.size()+"\n");
        list1CdcResponseToDb.stream().forEach(x -> System.out.println("Record::: extract dt::" + x.getExtrDt() + " vaxEventId::" +x.getVaxEventId() +
                " jobnm::"+x.getJobNm()));

       // TokenResponse tokenResponse = getTokenResponseForValidate();
       /* List<CdcRequestToApi> s = cdcRespRepo.saveAll(list1CdcResponseToDb);*/
    }

   /* private void callCdcForValidate() {

        URI cdcUri = Utils.createURI()
    }
*/

    private TokenResponse getTokenResponseForValidate() {

        TokenResponse tokenResponse = null;
        try {
            URI cdcTokenUri = Utils.createURI(tokenUrl);
            HttpHeaders httpHeaders = Utils.generateHeaders();
            ArrayList scopeArrayList = new ArrayList<String>();
            scopeArrayList.add("Upload");
            TokenRequest tokenRequest = TokenRequest.builder().clientID(Constants.CLIENT_ID).clientSecret(Constants.CLIENT_SECRET).scopes(scopeArrayList).build();
            HttpEntity<TokenRequest> tokenRequestHttpEntity = new HttpEntity<>(tokenRequest, httpHeaders);
            tokenResponse = restTemplate.postForEntity(cdcTokenUri, tokenRequestHttpEntity, TokenResponse.class).getBody();
            log.debug(" Response from CDC is "+tokenResponse);
        } catch (HttpStatusCodeException e) {
           log.error("HttpStatusCodeException occuring while connection to CDC", e.getResponseBodyAsString());
           if(e.getRawStatusCode()== HttpStatus.GATEWAY_TIMEOUT.value() || e.getRawStatusCode() == HttpStatus.SERVICE_UNAVAILABLE.value()){
               log.error("HttpStatusCodeException occuring while connection to CDC", e.getResponseBodyAsString());
               //throw e;
           }
        } catch ( ResourceAccessException e){
            String errorMessage = String.format("Connectivity exception with CDC token service, %s", e.getMessage());
            log.error(errorMessage);
            throw new CdcRuntimeException(errorMessage,e);
        } catch (BadRequestException e) {
            String errorMessage = String.format("Connectivity exception with CDC token service, %s", e.getMessage());
            log.error(errorMessage);
        }

        return tokenResponse;

    }

   /* private TokenResponse getValidateToken() {
        List<String> validateList = new ArrayList<>();
        validateList.add("VALIDATE");
        TokenRequest tokenRequest = TokenRequest.builder() .clientID("CV1-VALIDATE")
                                                .clientSecret("wRfMQxrk4EuJtbtSMaK0YQ%3d%3d")
                                                .scopes(validateList)
                                                .build();

    }*/
   /*
   * TODO :
   *  1. call token for validate funcationality
   * 2. with token call the validate api
   * 3. write the validation errors to db
   * 4. exclude those validation error from the actual payload for the uplaod
   * 5.write the upload errors to db.
   * 6. send an email to stakeholders once the job is completed ( as an at 1pm 30 files are there , after completion of 30 files, just read the data from table and send the email.
   * 7. for 1/2/3/5 - we need to retry 5 times with 5 sec pause in beteen the calls when the end point is down or 5XX erros. we have to send the email to stakeholders.
    */

    private String getCDCResponeforValidate(CdcRequestToApi cdcRequestToApi) {

        return "{\"id\":\"d8a322dd-fc95-4a8c-972d-01d38d4a7839\",\"validationErrors\":[\"line#8: vaxEventId=4821914116077 recipId=48219141160770200 field=recip_dob message=invalid date format, expected: \\\"YYYY-MM-DD\\\" or \\\"YYYY-MM\\\"\",\"line#79: vaxEventId=48267-R73677414 recipId=48267-R73677414-0550 field=admin_date message=invalid date format, expected: \\\"YYYY-MM-DD\\\" or \\\"YYYY-MM\\\"\",\"line#101: vaxEventId=48279661062245 recipId=482796610622457089 field=admin_date message=admin_date must be on or before submission date\",\"line#103: vaxEventId=48279661062246 recipId=482796610622466335 field=recip_dob message=recip_dob must be on or before admin_date\",\"line#147: vaxEventId=48294R55623784 recipId=48294R556237848921 field=recip_address_zip message=must be a valid ZIP. 5 digit or 5 digit + 4 are acceptable\",\"line#149: vaxEventId=4830668202326 recipId=48306682023261667 field=admin_date message=admin_date must be on or before submission date\",\"line#165: vaxEventId=483296400314 recipId=4832964003146794 field=admin_date message=admin_date must be on or before submission date\"],\"processingErrors\":[\"storage=NON_REDACTED_DB vaxEventID=48232819120296 recipId=482328191202960000 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677381 recipId=48267-R73677381 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677381 recipId=48267-R73677381 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=48333451000270373 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100052 recipId=4833345100052 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100052 recipId=4833345100052 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100055 recipId=48333451000556958 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4853055596583 recipId=4853055596583 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\"],\"truncated\":true,\"storageResult\":{\"NON_REDACTED_DB\":{\"status\":\"SUCCESS\"},\"REDACTED_DB\":{\"status\":\"SUCCESS\"}}}";
    }

}
