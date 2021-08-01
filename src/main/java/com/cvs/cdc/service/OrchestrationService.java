package com.cvs.cdc.service;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseFromApi;
import com.cvs.cdc.model.OperationType;
import com.cvs.cdc.model.ValidationError;
import com.cvs.cdc.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrchestrationService {

    private final CdcAPIService cdcAPIService;
    private final DBWriterService dbWriterService;

    /**
     * Method to orchestrate different services
     * @param request
     */
    public void processData(List<CdcRequestToApi> request) {
        val validateResponse = cdcAPIService.postUpdateValidateData(OperationType.VALIDATION, request);
        if (validateResponse != null) {
            log.debug("Validate api Response validation errors size {}", validateResponse.getValidationErrors().size());
            val validationErrorList = parseError(validateResponse.getId(), validateResponse.getValidationErrors());
            val uniqueVaxEvents = validationErrorList.stream()
                    .map(ValidationError::getVaxEventId)
                    .collect(Collectors.toSet());
            // Exclude validation vaxeventIds
            request.removeAll(uniqueVaxEvents);
            //Write errors to DB
            dbWriterService.writeErrorsToDB(OperationType.VALIDATION, validateResponse, validationErrorList);
            CdcResponseFromApi uploadResponse = cdcAPIService.postUpdateValidateData(OperationType.UPLOAD, request);
            log.debug("Upload api Response processingErrors size {}", validateResponse.getProcessingErrors().size());
            val processingErrorList = parseError(validateResponse.getId(), validateResponse.getValidationErrors());
            dbWriterService.writeErrorsToDB(OperationType.UPLOAD, uploadResponse, processingErrorList);
        }

    }

    private List<ValidationError> parseError(String id, List<String> lines) {
        List<ValidationError> validationErrorList = new ArrayList<>();
        if (!Utils.isNullOrEmpty(lines)) {
            for (String line : lines) {
                ValidationError validationError = new ValidationError();
                validationError.setDchRspnsId(id);
                String[] data = line.split("[:;=]");
                validationError.setLine(data[0]);
                validationError.setVaxEventId(data[1]);
                validationError.setRecipId(data[2]);
                validationError.setField(data[3]);
                validationErrorList.add(validationError);
            }
        } else {
            log.debug("Empty collection nothing to procced..");
        }
        return validationErrorList;
    }


}
