package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class PutIndexReq {
    private ArrayList<String> indexkey;
    private String key;
    private ArrayList<String> indexraw;
    private long time;
    private String sg;

    public void setIndexkey(ArrayList<String> indexkey){
        this.indexkey = indexkey;
    }

    public ArrayList<String> getIndexkey(){
        return indexkey;
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
