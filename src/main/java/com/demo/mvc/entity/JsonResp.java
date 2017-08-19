package com.demo.mvc.entity;

/**
 * Created by Administrator on 2017/8/13.
 */
public class JsonResp {
    private static final int SUCC = 1;
    private static final int FAIL = -1;

    private int status;
    private Object data;
    private String errMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public JsonResp(int status, Object data, String errMsg){
        this.status = status;
        this.data = data;
        this.errMsg = errMsg;
    }
    public JsonResp(int status, Object data){
        this.status = status;
        this.data = data;
    }
    public JsonResp(){}

    public static  JsonResp fail(String errMsg){
        return new JsonResp(FAIL, null,errMsg );
    }

    public static JsonResp succ(){
        return  new JsonResp(SUCC, null);
    }

    public static JsonResp succ(Object data){
        return new JsonResp(SUCC, data);
    }

}
