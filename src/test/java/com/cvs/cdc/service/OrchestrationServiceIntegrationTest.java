package com.cvs.cdc.service;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseFromApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrchestrationServiceIntegrationTest {

    @Autowired
    OrchestrationService orchestrationService;

    @Autowired
    CdcAPIService cdcAPIService;
    @Autowired
    DBWriterService dbWriterService;


    @Test
    void processDataTest() throws IOException {
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

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        sc.close();

        CdcResponseFromApi response = loadCdcResponse();

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
