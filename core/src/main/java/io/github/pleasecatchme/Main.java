package io.github.pleasecatchme;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture floor;
    private Texture playerTexture;
    private TextureRegion player; // Ermöglicht Rotation

    private float playerX = 900;  // Startposition X
    private float playerY = 105;  // Startposition Y
    private float speed = 300;    // Geschwindigkeit pro Sekunde
    private float rotation = 0;   // Rotationswinkel

    @Override
    public void create() {
        batch = new SpriteBatch();
        floor = new Texture("floor.png");
        playerTexture = new Texture("manbluehold.png");
        player = new TextureRegion(playerTexture); // Ermöglicht Rotation der Texture
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Touch-Input verarbeiten
        handleInput(Gdx.graphics.getDeltaTime());

        batch.begin();
        batch.draw(floor, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Zeichne Spieler mit Rotation
        batch.draw(
            player,
            playerX, playerY,
            player.getRegionWidth() / 2f, player.getRegionHeight() / 2f, // Mittelpunkt der Rotation
            player.getRegionWidth(), player.getRegionHeight(), // Originalgröße
            1, 1, // Keine Skalierung
            rotation // Rotationswinkel
        );

        batch.end();
    }

    private void handleInput(float deltaTime) {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Y umdrehen

            // **1. Rotationswinkel berechnen**
            float dx = touchX - (playerX + player.getRegionWidth() / 2f);
            float dy = touchY - (playerY + player.getRegionHeight() / 2f);
            rotation = (float) Math.toDegrees(Math.atan2(dy, dx)) - 10; // -90° um Sprite korrekt auszurichten

            // **2. Sanfte Bewegung zur Touch-Position**
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance > 5) { // Nur bewegen, wenn die Distanz groß genug ist
                playerX += (dx / distance) * speed * deltaTime;
                playerY += (dy / distance) * speed * deltaTime;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        floor.dispose();
        playerTexture.dispose();
    }
}
