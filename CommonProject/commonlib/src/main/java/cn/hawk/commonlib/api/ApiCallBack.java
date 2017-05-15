package cn.hawk.commonlib.api;

/**
 * Created by kehaowei on 2017/5/15.
 */

public abstract class ApiCallBack<T> {
    public abstract void onSuccess(T data);

    public void onStart() {
    }

    public abstract void onTokenError(int code, String message);

    public abstract void onFailure(int code, String message);

    public void onCompleted() {
    }
}
