package io.github.wdatabase.wdbdrive;


public class ListIndexReq {
    private String indexkey;
    private String conditon;
    private long offset;
    private long limit;
    private String order;
    private long time;
    private String sg;

    public void setCondition(String conditon){
        this.conditon = conditon;
    }

    public String getCondition(){
        return conditon;
    }

    public void setIndexkey(String indexkey){
        this.indexkey = indexkey;
    }

    public String getIndexkey(){
        return indexkey;
    }

    public void setOffset(long offset){
        this.offset = offset;
    }

    public long getOffset(){
        return offset;
    }

    public void setLimit(long limit){
        this.limit = limit;
    }

    public long getLimit(){
        return limit;
    }

    public void setOrder(String order){
        this.order = order;
    }

    public String getOrder(){
        return order;
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
