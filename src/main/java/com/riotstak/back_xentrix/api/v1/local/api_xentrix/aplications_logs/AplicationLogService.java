package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.AplicationLogAdapter;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1.AplicationLog;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.payloads.AplicationLogDto;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class AplicationLogService {

    @Value("${system.id}")
    private String systemId;

    @Autowired
    AplicationLogAdapter aplicationLogAdapter;

    String myClassName = AplicationLogService.class.getName();

    public Object registerLogDb(AplicationLogDto apiLogsDto) {
        try {
            AplicationLog apiLogs = new AplicationLog();
            apiLogs.setCode(apiLogsDto.getCode());
            apiLogs.setAction(apiLogsDto.getAction());
            apiLogs.setMessage(apiLogsDto.getMessage());
            apiLogs.setPayload(apiLogsDto.getPayload());
            apiLogs.setClassPath(apiLogsDto.getClassPath());
            apiLogs.setUserId(apiLogsDto.getUserId());
            apiLogs.setIp(apiLogsDto.getIp());
            apiLogs.setUrl(apiLogsDto.getUrl());
            apiLogs.setMethod(apiLogsDto.getMethod());
            apiLogs.setDescription(apiLogsDto.getDescription());
            apiLogs.setSystemId(Long.parseLong(this.systemId));
            apiLogs.setEndpoint(apiLogsDto.getEndpoint());
            Object response = aplicationLogAdapter.registerLog(apiLogs);
            return response;
        } catch (Exception e) {
            return new ErrorService(
                    "Error registrando log en BD",
                    e.getMessage(),
                    this.myClassName,
                    500);
        }
    }

}
