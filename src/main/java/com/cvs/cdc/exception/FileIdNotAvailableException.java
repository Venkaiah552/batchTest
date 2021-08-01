package com.cvs.cdc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileIdNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 6346530802641582648L;
    @Getter
    private final String fileName;

    public FileIdNotAvailableException(String fileName) {
        super(String.format("%s not found in the cdc Database", fileName));
        this.fileName = fileName;
    }


}
