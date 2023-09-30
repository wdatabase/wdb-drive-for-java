package com.github.wdatabase.wdbdrive;

public class ApiRsp {
    private long code;
    private String msg;
    private String data;

    public void setCode(long code){
        this.code = code;
    }

    public long getCode(){
        return code;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }
}
