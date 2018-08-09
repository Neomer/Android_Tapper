package my.neomer.tapper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicService extends Service {

    MediaPlayer player;

    @Override
    public void onStart(Intent intent, int startId) {
        player.start();
    }

    @Override
    public void onDestroy() {
        player.stop();
    }

    @Override
    public void onCreate() {

        try {
            DataInputStream is = new DataInputStream(getAssets().open("melody/theme.mp3"));
        }
        catch (IOException ex) {}
        player = MediaPlayer.create(this, R.raw.theme);
        player.setLooping(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
