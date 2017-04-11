package cn.hawk.commonproject.models;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class BaseJsonBean<T> {
    String code;
    String message;
    T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
}
