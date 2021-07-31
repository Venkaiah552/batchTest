package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "IDW_APPS_STG_VW.CDC_CDC_FILE_PROCESS")
/*@IdClass(CompositeKey.class)*/
@Immutable

/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cdcId")*/
//@EqualsAndHashCode(exclude = {"attributeOfTypeList", "attributeOfTypeSet"})
public class CdcResponseToDb implements Serializable {

    /* @EmbeddedId
     private CompositeKey cdcResponseKey;*/

    @Column(name = "FILE_NM")
    @Id
    private String fileNm;

    @Column(name = "EXTR_DT")
    private String extrDt;

    @Column(name = "JOB_NM")
    private String jobNm;

    @Column(name = "FILE_REC_CNT")
    private String fileRecCnt;

    @Column(name = "FILE_CRTE_TS")
    private String fileCrteTs;

    @Column(name = "APP_INIT_TS")
    private String appInitTs;

    @Column(name = "VAL_RSPNS_TS")
    private String valRspnsTs;

    @Column(name = "VAL_PROCESS_CNT")
    private String valProcessCnt;

    @Column(name = "VAL_ERROR_CNT")
    private String valErrorCnt;

    @Column(name = "UPLOAD_RSPNS_TS")
    private String uploadRspnsTs;

    @Column(name = "UPLOAD_PROCESS_CNT")
    private String uploadProcessCnt;

    @Column(name = "UPLOAD_ERROR_CNT")
    private String uploadErrorCnt;

    @Column(name = "STATUS_CD")
    private String statusCd;







    /*@Column(name = "RXC_IMM_ID")
    @Id
    private String rxcImmId;*/






   /* @Column(name = "ACTVY_TYP_CD")
    private String activityTypCd;

    @Column(name = "RESULT_CD")
    private String resultCd;*/

   /* @Column(name = "STATUS_CD")
    private String statusCd;

    @Column(name = "INSRT_TS")
    private String insrtTs;*/

   /* @Column(name = "UPDT_TS")
    private String updtTs;*/

   /* @Column(name = "VAL_STATUS_MSG")
    private String valStatusMsg;

    @Column(name = "UPLOAD_STATUS_MSG")
    private String uploadStatusMsg;

    @Column(name="UPLD_TS")
    private String upldTs;

    @Column(name="RSPNS_TS")
    private String rspnsTs;
*/


    /*@Override
    public CompositeKey getId() {
        return CompositeKey.builder().vaxEventId(vaxEventId).extrDt(extrDt).jobNm(jobNm).build();
    }*/

    /*@Override
    public boolean isNew() {

       if(vaxEventId == null || extrDt == null || jobNm == null)
        return true;
       else
           return false;
    }*/
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
   */
/* @OneToOne(cascade = CascadeType.ALL fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "cdc_vax_event_id", referencedColumnName = "vax_event_id")
    @JsonManagedReference
    @JsonBackReference*//*

   // @JsonIgnore
   private CdcRequestToApi cdcRequestToApi;
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
    }*/

    //It is addition of validation and processing errors

}
