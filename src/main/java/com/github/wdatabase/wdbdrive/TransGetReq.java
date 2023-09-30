package com.github.wdatabase.wdbdrive;

public class TransGetReq {
    private String tsid;
    private String key;
    private long time;
    private String sg;

    public void setTsid(String tsid){
        this.tsid = tsid;
    }

    public String getTsid(){
        return tsid;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }

    public void setSg(String sg){
        this.sg = sg;
    }

    public String getSg(){
        return sg;
    }
}
