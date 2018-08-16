package my.neomer.tapper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends BaseGameActivity {

    private Button btnNewGame, btnSettings, btnExit;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, MusicService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindowSettings();

        setContentView(R.layout.activity_main);

        loadActions();

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#000000"));

        startService(new Intent(this, MusicService.class));
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadActions() {
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnExit = (Button) findViewById(R.id.btnExit);

        Typeface mainFont = null;
        try
        {
            mainFont = Typeface.createFromAsset(getAssets(), "fonts/main-font.otf");
        }
        catch (Exception ex) {}

        if (mainFont != null)
        {
            btnNewGame.setTypeface(mainFont);
            btnSettings.setTypeface(mainFont);
            btnExit.setTypeface(mainFont);
        }
    }
}
