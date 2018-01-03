package com.wusir.bean;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class DanMuBean {
    private String text;
    private long time;
    private String news_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
