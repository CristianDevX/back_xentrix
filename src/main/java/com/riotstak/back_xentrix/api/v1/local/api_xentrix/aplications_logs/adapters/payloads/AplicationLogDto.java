package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.payloads;

public class AplicationLogDto {

    private String code;
    private String action;
    private String message;
    private String payload;
    private String classPath;
    private Long userId;
    private String ip;
    private String url;
    private String method;
    private String description;
    private String endpoint;

    public AplicationLogDto() {
    }

    public AplicationLogDto(String code, String action, String message, String payload, String classPath, Long userId,
            String ip, String url, String method, String description, String endpoint) {
        this.code = code;
        this.action = action;
        this.message = message;
        this.payload = payload;
        this.classPath = classPath;
        this.userId = userId;
        this.ip = ip;
        this.url = url;
        this.method = method;
        this.description = description;
        this.endpoint = endpoint;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "AplicationLogDto [code=" + code + ", action=" + action + ", message=" + message + ", payload=" + payload
                + ", classPath=" + classPath + ", userId=" + userId + ", ip=" + ip + ", url=" + url + ", method="
                + method + ", description=" + description + ", endpoint=" + endpoint + "]";
    }

}
