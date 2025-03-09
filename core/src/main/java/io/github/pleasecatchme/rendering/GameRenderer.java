package io.github.pleasecatchme.rendering;
// GameRenderer.java – Eine separate Klasse für das Rendering
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pleasecatchme.player.PlayerDrawing;


public class GameRenderer {
    private SpriteBatch batch;
    private PlayerDrawing playerDrawingUnit;



    public GameRenderer() {
        batch = new SpriteBatch();
        playerDrawingUnit = new PlayerDrawing(batch);
    }

    public void render() {
        playerDrawingUnit.draw();
    }

    public void dispose() {
        playerDrawingUnit.dispose();
        batch.dispose();
    }
}

