package edu.calvin.cs262.cmd38;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class Player {

    // Help for autogen from https://stackoverflow.com/questions/44109700/how-to-make-primary-key-as-autoincrement-for-room-persistence-lib
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @Nullable
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "email")
    private String mEmail;

    public Player(int id, @Nullable String name, @NonNull String email) {
        this.mId = id;
        this.mName = name;
        this.mEmail = email;
    }

    public int getId(){return this.mId;}
    public String getEmail(){return this.mEmail;}
    public String getName(){return this.mName;}
}
