package com.wusir.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy on 2018/1/16.
 * aidl传递复杂数据类型按以下要求
 */

public class CountBean implements Parcelable{
    public int count;
    public static final Parcelable.Creator<CountBean> CREATOR=new Creator<CountBean>(){

        @Override
        public CountBean createFromParcel(Parcel source) {
            CountBean bean=new CountBean();
            bean.count=source.readInt();
            return bean;
        }

        @Override
        public CountBean[] newArray(int size) {
            return new CountBean[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
    }
}
