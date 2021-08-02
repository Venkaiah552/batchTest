package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {

    private String dchRspnsId;
    private String line;
    private String vaxEventId;
    private String recipId;
    private String field;
    private String message;


}
