package com.cvs.cdc.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Table(name = "IMM_COVID_IDNTFD_EXTRACT")
public class CdcRequestToApi implements Serializable {


    @Column(name = "EXTR_DT")
    @Id
    private String extrDt;

    @Column(name = "JOB_NM")
    @Id
    private String jobNm;

    @Column(name = "vax_event_id")
    private String vaxEventId;

    @Column(name = "ext_type ")
    private String extType;

    @Column(name = "pprl_id ")
    private String pprlId;

    @Column(name = "recip_id")
    private String recipId;

    @Column(name = "recip_first_name")
    private String recipFirstName;

    @Column(name = "recip_middle_name")
    private String recipMiddleName;

    @Column(name = "recip_last_name")
    private String recipLastName;

    @Column(name = "recip_dob")
    private String recipDob;


    @Column(name = "recip_sex")
    private String recipSex;

    @Column(name = "recip_address_street")
    private String recipAddressStreet;

    @Column(name = "recip_address_street_2 ")
    private String recipAddressStreet_2;

    @Column(name = "recip_address_city")
    private String recipAddressCity;

    @Column(name = "recip_address_county")
    private String recipAddressCounty;

    @Column(name = "recip_address_state")
    private String recipAddressState;

    @Column(name = "recip_address_zip")
    private String recipAddressZip;

    @Column(name = "recip_race_1")
    private String recipRace1;
    //coded value
    @Column(name = "recip_race_2")
    private String recipRace2;
    //coded value
    @Column(name = "recip_race_3")
    private String recipRace3;
    //coded value
    @Column(name = "recip_race_4")
    private String recipRace4;
    //coded value
    @Column(name = "recip_race_5")
    private String recipRace5;
    //coded value
    @Column(name = "recip_race_6")
    private String recipRace6;
    //coded value
    @Column(name = "recip_ethnicity")
    private String recipEthnicity;

    @Column(name = "ADMIN_DATE")
    private String adminDate;
    //coded value
    @Column(name = "CVX")
    private String cvx;
    //coded value
    @Column(name = "ndc")
    private String ndc;
    //coded value
    @Column(name = "mvx")
    private String mvx;

    @Column(name = "LOT_NUMBER")
    private String lotNumber;

    @Column(name = "VAX_EXPIRATION")
    private String vaxExpiration;

    @Column(name = "VAX_ADMIN_SITE")
    private String vaxAdminSite;

    @Column(name = "VAX_ROUTE")
    private String vaxRoute;

    @Column(name = "DOSE_NUM")
    private String doseNum;

    @Column(name = "VAX_SERIES_COMPLETE")
    private String vaxSeriesComplete;

    @Column(name = "RESPONSIBLE_ORG")
    private String responsibleOrg;

    @Column(name = "ADMIN_NAME")
    private String adminName;

    @Column(name = "VTRCKS_PROV_PIN")
    private String vtrcksProvPin;

    @Column(name = "ADMIN_TYPE")
    private String adminType;

    @Column(name = "ADMIN_ADDRESS_STREET")
    private String adminAddressStreet;

    @Column(name = "ADMIN_ADDRESS_STREET_2")
    private String adminAddressStreet_2;

    @Column(name = "ADMIN_ADDRESS_CITY")
    private String adminAddressCity;

    @Column(name = "ADMIN_ADDRESS_COUNTY")
    private String adminAddressCounty;

    @Column(name = "ADMIN_ADDRESS_STATE")
    private String adminAddressState;

    @Column(name = "ADMIN_ADDRESS_ZIP")
    private String adminAddressZip;

    @Column(name = "VAX_REFUSAL")
    private String vaxRefusal;

    @Column(name = "CMORBID_STATUS")
    private String cmorbidStatus;

    @Column(name = "SEROLOGY")
    private String serology;

    @Column(name = "ACTVY_TYP_CD")
    private String activityTypCd;

    @Column(name = "RESULT_CD")
    private String resultCd;

    @Column(name = "STATUS_CD")
    private String statusCd;

    @Column(name = "INSRT_TS")
    private String insrtTs;

    @Column(name = "UPDT_TS")
    private String updtTs;

    @Column(name = "UPLD_TS")
    private String upldTs;

    @Column(name = "RSPNS_TS")
    private String rspnsTs;

    @Column(name = "VAL_STATUS_MSG")
    private String valStatusMsg;

    @Column(name = "UPLOAD_STATUS_MSG")
    private String uploadStatusMsg;


    private String fileName;


}
