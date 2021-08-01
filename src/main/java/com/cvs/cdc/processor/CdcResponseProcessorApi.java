package com.cvs.cdc.processor;

import com.cvs.cdc.model.CdcRequestToApi;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Qualifier("cdcresponseeprocessor")
public class CdcResponseProcessorApi implements ItemProcessor<CdcRequestToApi, CdcRequestToApi> {


    @Autowired
    private ExecutionContext executionContext;


    @Override
    public CdcRequestToApi process(CdcRequestToApi cdcRequestToApi) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm.ss.SSS");
        DateTimeFormatter extractDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fileName = executionContext.getString("filename");

        String extractDate = Stream.of(executionContext.getString("filename")
                        .split("/")).map(String::trim).collect(Collectors.toList()).stream()
                .filter(x -> (x.contains("CV1"))
                ).collect(Collectors.toList()).get(0).split("_")[1];

        LocalDate parsedExtractedDate = LocalDate.parse(extractDate, extractDateTimeFormatter);

        String jobName = Stream.of(executionContext.getString("filename").split("/")).map(String::trim).collect(Collectors.toList()).stream().filter(x -> (x.contains("CV1"))
        ).collect(Collectors.toList()).get(0).split("_")[2];
        ;


        return cdcRequestToApi;
    }


}