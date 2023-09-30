package io.github.wdatabase.wdbdrive;

public class TransIdReq {
    private String tsid;
    private long time;
    private String sg;

    public void setTsid(String tsid){
        this.tsid = tsid;
    }

    public String getTsid(){
        return tsid;
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
