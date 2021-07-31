package com.cvs.cdc.model;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Table(name="IDW_APPS_STG_VW.CDC_FILE_PROCESS")
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
public class CdcFileProcess  implements Serializable {



    /*@EmbeddedId
    private CompositeKey cdcResponseKey;*/
    @Id
    @Column(name="file_name")
    private String fileName;

    //The below is used only when reading from db is done.
   /* @Column(name="RXC_IMM_ID")
    @Id
    private String rxcImmId;*/

    @Column(name="EXTR_DT")
    private String extrDt;

    @Column(name="JOB_NM")
    @Id
    private String jobNm;

    @Column(name="file_rec_cnt ")
    private String fileRecCnt;

    @Column(name="file_crte_ts ")
    private  String file_create_ts;

    @Column(name="app_init_ts")
    private String appInitTs;

    @Column(name="val_rspns_ts")
    private String valRspnsTs;

    @Column(name="val_process_cnt")
    private String valProcessCnt;

    @Column(name="val_error_cnt")
    private String valErrorCnt;

    @Column(name="upload_rspns_ts")
    private String uploadRspnsTs;

  /*  @Column(name="recip_sex")
    private Character recipSex;*/

    @Column(name="upload_process_cnt")
    private String uploadProcessCnt;

    @Column(name="upload_error_cnt")
    private String uploadErrorCnt;

    @Column(name="status_cd ")
    private String statusCd;

}
