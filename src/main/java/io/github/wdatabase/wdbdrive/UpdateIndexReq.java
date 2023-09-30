package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class UpdateIndexReq {
    private ArrayList<String> oindexkey;
    private ArrayList<String> cindexkey;
    private String key;
    private ArrayList<String> indexraw;
    private long time;
    private String sg;

    public void setOindexkey(ArrayList<String> oindexkey){
        this.oindexkey = oindexkey;
    }

    public ArrayList<String> getOindexkey(){
        return oindexkey;
    }

    public void setCindexkey(ArrayList<String> cindexkey){
        this.cindexkey = cindexkey;
    }

    public ArrayList<String> getCindexkey(){
        return cindexkey;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setIndexraw(ArrayList<String> indexraw){
        this.indexraw = indexraw;
    }

    public ArrayList<String> getIndexraw(){
        return indexraw;
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
