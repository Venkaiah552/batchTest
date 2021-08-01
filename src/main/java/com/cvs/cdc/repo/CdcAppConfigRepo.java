package com.cvs.cdc.repo;

import com.cvs.cdc.model.CdcAppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdcAppConfigRepo extends JpaRepository<CdcAppConfig, String> {
}
