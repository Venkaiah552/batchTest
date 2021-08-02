package com.cvs.cdc.service;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseFromApi;
import com.cvs.cdc.model.OperationType;
import com.cvs.cdc.model.ValidationError;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class OrchestrationServiceTest {

    @InjectMocks
    OrchestrationService orchestrationService;

    @Mock
    CdcAPIService cdcAPIService;
    @Mock
    DBWriterService dbWriterService;


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void processDataTest() throws IOException {

    /*    List<String> data = Files.readAllLines(Paths.get("../../../../resource/CV1_20201207_sjxd626d1_01.txt"),
                StandardCharsets.UTF_8);

        data.remove(0);

        data.forEach(line -> {
            String[] columns = line.split("\t");

        });*/
        List<CdcRequestToApi> cdcRequestToApis = new ArrayList<>();
        File file = new File("D:\\freelance_srini\\java\\src\\test\\resources\\CV1_20201207_sjxd626d1_01.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            sc.hasNextLine(); //ignore header
            while(sc.hasNext()){
                String str = sc.nextLine();
                cdcRequestToApis.add(parseLine(str));
            }

        } catch (IOException  exp) {
            exp.printStackTrace();
        }

        sc.close();

        CdcResponseFromApi response = loadCdcResponse();
        List<ValidationError> validationErrorList = new ArrayList<>();
        Mockito.when(cdcAPIService.postUpdateValidateData(OperationType.VALIDATION, cdcRequestToApis)).thenReturn(response);
        Mockito.doNothing().when(dbWriterService).writeErrorsToDB(OperationType.VALIDATION, response,validationErrorList);
        Mockito.when(cdcAPIService.postUpdateValidateData(OperationType.UPLOAD, cdcRequestToApis)).thenReturn(response);
        Mockito.doNothing().when(dbWriterService).writeErrorsToDB(OperationType.UPLOAD, response,validationErrorList);
        orchestrationService.processData(cdcRequestToApis);

    }

    private CdcResponseFromApi loadCdcResponse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //JSON file to Java object
        CdcResponseFromApi cdcResponseFromApi = mapper.readValue(new File("D:\\freelance_srini\\java\\src\\test\\resources\\validateresponse.json"),
                CdcResponseFromApi.class);
        return cdcResponseFromApi;

    }

    private CdcRequestToApi parseLine(String str){

        Scanner sc = new Scanner(str);
        sc.useDelimiter("\t");
        CdcRequestToApi cdcRequestToApi = null;
        // Check if there is another line of input
        while(sc.hasNext()){
        cdcRequestToApi = new CdcRequestToApi(
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next(),sc.next(), sc.next(), sc.next(),
               sc.next(), sc.next(), sc.next()
                      );
        }
        sc.close();
        return cdcRequestToApi;
    }
}
