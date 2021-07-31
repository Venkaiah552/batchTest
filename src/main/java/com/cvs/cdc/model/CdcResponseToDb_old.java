/*
package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "IDW_APPS_STG_VW.CDC_API_UPLOAD_STATUS")
@IdClass(CompositeKey2.class)

*/
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cdcId")*//*

//@EqualsAndHashCode(exclude = {"attributeOfTypeList", "attributeOfTypeSet"})
public class CdcResponseToDb_old */
/*implements Persistable<CdcResponseToDb>*//*
 {

    */
/* @EmbeddedId
     private CompositeKey cdcResponseKey;*//*

    @Id
    @Column(name = "vax_event_id")
    private String vaxEventId;

    @Column(name = "RXC_IMM_ID")
    @Id
    private String rxcImmId;

    @Column(name = "EXTR_DT")
    @Id
    private String extrDt;

    @Column(name = "JOB_NM")
    @Id
    private String jobNm;

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

    @Column(name = "VAL_STATUS_MSG")
    private String valStatusMsg;

    @Column(name = "UPLOAD_STATUS_MSG")
    private String uploadStatusMsg;

    @Column(name="UPLD_TS")
    private String upldTs;

    @Column(name="RSPNS_TS")
    private String rspnsTs;

    @Override
    public String toString() {
        return "CdcResponseToDb{" +
                "vaxEventId='" + vaxEventId + '\'' +
                ", rxcImmId='" + rxcImmId + '\'' +
                ", extrDt='" + extrDt + '\'' +
                ", jobNm='" + jobNm + '\'' +
                ", activityTypCd='" + activityTypCd + '\'' +
                ", resultCd='" + resultCd + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", insrtTs='" + insrtTs + '\'' +
                ", updtTs='" + updtTs + '\'' +
                ", valStatusMsg='" + valStatusMsg + '\'' +
                ", uploadStatusMsg='" + uploadStatusMsg + '\'' +
                ", upldTs='" + upldTs + '\'' +
                ", rspnsTs='" + rspnsTs + '\'' +
                '}';
    }


*/
/*




    @Id
    @Column(name="cdc_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int cdcId;
    @Column(name="status")// Teradata procedure understands once the spring batch program runs and takes the chunks ( makes the chunk records status 0 to 1)
    private String status ;
    @Column(name="create_at")
    private String createAt ;
    @Column(name="updated_at")
    private String updatedAt;
    @Column(name="validation_errors")
    private String validationErrors ;
    @Column(name="processing_errors")
    private String processingErrors ;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_by")
    private String udpatedBy;
    @Column(name="status_message")
    private String cdcStatusMessage;
    @Column(name="vax_event_id")
    private String vaxEventId;
   *//*

*/
/* @OneToOne(cascade = CascadeType.ALL fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "cdc_vax_event_id", referencedColumnName = "vax_event_id")
    @JsonManagedReference
    @JsonBackReference*//*
*/
/*

   // @JsonIgnore
   private CdcRequestToApi cdcRequestToApi;
*//*


    */
/*@Override
    public CdcResponseToDb getId() {
        return CdcResponseToDb.builder().cdcId(cdcId)
                .cdcRequestToApi(cdcRequestToApi)
                .cdcStatusMessage(cdcStatusMessage)
                .createAt(createAt).build();
    }

    @Override
    public boolean isNew() {
        return false;
    }*//*


    //It is addition of validation and processing errors

}
*/
