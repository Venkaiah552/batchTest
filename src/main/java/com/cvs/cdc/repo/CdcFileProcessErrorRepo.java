package com.cvs.cdc.repo;

import com.cvs.cdc.model.CdcFileProcessError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdcFileProcessErrorRepo extends JpaRepository<CdcFileProcessError, String> {
}
