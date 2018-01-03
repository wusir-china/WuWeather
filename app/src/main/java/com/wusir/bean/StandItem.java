package com.wusir.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class StandItem implements Serializable{
    private String icon;
    private String gameName;
    private String type;
    private String gameDes;
    private String downurl;
    private int id;
    private int place;
    private int size;
    private int downNum;

    public StandItem(String icon, String gameName, String type, String gameDes, String downurl, int id, int place, int size, int downNum) {
        this.icon = icon;
        this.gameName = gameName;
        this.type = type;
        this.gameDes = gameDes;
        this.downurl = downurl;
        this.id = id;
        this.place = place;
        this.size = size;
        this.downNum = downNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGameDes() {
        return gameDes;
    }

    public void setGameDes(String gameDes) {
        this.gameDes = gameDes;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDownNum() {
        return downNum;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }
}
