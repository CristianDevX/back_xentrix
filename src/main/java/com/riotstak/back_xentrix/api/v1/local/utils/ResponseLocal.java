package com.riotstak.back_xentrix.api.v1.local.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.AplicationLogService;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.AplicationLogThread;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.payloads.AplicationLogDto;

public class ResponseLocal {

    public boolean success;
    public Integer code;
    public Object data;
    public String message;
    public String description;

    private String action;
    private AplicationLogService logService;

    public ResponseLocal() {
        this.success = true;
        this.code = 200;
    }

    public ResponseLocal(AplicationLogService logService, String action) {
        this.success = true;
        this.code = 200;
        this.logService = logService;
        this.action = action;
        // this.endpoint = endpoint;
    }

    public HttpStatus validateService(
            Object data,
            String message,
            String class_path,
            String payload,
            HttpServletRequest req) {

        if (UtilsService.isErrorService(data)) {
            String message_ = ((ErrorService) data).getMessage();
            String description_ = ((ErrorService) data).getDescription();
            String class_path_ = ((ErrorService) data).getClass_path();
            int code_ = ((ErrorService) data).getCode();
            this.setError(code_,
                    message_,
                    description_,
                    new ArrayList<ObjectError>(),
                    class_path_,
                    payload,
                    req);

            HttpStatus httpStatus = this.getHttpStatus(code_);
            return httpStatus;
        } else {
            this.success = true;
            this.code = 200;
            this.message = message;
            this.data = data;
            this.setSuccess(
                    class_path,
                    payload,
                    req);
            return HttpStatus.OK;
        }
    }

    public void setSuccess(
            String class_path,
            String payload,
            HttpServletRequest req) {
        String ip = UtilsLocal.getIpAddr(req);
        String url = this.getUrl(req);
        String method = req.getMethod();

        if (this.logService != null) {
            AplicationLogDto log = new AplicationLogDto();
            log.setCode(String.valueOf(this.code));
            log.setAction(action);
            log.setMessage(message);
            log.setPayload(payload);
            log.setClassPath(class_path);
            log.setIp(ip);
            log.setUrl(url);
            log.setMethod(method);
            log.setDescription(this.description);

            // // Asincrona
            AplicationLogThread logThread = new AplicationLogThread(this.logService, log);
            logThread.start();
        }
    }

    public void setError(
            Integer code,
            String message,
            String description,
            List<ObjectError> listErrors,
            String class_path,
            String payload,
            HttpServletRequest req) {
        this.success = false;
        if (code == null)
            this.code = 500;
        else
            this.code = code;

        if (listErrors.size() != 0) {
            StringBuilder messages_ = new StringBuilder();
            messages_.append("Lista_errores:");
            listErrors.forEach(err -> messages_.append("- " + err.getDefaultMessage()));
            this.message = messages_.toString();
        } else {
            this.message = message;
        }

        if (description == null || description.equals("")) {
            description = this.message;
        }
        this.description = description;

        String ip = UtilsLocal.getIpAddr(req);
        String url = this.getUrl(req);
        String method = req.getMethod();

        if (this.logService != null) {
            AplicationLogDto log = new AplicationLogDto();
            log.setCode(String.valueOf(this.code));
            log.setAction(action);
            log.setMessage(message);
            log.setPayload(payload);
            log.setClassPath(class_path);
            log.setIp(ip);
            log.setUrl(url);
            log.setMethod(method);
            log.setDescription(this.description);

            // // Asincrona
            AplicationLogThread logThread = new AplicationLogThread(this.logService, log);
            logThread.start();
        }
    }

    public String getIpAddr(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    private String getUrl(HttpServletRequest req) {
        String queryString = (req.getQueryString() != null) ? "?" + req.getQueryString() : "";
        return req.getRequestURL().toString() + queryString;
    }

    private HttpStatus getHttpStatus(int code) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (code) {
            case 200:
                httpStatus = HttpStatus.OK;
                break;
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case 401:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case 403:
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case 404:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case 405:
                httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                break;
            case 406:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
            case 409:
                httpStatus = HttpStatus.CONFLICT;
                break;
            case 408:
                httpStatus = HttpStatus.REQUEST_TIMEOUT;
                break;
            case 501:
                httpStatus = HttpStatus.NOT_IMPLEMENTED;
                break;
            case 502:
                httpStatus = HttpStatus.BAD_GATEWAY;
                break;
            case 503:
                httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case 504:
                httpStatus = HttpStatus.GATEWAY_TIMEOUT;
                break;
        }
        return httpStatus;
    }
}
