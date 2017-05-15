package cn.hawk.commonlib.api;

/**
 * Created by kehaowei on 2017/5/15.
 */

public class ResponseBean<T> {
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_TOKEN_ERROR = 110;

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    public boolean isTokenError() {
        return code == CODE_TOKEN_ERROR;
    }
}
