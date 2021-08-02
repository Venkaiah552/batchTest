package com.cvs.cdc.writer;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.repo.CdcRespRepo;
import com.cvs.cdc.service.OrchestrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("cdcdbresponsewriter")
@Slf4j
public class CdcResponseDBWriter implements ItemWriter<CdcRequestToApi> {
    @Autowired
    private CdcRespRepo cdcRespRepo;


    @Value("${cdc.validate.upload.url}")
    private String validateUploadUrl;

    @Autowired
    private OrchestrationService orchestrationService;

    @Override
    public void write(List<? extends CdcRequestToApi> listCdcResponseToDb) throws Exception {

        log.info("calling orchestration service with payload of size {}", listCdcResponseToDb.size());
        orchestrationService.processData((List<CdcRequestToApi>) listCdcResponseToDb);
        listCdcResponseToDb.stream().forEach(x -> System.out.println("Record::: extract dt::" + x.getExtrDt() + " vaxEventId::" + x.getVaxEventId()
               ));
        List<? extends CdcRequestToApi> list1CdcResponseToDb = listCdcResponseToDb.stream().filter(x -> !(x.getVaxEventId().equalsIgnoreCase("vax_event_id"))).collect(Collectors.toList());
        System.out.println("count of records getting updated to db::: " + list1CdcResponseToDb.size() + "\n");
        list1CdcResponseToDb.stream().forEach(x -> System.out.println("Record::: extract dt::" + x.getExtrDt() + " vaxEventId::" + x.getVaxEventId() +
                " jobnm::" ));

    }




    /*
     * TODO :
     *  1. call token for validate funcationality
     * 2. with token call the validate api
     * 3. write the validation errors to db
     * 4. exclude those validation error from the actual payload for the uplaod
     * 5. write the upload errors to db.
     * 6. send an email to stakeholders once the job is completed ( as an at 1pm 30 files are there , after completion of 30 files, just read the data from table and send the email.
     * 7. for 1/2/3/5 - we need to retry 5 times with 5 sec pause in beteen the calls when the end point is down or 5XX erros. we have to send the email to stakeholders.
     */

    private String getCDCResponeforValidate(CdcRequestToApi cdcRequestToApi) {

        return "{\"id\":\"d8a322dd-fc95-4a8c-972d-01d38d4a7839\",\"validationErrors\":[\"line#8: vaxEventId=4821914116077 recipId=48219141160770200 field=recip_dob message=invalid date format, expected: \\\"YYYY-MM-DD\\\" or \\\"YYYY-MM\\\"\",\"line#79: vaxEventId=48267-R73677414 recipId=48267-R73677414-0550 field=admin_date message=invalid date format, expected: \\\"YYYY-MM-DD\\\" or \\\"YYYY-MM\\\"\",\"line#101: vaxEventId=48279661062245 recipId=482796610622457089 field=admin_date message=admin_date must be on or before submission date\",\"line#103: vaxEventId=48279661062246 recipId=482796610622466335 field=recip_dob message=recip_dob must be on or before admin_date\",\"line#147: vaxEventId=48294R55623784 recipId=48294R556237848921 field=recip_address_zip message=must be a valid ZIP. 5 digit or 5 digit + 4 are acceptable\",\"line#149: vaxEventId=4830668202326 recipId=48306682023261667 field=admin_date message=admin_date must be on or before submission date\",\"line#165: vaxEventId=483296400314 recipId=4832964003146794 field=admin_date message=admin_date must be on or before submission date\"],\"processingErrors\":[\"storage=NON_REDACTED_DB vaxEventID=48232819120296 recipId=482328191202960000 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677372 recipId=48267-R73677372 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677381 recipId=48267-R73677381 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677381 recipId=48267-R73677381 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100027 recipId=48333451000270373 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100052 recipId=4833345100052 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100052 recipId=4833345100052 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4833345100055 recipId=48333451000556958 message=Record already exists\",\"storage=NON_REDACTED_DB vaxEventID=4853055596583 recipId=4853055596583 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677413 recipId=48267-R73677413 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=48267-R73677666 recipId=48267-R73677666 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\",\"storage=REDACTED_DB vaxEventID=4833345100027 recipId=4833345100027 message=Record already exists\"],\"truncated\":true,\"storageResult\":{\"NON_REDACTED_DB\":{\"status\":\"SUCCESS\"},\"REDACTED_DB\":{\"status\":\"SUCCESS\"}}}";
    }

}
