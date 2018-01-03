package com.wusir.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 用户
 */
@Entity
public class User {
    @Id
    public long mId; // id
    @Property(nameInDb = "mName")
    public String mName; // 用户名

    @Generated(hash = 2010336562)
    public User(long mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    @Generated(hash = 586692638)
    public User() {
    }

//    @Generated(hash = 586692638)
//    public User() {
//    }
//    @Generated(hash = 1144922831)
//    public User(long mId, String mName) {
//        this.mId = mId;
//        this.mName = mName;
//    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getMId() {
        return this.mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
    }

    public String getMName() {
        return this.mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }
}
