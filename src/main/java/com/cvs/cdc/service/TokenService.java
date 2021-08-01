package com.cvs.cdc.service;

import com.cvs.cdc.model.TokenResponse;
import com.cvs.cdc.model.OperationType;

@FunctionalInterface
public interface TokenService {
    TokenResponse getTokenResponse(OperationType operationType);
}
