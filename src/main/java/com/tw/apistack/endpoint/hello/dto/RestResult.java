package com.tw.apistack.endpoint.hello.dto;

public class RestResult {
    private String error;
    private String msg;
    private Object data;

    public String getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public RestResult(String error, String msg, Object data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestResult that = (RestResult) o;

        if (error != null ? !error.equals(that.error) : that.error != null) {
            return false;
        }
        if (msg != null ? !msg.equals(that.msg) : that.msg != null) {
            return false;
        }
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = error != null ? error.hashCode() : 0;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
