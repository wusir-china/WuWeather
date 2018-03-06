package com.wusir.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy on 2017/5/25.
 */

public class Weather implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    public static final Parcelable.Creator<Weather> CREATOR=new Parcelable.Creator<Weather>(){

        @Override
        public Weather createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[0];
        }
    };
}
