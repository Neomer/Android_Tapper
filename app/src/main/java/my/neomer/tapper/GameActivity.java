package my.neomer.tapper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindowSettings();

        renderer = new Renderer(this, getAssets());

        renderer.setOnGameOverLisener(new OnGameOverListener() {
            @Override
            public void OnGameOver(GameResults results) {
                ShowResults(results);
            }
        });

        setContentView(renderer);
    }

    private void ShowResults(GameResults results) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("game_results", results);

        startActivity(intent);
    }


    private void setupWindowSettings() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


}