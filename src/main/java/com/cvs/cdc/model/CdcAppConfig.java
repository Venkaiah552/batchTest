package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CDC_APP_CONFIG")
public class CdcAppConfig implements Serializable {

    @Column(name = "CONFIG_VAL")
    @Id
    private String configVal;

    @Column(name = "ACTV_IND")
    private Character actvInd;
}
