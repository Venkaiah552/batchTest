package com.cvs.cdc.controller;

import com.cvs.cdc.model.*;
import com.cvs.cdc.runner.JobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/*
url: http://localhost:8080/run/job
 */

@RestController
@RequestMapping("/run")
public class JobController {

    private JobRunner jobRunner;


    @Autowired
    public JobController(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }


    @RequestMapping(value = "/cvstoapi")
    public String runJobDbToApiDemo() {
        jobRunner.runBatchJobDbtoApi();
        return String.format("Job cvs db to cdc api submitted successfully.");
    }

    //this end point gets called internally by the batch program.
    @PostMapping(value = "/cdcinfo", consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
    public CdcResponseFromApi getCdcInfo(@RequestBody CdcRequestToApi cdcRequestToApi) {
        return CdcResponseFromApi.builder()/*.id(*//*cdcRequestToApi.getVaxEventId()*//*)*/
                .storageResult(StorageResult.builder().nON_REDACTED_DB(NONREDACTEDDB.builder()
                        .status("SUCCESS").build()).rEDACTED_DB(REDACTEDDB.builder().status("SUCCESS").build()).build())
                .validationErrors(new ArrayList<>()).processingErrors(new ArrayList<>()).build();
    }

   /* @RequestMapping(value = "/csvtodbmultithread")
    public String runJobDemoCsvToDbMultiThread() {
        jobRunner.runBatchJobCsvToDbMultiThread();
        return String.format("Job Demo1 submitted successfully.");
    }*/
}