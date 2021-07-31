package com.cvs.cdc.repo;

import com.cvs.cdc.model.CdcRequestToApi;
//import com.cvs.cdc.model.CdcResponseToDb;
import com.cvs.cdc.model.CompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmunizationInfoRepo extends JpaRepository<CdcRequestToApi, CompositeKey>/*PagingAndSortingRepository<CdcRequestToApi,String>*/ {
   /* @Modifying
    @Query("update User u set u.active = false where u.lastLoginDate < :date")
    void deactivateUsersNotLoggedInSince(@Param("date") String date);*/
//limit 10
    @Query(nativeQuery = true, value = "select * from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT")
    List<CdcRequestToApi> getAll();
}