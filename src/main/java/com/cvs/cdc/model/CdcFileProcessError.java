package com.cvs.cdc.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Table(name="IDW_APPS_STG_VW.CDC_FILE_PROCESS_ERROR")
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
public class CdcFileProcessError {



    /*@EmbeddedId
    private CompositeKey cdcResponseKey;*/
    @Column(name="file_nm")
    @Id
    private String fileName;

    //The below is used only when reading from db is done.
   /* @Column(name="RXC_IMM_ID")
    @Id
    private String rxcImmId;*/

    @Column(name="error_typ")
    private String errorType;

    @Column(name="error_ts")
    private String errorTs;

    @Column(name="dch_rspns_id ")
    private String dchRspnsId;

    @Column(name="vacc_evnt_cd ")
    private  String vaccEvntId;

    @Column(name="error_msg")
    private String errorMsg;



}
