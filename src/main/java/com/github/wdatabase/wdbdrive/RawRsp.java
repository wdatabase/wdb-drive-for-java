package com.github.wdatabase.wdbdrive;


public class RawRsp {
    private long code;
    private String msg;
    private long size;
    private byte[] raw;

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    public byte[] getRaw() {
        return raw;
    }
}
