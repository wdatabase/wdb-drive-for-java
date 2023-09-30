package com.github.wdatabase.wdbdrive;

public class UpdateDataReq {
    private String key;
    private String content;
    private long time;
    private String sg;

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
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
