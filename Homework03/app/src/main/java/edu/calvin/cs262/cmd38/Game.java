package edu.calvin.cs262.cmd38;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_table")
public class Game {

    // Help for autogen from https://stackoverflow.com/questions/44109700/how-to-make-primary-key-as-autoincrement-for-room-persistence-lib
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "time")
    private String mTime;

    public Game(int id, String time) {
        this.mId = id;
        this.mTime = time;
    }

    public int getId(){return this.mId;}
    public String getTime(){return this.mTime;}
}
