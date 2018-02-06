package com.wusir.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zy on 2018/2/6.
 */
@DatabaseTable(tableName = "game_table")
public class Game {
    @DatabaseField(generatedId = true)
    private int _id;
    @DatabaseField(columnName = "gameName")
    private String gameName;
    @DatabaseField(columnName = "gameType")
    private String gameType;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
