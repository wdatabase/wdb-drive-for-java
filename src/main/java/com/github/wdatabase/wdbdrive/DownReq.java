package com.github.wdatabase.wdbdrive;

public class DownReq {
    private String path;
    private String key;
    private long time;
    private String sg;

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
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
