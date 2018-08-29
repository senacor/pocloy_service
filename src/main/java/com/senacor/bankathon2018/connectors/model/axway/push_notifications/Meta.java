package com.senacor.bankathon2018.connectors.model.axway.push_notifications;

public class Meta {

    int code;
    String status;
    String method_name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", method_name='" + method_name + '\'' +
                '}';
    }
}
