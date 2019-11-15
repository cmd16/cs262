package edu.calvin.cs262.cmd38;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "playergame_table")
public class PlayerGame {

    // Help for autogen from https://stackoverflow.com/questions/44109700/how-to-make-primary-key-as-autoincrement-for-room-persistence-lib
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "GameID")
    private int mGameID;
    @ForeignKey(entity = Player.class, parentColumns = "id", childColumns = "GameID")
    private int mPlayerID;

    @ColumnInfo(name = "score")
    private int mScore;

    public PlayerGame(int id, int gameID, int playerID, int score) {
        this.mId = id;
        this.mGameID = gameID;
        this.mPlayerID = playerID;
        this.mScore = score;
    }

    public int getId() {
        return this.mId;
    }

    public int getGameID() {
        return this.mGameID;
    }

    public int getPlayerID() {
        return this.mPlayerID;
    }

    public int getScore() {
        return this.mScore;
    }
}
