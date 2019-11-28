package com.beijing.ruan.entity;

import java.io.Serializable;

public class ReturnObject implements Serializable {
    private static final long serialVersionUID = -7946233781886120441L;

    private Boolean success;

    private Object data;

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
