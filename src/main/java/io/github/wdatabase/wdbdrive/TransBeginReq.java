package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class TransBeginReq {
    private ArrayList<String> keys;
    private long time;
    private String sg;

    public void setKeys(ArrayList<String> keys){
        this.keys = keys;
    }

    public ArrayList<String> getKeys(){
        return keys;
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
