package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class UploadReq {
    private String path;
    private String key;
    private ArrayList<String> categories;
    private long time;
    private String sg;

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

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
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
