// Main.java – Hauptklasse des Spiels
package io.github.pleasecatchme;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pleasecatchme.player.PlayerDrawing;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerDrawing playerDrawingUnit;

    @Override
    public void create() {
        batch = new SpriteBatch();
        playerDrawingUnit = new PlayerDrawing(batch);
    }

    @Override
    public void render() {
        // Delegiere an PlayerDrawing zum Aktualisieren und Zeichnen
        playerDrawingUnit.draw();
    }

    @Override
    public void dispose() {
        // Ressourcen freigeben
        playerDrawingUnit.dispose();
        batch.dispose();
    }
}
