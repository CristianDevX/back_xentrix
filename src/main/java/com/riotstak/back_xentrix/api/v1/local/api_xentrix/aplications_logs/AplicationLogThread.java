package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.payloads.AplicationLogDto;

public class AplicationLogThread extends Thread {

    private AplicationLogDto apiLogsDto;
    private AplicationLogService apiLogsService;

    public AplicationLogThread(AplicationLogService apiLogsService, AplicationLogDto apiLogsDto) {
        this.apiLogsService = apiLogsService;
        this.apiLogsDto = apiLogsDto;
    }

    @Override
    public void run() {
        try {
            Object response = apiLogsService.registerLogDb(apiLogsDto);
        } catch (Exception e) {
        }
    }

}
