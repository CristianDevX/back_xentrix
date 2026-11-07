package com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aplications_logs")
public class AplicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_")
    private String code;
    @Column(name = "action_")
    private String action;
    @Column(name = "message_")
    private String message;
    private String payload;
    @Column(name = "class_path")
    private String classPath;
    @Column(name = "user_id")
    private Long userId;
    private String ip;
    private String url;
    private String method;
    private String description;
    @Column(name = "system_id")
    private Long systemId;
    private String endpoint;

    public AplicationLog() {
    }

    public AplicationLog(Long id, String code, String action, String message, String payload, String classPath,
            Long userId, String ip, String url, String method, String description, Long systemId, String endpoint) {
        this.id = id;
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
        this.systemId = systemId;
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "AplicationLog [id=" + id + ", code=" + code + ", action=" + action + ", message=" + message
                + ", payload=" + payload + ", classPath=" + classPath + ", userId=" + userId + ", ip=" + ip + ", url="
                + url + ", method=" + method + ", description=" + description + ", systemId=" + systemId + ", endpoint="
                + endpoint + "]";
    }

}
