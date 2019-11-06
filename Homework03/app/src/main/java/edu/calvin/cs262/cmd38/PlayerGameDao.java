package edu.calvin.cs262.cmd38;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerGameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PlayerGame playerGame);

    @Query("DELETE FROM playergame_table")
    void deleteAll();

    @Query("SELECT * from playergame_table ORDER BY id DESC")
    LiveData<List<PlayerGame>> getAllPlayerGames();

    @Query("SELECT * from playergame_table LIMIT 1")
    PlayerGame[] getAnyPlayerGame();

    @Delete
    void deletePlayerGame(PlayerGame playerGame);
}
