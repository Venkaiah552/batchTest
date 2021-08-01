package com.cvs.cdc.repo;

import com.cvs.cdc.model.CdcResponseToDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CdcRespRepo extends JpaRepository<CdcResponseToDb, String> {

    Optional<CdcResponseToDb> findByFileNmAndExtrDtAndJobNm(String filename, String extractDate, String jobName);

}
