package edu.calvin.cs262.cmd38;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerGameRepository {
    private PlayerGameDao mPlayerGameDao;
    private LiveData<List<PlayerGame>> mAllPlayerGames;

    PlayerGameRepository(Application application) {
        MonopolyRoomDatabase db = MonopolyRoomDatabase.getDatabase(application);
        mPlayerGameDao = db.playerGameDao();
        mAllPlayerGames = mPlayerGameDao.getAllPlayerGames();
    }

    LiveData<List<PlayerGame>> getAllPlayerGames() {
        return mAllPlayerGames;
    }

    public void insert (PlayerGame playerGame) {
        new insertAsyncTask(mPlayerGameDao).execute(playerGame);
    }

    private static class insertAsyncTask extends AsyncTask<PlayerGame, Void, Void> {

        private PlayerGameDao mAsyncTaskDao;

        insertAsyncTask(PlayerGameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PlayerGame... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllPlayerGamesAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlayerGameDao mAsyncTaskDao;

        deleteAllPlayerGamesAsyncTask(PlayerGameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllPlayerGamesAsyncTask(mPlayerGameDao).execute();
    }

    private static class deletePlayerGameAsyncTask extends AsyncTask<PlayerGame, Void, Void> {
        private PlayerGameDao mAsyncTaskDao;

        deletePlayerGameAsyncTask(PlayerGameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PlayerGame... params) {
            mAsyncTaskDao.deletePlayerGame(params[0]);
            return null;
        }
    }

    public void deletePlayerGame(PlayerGame playerGame) {
        new deletePlayerGameAsyncTask(mPlayerGameDao).execute(playerGame);
    }
}
