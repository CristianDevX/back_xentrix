package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1.AplicationLog;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1.AplicationLogRepository;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class AplicationLogAdapter {

    @Autowired
    AplicationLogRepository aplicationLogRepository;

    private final String myClassName = AplicationLogAdapter.class.getName();

    public Object registerLog(AplicationLog apilogs) {
        try {
            return aplicationLogRepository.save(apilogs);
        } catch (Exception e) {
            return new ErrorService(
                    "Error registrando log en BD",
                    e.getMessage(),
                    this.myClassName,
                    500);
        }
    }
}
