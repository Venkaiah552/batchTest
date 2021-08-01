package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "CDC_CDC_FILE_PROCESS")
@Immutable
public class CdcResponseToDb implements Serializable {


    @Column(name = "FILE_NM")
    @Id
    private String fileNm;

    @Column(name = "EXTR_DT")
    private String extrDt;

    @Column(name = "JOB_NM")
    private String jobNm;

    @Column(name = "FILE_REC_CNT")
    private int fileRecCnt;

    @Column(name = "FILE_CRTE_TS")
    private Instant fileCrteTs;

    @Column(name = "APP_INIT_TS")
    private Instant appInitTs;

    @Column(name = "VAL_RSPNS_TS")
    private Instant valRspnsTs;

    @Column(name = "VAL_PROCESS_CNT")
    private int valProcessCnt;

    @Column(name = "VAL_ERROR_CNT")
    private int valErrorCnt;

    @Column(name = "UPLOAD_RSPNS_TS")
    private Instant uploadRspnsTs;

    @Column(name = "UPLOAD_PROCESS_CNT")
    private int uploadProcessCnt;

    @Column(name = "UPLOAD_ERROR_CNT")
    private int uploadErrorCnt;

    @Column(name = "STATUS_CD")
    private StatusCode statusCd;


}
