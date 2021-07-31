package com.cvs.cdc.processor;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseToDb;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Qualifier("cdcresponseeprocessor")
public class CdcResponseProcessorApi implements ItemProcessor<CdcRequestToApi, CdcRequestToApi> {

    @Autowired
    @Qualifier("restTemplateExternal")
    private RestTemplate apiRestTemplate;

    @Autowired
    private  ExecutionContext executionContext;



    @Override
    public CdcRequestToApi process(CdcRequestToApi cdcRequestToApi) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm.ss.SSS");
        DateTimeFormatter extractDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fileName = executionContext.getString("filename");

     /*   String[] splitted = Arrays.stream(fileName.split("_"))
                                  .map(String::trim)
                                  .toArray(String[]::new);*/
        String extractDate=  Stream.of(executionContext.getString("filename").split("/")).map(String::trim).collect(Collectors.toList()).stream().filter(x->(x.contains("CV1"))
        ).collect(Collectors.toList()).get(0).split("_")[1];

        LocalDate parsedExtractedDate = LocalDate.parse(extractDate, extractDateTimeFormatter);

        String jobName = Stream.of(executionContext.getString("filename").split("/")).map(String::trim).collect(Collectors.toList()).stream().filter(x->(x.contains("CV1"))
        ).collect(Collectors.toList()).get(0).split("_")[2];;
        LocalDateTime.now().format(dateTimeFormatter);
        //LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter));
       /* CdcResponseToDb cdcResponseToDb = CdcResponseToDb.builder()*//*.cdcResponseKey(CompositeKey.builder()
                                                          .extrDt(LocalDate.now().toString()).jobNm("testJobnm").rxcImmId("12")*//*
                                                                   .vaxEventId(cdcRequestToApi.getVaxEventId())
                                                                  // .cdcCompositeKey(CompositeKey2.builder().vaxEventId(cdcRequestToApi.getVaxEventId()).jobNm(jobName)
                                                                                                // .extrDt(parsedExtractedDate.toString()).build())//.svaxEventId(cdcRequestToApi.getVaxEventId())
                                                                   .jobNm(jobName)
                                                                   .extrDt(parsedExtractedDate.toString()*//*cdcRequestToApi.getExtrDt()*//**//*LocalDate.now().toString()*//*)
                                                                   // .rxcImmId(cdcRequestToApi.getRxcImmId())
                                                                   //.activityTypCd("1F")
                                                                   .insrtTs(LocalDateTime.now().format(dateTimeFormatter))
                                                                   .upldTs(LocalDateTime.now().format(dateTimeFormatter))
                                                                   .rspnsTs(LocalDateTime.now().format(dateTimeFormatter))
                                                                   // .resultCd("resultCd")
                                                                   .statusCd("statusCd")
                                                                   //.updtTs(LocalDateTime.now().format(dateTimeFormatter))
                                                                   .uploadStatusMsg("uploadMsg")
                                                                   .valStatusMsg("valStatusMsg").build();*/
       /* final String url = "http://localhost:8080/run/cdcinfo";
        RequestEntity<CdcRequestToApi> requestEntity = RequestEntity.post(url).body(cdcRequestToApi);

        ResponseEntity<CdcResponseFromApi> cdcResponse = apiRestTemplate.postForEntity(url,requestEntity, CdcResponseFromApi.class);
        CdcResponseFromApi cdcResponseFromApi = cdcResponse.getBody();
        cdcResponseFromApi.setCdcRequestToApi(cdcRequestToApi);*/
        return cdcRequestToApi;
    }


}