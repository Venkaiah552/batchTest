package com.cvs.cdc.service;


import com.cvs.cdc.exception.FileIdNotAvailableException;
import com.cvs.cdc.model.*;
import com.cvs.cdc.repo.CdcAppConfigRepo;
import com.cvs.cdc.repo.CdcFileProcessErrorRepo;
import com.cvs.cdc.repo.CdcRespRepo;
import com.cvs.cdc.utils.Constants;
import com.cvs.cdc.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DBWriterService {

    private final CdcRespRepo cdcRespRepo;
    private final ExecutionContext executionContext;
    private final CdcFileProcessErrorRepo cdcFileProcessErrorRepo;
    private final CdcAppConfigRepo cdcAppConfigRepo;

    @Value("${DEFAULT.REC.COUNT}")
    private long NO_ERR_REC_WRITTEN_TO_PRC_ERR_TBL;

    /**
     * Method to write api response validate/upload errors to DB
     * @param operationType
     * @param response
     * @param errorList
     */
    public void writeErrorsToDB(OperationType operationType, CdcResponseFromApi response, List<ValidationError> errorList) {
        val cdcAppConfigs = cdcAppConfigRepo.findAll();

        if (!Utils.isNullOrEmpty(cdcAppConfigs)) {
            NO_ERR_REC_WRITTEN_TO_PRC_ERR_TBL = Long.valueOf(cdcAppConfigs.get(0).getConfigVal());
        }

        if (response != null) {
            // check config entries based on value wrote only those no.of records
            checkFileExists();
            CdcResponseToDb cdcResponseToDb = CdcResponseToDb.builder()
                    .fileNm(executionContext.getString(Constants.FILE_NAME_CONTEXT_KEY))
                    .appInitTs(Instant.now())
                    .valRspnsTs(Instant.now())
                    .valProcessCnt(Utils.isOperationValidation(operationType) ? response.getValidationErrorsCount() : 0)
                    .valErrorCnt(Utils.isOperationValidation(operationType) ? errorList.size() : 0)
                    .uploadRspnsTs(Instant.now())
                    .uploadProcessCnt(Utils.isOperationUpload(operationType) ? response.getValidationErrorsCount() : 0)
                    .uploadErrorCnt(errorList.size())
                    .statusCd(errorList.size() > 0 ? StatusCode.FAIL : StatusCode.SUCCESS)
                    .build();
            cdcRespRepo.save(cdcResponseToDb);
            saveFileProcessError(operationType, errorList, NO_ERR_REC_WRITTEN_TO_PRC_ERR_TBL);
        }

    }


    private void checkFileExists() {
        cdcRespRepo.findByFileNmAndExtrDtAndJobNm(
                        executionContext.getString(Constants.FILE_NAME_CONTEXT_KEY),
                        executionContext.getString(Constants.FILE_EXTRACT_DATE_CONTEXT_KEY),
                        executionContext.getString(Constants.FILE_EXTRACT_JOB_CONTEXT_KEY))
                .orElseThrow(() -> new FileIdNotAvailableException(executionContext.getString(Constants.FILE_NAME_CONTEXT_KEY)));
    }

    private void saveFileProcessError(OperationType operationType, List<ValidationError> errorList, long NO_ERR_REC_WRITTEN_TO_PRC_ERR_TBL) {
        if (!Utils.isNullOrEmpty(errorList)) {
            List<CdcFileProcessError> cdcFileProcessErrorList = new ArrayList<>();
            errorList.stream().limit(NO_ERR_REC_WRITTEN_TO_PRC_ERR_TBL).forEach(validationError -> {
                CdcFileProcessError cdcFileProcessError = CdcFileProcessError.builder()
                        .fileName(executionContext.getString(Constants.FILE_NAME_CONTEXT_KEY))
                        .errorTs(Instant.now())
                        .errorType(operationType.name())
                        .errorMsg(validationError.getField())
                        .recipId(validationError.getRecipId())
                        .dchRspnsId(validationError.getDchRspnsId())
                        .vaccEvntId(validationError.getVaxEventId())
                        .build();
                cdcFileProcessErrorList.add(cdcFileProcessError);
            });
            cdcFileProcessErrorRepo.saveAll(cdcFileProcessErrorList);
        } else {
            log.debug("Empty collection nothing to save to file process error table..");
        }

    }


}
