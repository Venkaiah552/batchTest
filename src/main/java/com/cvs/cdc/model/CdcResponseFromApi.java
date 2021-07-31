package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CdcResponseFromApi {


    private String id;
    public List<String> validationErrors;
    public List<String> processingErrors;
    public StorageResult storageResult;
    private String vaxEventId;
    public int totalRecords;
    public int validationErrorsCount;
    public boolean truncated;


}
