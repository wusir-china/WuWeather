package com.wusir.bean;

/**
 * Created by zy on 2017/5/25.
 */

public class Weather {
    private String time;
    private String status;
    private String tmp;

    public Weather(String time, String status, String tmp) {
        this.time = time;
        this.status = status;
        this.tmp = tmp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
