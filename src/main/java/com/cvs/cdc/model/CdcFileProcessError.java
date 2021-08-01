package com.cvs.cdc.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Table(name = "CDC_FILE_PROCESS_ERROR")
public class CdcFileProcessError implements Serializable {


    @Column(name = "file_nm")
    @Id
    private String fileName;

    @Column(name = "error_typ")
    private String errorType;

    @Column(name = "error_ts")
    private Instant errorTs;

    @Column(name = "recip_id")
    private String recipId;

    @Column(name = "dch_rspns_id ")
    private String dchRspnsId;

    @Column(name = "vacc_evnt_cd ")
    private String vaccEvntId;

    @Column(name = "error_msg")
    private String errorMsg;


}
