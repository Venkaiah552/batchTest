package com.cvs.cdc.repo;

import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseToDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmunizationInfoRepo extends PagingAndSortingRepository<CdcRequestToApi,String> {
   /* @Modifying
    @Query("update User u set u.active = false where u.lastLoginDate < :date")
    void deactivateUsersNotLoggedInSince(@Param("date") String date);*/
//limit 10
    @Query(nativeQuery = true, value = "select * from immunization_info ")
    List<CdcRequestToApi> getAll();
}