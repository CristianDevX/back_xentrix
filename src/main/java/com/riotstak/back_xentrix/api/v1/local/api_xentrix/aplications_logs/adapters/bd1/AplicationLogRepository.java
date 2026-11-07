package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicationLogRepository extends JpaRepository<AplicationLog, Long> {

}