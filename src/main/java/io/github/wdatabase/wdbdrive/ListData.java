package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;

public class ListData {
    private long total;
    private ArrayList<String> list;

    public void setTotal(long total){
        this.total = total;
    }

    public long getTotal(){
        return total;
    }

    public void setList(ArrayList<String> list){
        this.list = list;
    }

    public ArrayList<String> getList(){
        return list;
    }
}
