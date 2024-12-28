package com.coderdot.response;

public class LogInResponse {
    String message;
    Boolean status;
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogInResponse(String message, Boolean status, Long id ) {
        this.message = message;
        this.status = status;
        this.id=id;

    }

    public LogInResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "LogInResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", status=" + id +
                '}';
    }
}
