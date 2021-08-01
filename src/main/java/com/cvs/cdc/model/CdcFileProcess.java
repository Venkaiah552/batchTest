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
@Table(name = "CDC_FILE_PROCESS")

public class CdcFileProcess implements Serializable {


    @Id
    @Column(name = "file_name")
    private String fileName;


    @Column(name = "EXTR_DT")
    private String extrDt;

    @Column(name = "JOB_NM")
    @Id
    private String jobNm;

    @Column(name = "file_rec_cnt ")
    private String fileRecCnt;

    @Column(name = "file_crte_ts ")
    private String file_create_ts;

    @Column(name = "app_init_ts")
    private String appInitTs;

    @Column(name = "val_rspns_ts")
    private String valRspnsTs;

    @Column(name = "val_process_cnt")
    private String valProcessCnt;

    @Column(name = "val_error_cnt")
    private String valErrorCnt;

    @Column(name = "upload_rspns_ts")
    private String uploadRspnsTs;

    @Column(name = "upload_process_cnt")
    private String uploadProcessCnt;

    @Column(name = "upload_error_cnt")
    private String uploadErrorCnt;

    @Column(name = "status_cd ")
    private String statusCd;

}
