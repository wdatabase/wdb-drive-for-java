package com.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class TransPutDataReq {
    private String tsid;
    private String key;
    private ArrayList<String> categories;
    private String content;
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

    public void setCategories(ArrayList<String> categories){
        this.categories = categories;
    }

    public ArrayList<String> getCategories(){
        return categories;
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
