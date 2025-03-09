// Player.java – Repräsentiert einen Spieler (Spielfigur) mit Position, Rotation, etc.
package io.github.pleasecatchme.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    public String id;             // Eindeutige Spieler-ID (z.B. Firebase-UID oder "local")
    public float x, y;            // Aktuelle Position der Spielfigur
    public float rotation;        // Aktuelle Drehung der Spielfigur (in Grad)
    private TextureRegion sprite; // Darstellung der Spielfigur

    public Player(String id, float x, float y, Texture texture) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rotation = 0f;
        this.sprite = new TextureRegion(texture);
    }

    public float getWidth() {
        return sprite.getRegionWidth();
    }
    public float getHeight() {
        return sprite.getRegionHeight();
    }

    // Zeichnet den Spieler an seiner Position mit aktueller Rotation
    public void draw(SpriteBatch batch) {
        batch.draw(sprite,
            x, y,
            sprite.getRegionWidth() / 2f, sprite.getRegionHeight() / 2f, // Origin (Drehzentrum) in der Mitte
            sprite.getRegionWidth(), sprite.getRegionHeight(),
            1f, 1f,           // kein Skalieren
            rotation          // Drehwinkel
        );
    }
}
