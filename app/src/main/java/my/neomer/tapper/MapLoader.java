package my.neomer.tapper;

import android.graphics.BitmapFactory;

public class MapLoader {

    static void LoadMapFromFile(GameSurface gameSurface, String filename) {
        Sprite sprite = new Sprite(BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.block_ground));
        sprite.setScale(0.4);
        double dx = sprite.GetWidth();

        for (int i = 0; i < 200; i++) {
            IActor groundBlock = new GroundBlock(new Coordinate(i * dx - i, 0), sprite, new Material());
            gameSurface.SpawnActor(groundBlock);
        }
    }

}