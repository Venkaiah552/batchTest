package com.cvs.cdc.model;

import lombok.*;

import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class CdcRequestToApi implements Serializable {


    private String vaxEventId;
    private String extrDt;
    private String pprlId;
    private String recipId;

    private String recipFirstName;

    private String recipMiddleName;
    private String recipLastName;

    private String recipDob;

    private String recipSex;
    private String recipAddressStreet;
    private String recipAddressStreet_2;

    private String recipAddressCity;

    private String recipAddressCounty;

    private String recipAddressState;

    private String recipAddressZip;


    private String recipRace1;
    private String recipRace2;
    private String recipRace3;
    private String recipRace4;
    private String recipRace5;
    private String recipRace6;
    private String recipEthnicity;
    private String adminDate;
    private String cvx;
    private String ndc;
    private String mvx;
    private String lotNumber;
    private String vaxExpiration;
    private String vaxAdminSite;
    private String vaxRoute;

    private String doseNum;
    private String vaxSeriesComplete;

    private String responsibleOrg;
    private String adminName;

    private String vtrcksProvPin;
    private String adminType;
    private String adminAddressStreet;
    private String adminAddressStreet_2;
    private String adminAddressCity;
    private String adminAddressCounty;
    private String adminAddressState;
    private String adminAddressZip;

    private String vaxRefusal;
    private String cmorbidStatus;

    private String serology;

}
