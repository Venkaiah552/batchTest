package com.cvs.cdc.service;

import com.cvs.cdc.model.CdcAppConfig;
import com.cvs.cdc.model.CdcResponseFromApi;
import com.cvs.cdc.model.OperationType;
import com.cvs.cdc.model.ValidationError;
import com.cvs.cdc.repo.CdcAppConfigRepo;
import com.cvs.cdc.repo.CdcFileProcessErrorRepo;
import com.cvs.cdc.repo.CdcRespRepo;
import com.cvs.cdc.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@DataJpaTest
public class DBWriterServiceTest {

    @InjectMocks
    DBWriterService dbWriterService;


    @Autowired
    CdcRespRepo cdcRespRepo;
    @Autowired
    ExecutionContext executionContext;
    @Autowired
    CdcFileProcessErrorRepo cdcFileProcessErrorRepo;
    @Autowired
    CdcAppConfigRepo cdcAppConfigRepo;

    CdcResponseFromApi cdcResponseFromApi;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test() {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step-under-test");

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @BeforeEach
    void setUp() throws IOException {
        CdcAppConfig cdcAppConfig = new CdcAppConfig();
        cdcAppConfig.setConfigVal("3");

        cdcAppConfigRepo.save(cdcAppConfig);
        cdcResponseFromApi = loadCdcResponse();
    }

    @Test
    void writeErrorsToDBTest() {
        executionContext.put(Constants.FILE_NAME_CONTEXT_KEY, "CV1");
        List<ValidationError> validationErrorList = new ArrayList<>();
        dbWriterService.writeErrorsToDB(OperationType.VALIDATION, cdcResponseFromApi, validationErrorList);

    }


    private CdcResponseFromApi loadCdcResponse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //JSON file to Java object
        CdcResponseFromApi cdcResponseFromApi = mapper.readValue(new File("D:\\freelance_srini\\java\\src\\test\\resources\\validateresponse.json"),
                CdcResponseFromApi.class);
        return cdcResponseFromApi;

    }
}
