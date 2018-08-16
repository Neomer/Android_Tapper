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
import android.widget.TextView;

public class ResultsActivity extends BaseGameActivity {

    private TextView tvTotalTime;
    private Button btnToMainMenu;
    private TextView labelTotalTime;
    private  TextView labelGameResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindowSettings();
        setContentView(R.layout.activity_results);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#000000"));

        loadActions();

        btnToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainMenu();
            }
        });

        Intent intent = getIntent();
        if (intent != null)
        {
            GameResults gameResults = (GameResults) intent.getParcelableExtra("game_results");
            if (gameResults != null)
            {
                tvTotalTime.setText(TimeHelper.StringTimeFromMilliseconds(gameResults.getTotalTime()));
            }
        }
    }

    private void loadActions() {
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        btnToMainMenu = (Button) findViewById(R.id.btnToMainMenu);
        labelTotalTime = (TextView) findViewById(R.id.labelTotalTime);
        labelGameResults = (TextView) findViewById(R.id.labelGameResults);

        Typeface mainFont = null;
        try
        {
            mainFont = Typeface.createFromAsset(getAssets(), "fonts/main-font.otf");
        }
        catch (Exception ex) {}

        if (mainFont != null)
        {
            btnToMainMenu.setTypeface(mainFont);
            labelTotalTime.setTypeface(mainFont);
            labelGameResults.setTypeface(mainFont);
            tvTotalTime.setTypeface(mainFont);
        }
    }

    @Override
    public void onBackPressed() {
        toMainMenu();
    }

    private void toMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
