package edu.calvin.cs262.cmd38;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_PLAYER_ACTIVITY_REQUEST_CODE = 1;
    private PlayerViewModel mPlayerViewModel;
    private MonopolyRoomDatabase mMonopolyRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPlayerActivity.class);
                startActivityForResult(intent, NEW_PLAYER_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlayerListAdapter adapter = new PlayerListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mPlayerViewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable final List<Player> players) {
                // Update the cached copy of the players in the adapter.
                adapter.setPlayers(players);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Player myPlayer = adapter.getPlayerAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myPlayer.getName(), Toast.LENGTH_LONG).show();

                        // Delete the player
                        mPlayerViewModel.deletePlayer(myPlayer);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        mMonopolyRoomDatabase = MonopolyRoomDatabase.getDatabase(getApplication());

        // Log Players
        mMonopolyRoomDatabase.playerDao().getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable final List<Player> players) {
                if (players != null) {
                    for (int i = 0; i < players.size(); i++) {
                        Log.d("Player.id", Integer.toString(players.get(i).getId()));
                        if (players.get(i).getName() != null) {
                            Log.d("Player.name", players.get(i).getName());
                        }
                        Log.d("Player.emailAddress", players.get(i).getEmail());
                    }
                }
            }
        });

        // Log Games
        mMonopolyRoomDatabase.gameDao().getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable final List<Game> games) {
                if (games != null) {
                    for (int i = 0; i < games.size(); i++) {
                        Log.d("Game.id", Integer.toString(games.get(i).getId()));
                        Log.d("Game.time", games.get(i).getTime());
                    }
                }
            }
        });

        // Log PlayerGames
        mMonopolyRoomDatabase.playerGameDao().getAllPlayerGames().observe(this, new Observer<List<PlayerGame>>() {
            @Override
            public void onChanged(@Nullable final List<PlayerGame> playerGames) {
                if (playerGames != null) {
                    for (int i = 0; i < playerGames.size(); i++) {
                        Log.d("PlayerGame.playerId", Integer.toString(playerGames.get(i).getPlayerID()));
                        Log.d("PlayerGame.gameId", Integer.toString(playerGames.get(i).getGameID()));
                        Log.d("PlayerGame.score", Integer.toString(playerGames.get(i).getScore()));
                    }
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String email = null;
        String name = null;
        if (requestCode == NEW_PLAYER_ACTIVITY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            email = data.getStringExtra(NewPlayerActivity.EXTRA_EMAIL_REPLY);
            name = data.getStringExtra(NewPlayerActivity.EXTRA_NAME_REPLY);
        }
        if (email != null) {
            Player player = new Player(0, name, email);
            mPlayerViewModel.insert(player);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mPlayerViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
