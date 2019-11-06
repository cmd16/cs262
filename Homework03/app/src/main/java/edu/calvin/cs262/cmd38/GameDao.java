package edu.calvin.cs262.cmd38;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Game game);

    @Query("DELETE FROM game_table")
    void deleteAll();

    @Query("SELECT * from game_table ORDER BY time DESC")
    LiveData<List<Game>> getAllGames();

    @Query("SELECT * from game_table LIMIT 1")
    Game[] getAnyGame();

    @Delete
    void deleteGame(Game game);
}
