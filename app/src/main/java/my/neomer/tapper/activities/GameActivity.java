package my.neomer.tapper.activities;

import android.content.Intent;
import android.os.Bundle;

import my.neomer.tapper.BaseGameActivity;
import my.neomer.tapper.GameResults;
import my.neomer.tapper.GameSurface;
import my.neomer.tapper.MusicService;
import my.neomer.tapper.OnGameOverListener;
import my.neomer.tapper.viewitems.ButtonViewItem;
import my.neomer.tapper.viewitems.IViewItem;
import my.neomer.tapper.viewitems.OnViewItemEventListener;

public class GameActivity extends BaseGameActivity {

    private GameSurface gameSurface;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindowSettings();

        gameSurface = new GameSurface(this, getAssets());

        gameSurface.setOnGameOverLisener(new OnGameOverListener() {
            @Override
            public void OnGameOver(GameResults results) {
                ShowResults(results);
            }
        });

        setContentView(gameSurface);

        startService(new Intent(this, MusicService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void ShowResults(GameResults results) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("game_results", results);

        startActivity(intent);
        finish();
    }


}