package my.neomer.tapper;

import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends BaseGameActivity {

    private GameSurface gameSurface;

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
    }

    private void ShowResults(GameResults results) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("game_results", results);

        startActivity(intent);
        finish();
    }


}