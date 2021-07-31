package com.cvs.cdc.model;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Immutable
@Table(name="IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT")
@IdClass(CompositeKey.class)
//@EqualsAndHashCode(exclude = {"attributeOfTypeList", "attributeOfTypeSet"})
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "vaxEventId")*/
/*@NamedQueries({
        @NamedQuery(name = "CdcRequestToApi.findAll", query = "select u from CdcRequestToApi u")
})*/
/*@NamedNativeQueries({
        @NamedNativeQuery(name = "selectAuthorNames", query = "select * from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT"),
        *//*@NamedNativeQuery(name = "selectAuthorEntities", query = "SELECT a.id, a.version, a.firstname, a.lastname FROM Author a", resultClass = Author.class),
        @NamedNativeQuery(name = "selectAuthorValue", query = "SELECT a.id, a.firstname, a.lastname, count(b.id) as numBooks FROM Author a JOIN BookAuthor ba on a.id = ba.authorid JOIN Book b ON b.id = ba.bookid GROUP BY a.id", resultSetMapping = "AuthorValueMapping")*//*
})*/

/*@NamedNativeQuery(
        name = "CdcRequestToApi.findAll",
        query = "select * from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT  where actvy_typ_cd='INIT'")*/
public class CdcRequestToApi {


    public CdcRequestToApi(String vaxEventId, String rxcImmId, String extrDt, String jobNm, String extType) {
        this.vaxEventId = vaxEventId;
       // this.rxcImmId = rxcImmId;
        this.extrDt = extrDt;
        this.jobNm = jobNm;
        this.extType = extType;
    }
    /*@EmbeddedId
    private CompositeKey cdcResponseKey;*/
   // @Id

    @Column(name="EXTR_DT")
    @Id
    private String extrDt;

    @Column(name="JOB_NM")
    @Id
    private String jobNm;

    @Column(name="vax_event_id")
    private String vaxEventId;

    @Column(name="ext_type ")
    private String extType;

    @Column(name="pprl_id ")
    private  String pprlId;

    @Column(name="recip_id")
    private String recipId;

    @Column(name="recip_first_name")
    private String recipFirstName;

    @Column(name="recip_middle_name")
    private String recipMiddleName;

    @Column(name="recip_last_name")
    private String recipLastName;



    //The below is used only when reading from db is done.
   /* @Column(name="RXC_IMM_ID")
    @Id
    private String rxcImmId;*/











    @Column(name="recip_dob")
    private String recipDob;

  /*  @Column(name="recip_sex")
    private Character recipSex;*/

    @Column(name="recip_sex")
    private String recipSex;

    @Column(name="recip_address_street")
    private String recipAddressStreet;

    @Column(name="recip_address_street_2 ")
    private String recipAddressStreet_2;

    @Column(name="recip_address_city")
    private String recipAddressCity;

    @Column(name="recip_address_county")
    private String recipAddressCounty;

    @Column(name="recip_address_state")
    private String recipAddressState;

    @Column(name="recip_address_zip")
    private String recipAddressZip;
  /*  @OneToOne(mappedBy = "cdcRequestToApi",cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)*/
    //@PrimaryKeyJoinColumn
    //@MapsId
   // @JsonManagedReference
    //@JsonIgnore
   /* @JsonManagedReference
    @JsonBackReference
    @JsonIgnore*/
   //@JsonIgnore
   // @JsonIgnoreProperties("cdcRequestToApi")
   /* @JsonIgnore
    @ToString.Exclude*/
  /*  private CdcResponseToDb cdcResponseToDb;*/
    @Column(name="recip_race_1")
    private String recipRace1;
    //coded value
    @Column(name="recip_race_2")
    private String recipRace2;
    //coded value
    @Column(name="recip_race_3")
    private String recipRace3;
    //coded value
    @Column(name="recip_race_4")
    private String recipRace4;
    //coded value
    @Column(name="recip_race_5")
    private String recipRace5;
    //coded value
    @Column(name="recip_race_6")
    private String recipRace6;
    //coded value
    @Column(name="recip_ethnicity")
    private String recipEthnicity;

    @Column(name="ADMIN_DATE")
    private String   adminDate;
    //coded value
    @Column(name="CVX")
    private String cvx;
    //coded value
    @Column(name="ndc")
    private String ndc;
    //coded value
    @Column(name="mvx")
    private String mvx;

    @Column(name="LOT_NUMBER")
    private String lotNumber;

    @Column(name="VAX_EXPIRATION")
    private String vaxExpiration;

    @Column(name="VAX_ADMIN_SITE")
    private String vaxAdminSite;

    @Column(name="VAX_ROUTE")
    private String vaxRoute;

    @Column(name="DOSE_NUM")
    private String doseNum;

    @Column(name="VAX_SERIES_COMPLETE")
    private String vaxSeriesComplete;

    @Column(name="RESPONSIBLE_ORG")
    private String responsibleOrg;

    @Column(name="ADMIN_NAME")
    private String adminName;

    @Column(name = "VTRCKS_PROV_PIN")
    private String vtrcksProvPin;

    @Column(name="ADMIN_TYPE")
    private String adminType;

    @Column(name="ADMIN_ADDRESS_STREET")
    private String adminAddressStreet;

    @Column(name="ADMIN_ADDRESS_STREET_2")
    private String adminAddressStreet_2;

    @Column(name="ADMIN_ADDRESS_CITY")
    private String adminAddressCity;

    @Column(name="ADMIN_ADDRESS_COUNTY")
    private String adminAddressCounty;

    @Column(name="ADMIN_ADDRESS_STATE")
    private String adminAddressState;

    @Column(name="ADMIN_ADDRESS_ZIP")
    private String adminAddressZip;

    @Column(name="VAX_REFUSAL")
    private String vaxRefusal;

    @Column(name="CMORBID_STATUS")
    private String cmorbidStatus;

    @Column(name="SEROLOGY")
    private String serology;

    @Column(name="ACTVY_TYP_CD")
    private String activityTypCd;

    @Column(name="RESULT_CD")
    private String resultCd;

    @Column(name="STATUS_CD")
    private String statusCd;

    @Column(name="INSRT_TS")
    private String insrtTs;

    @Column(name="UPDT_TS")
    private String updtTs;

    @Column(name="UPLD_TS")
    private String upldTs;

    @Column(name="RSPNS_TS")
    private String rspnsTs;

    @Column(name="VAL_STATUS_MSG")
    private String valStatusMsg;

    @Column(name="UPLOAD_STATUS_MSG")
    private String uploadStatusMsg;


    private String fileName;





}
