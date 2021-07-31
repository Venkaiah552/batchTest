package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

        private String clientID;
        private String clientSecret;
        private List<String> scopes;

}
