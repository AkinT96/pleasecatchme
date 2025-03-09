// PlayerDrawing.java – Verwaltet Spiel-Logik und Rendering der Spielwelt und Spieler
package io.github.pleasecatchme.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.HashMap;
import java.util.Map;

public class PlayerDrawing {
    private SpriteBatch batch;
    private Texture floorTexture;
    private Texture playerTexture;
    private Map<String, Player> players;  // Alle Spieler im Spiel (Key z.B. Spieler-ID)
    private float speed = 300f;           // Bewegungsgeschwindigkeit der Spieler

    public PlayerDrawing(SpriteBatch batch) {
        this.batch = batch;
        // Texturen laden
        floorTexture = new Texture("floor.png");
        playerTexture = new Texture("manbluehold.png");
        // Spieler-Map initialisieren und lokalen Spieler anlegen
        players = new HashMap<>();
        // Startposition des lokalen Spielers (z.B. untere rechte Ecke)
        float startX = 900, startY = 105;
        Player localPlayer = new Player("local", startX, startY, playerTexture);
        players.put(localPlayer.id, localPlayer);
    }

    public void draw() {
        // Bildschirm mit Hintergrundfarbe leeren
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        // Eingaben verarbeiten und Spielerposition aktualisieren
        handleInput(Gdx.graphics.getDeltaTime());
        // Hintergrund (Floor) auf gesamte Bildschirmgröße zeichnen
        batch.draw(floorTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Alle Spieler zeichnen
        for (Player player : players.values()) {
            player.draw(batch);
        }
        batch.end();
    }

    private void handleInput(float deltaTime) {
        // Bewegen des lokalen Spielers entsprechend Touch-Eingabe
        Player local = players.get("local");
        if (local != null && Gdx.input.isTouched()) {
            // Touch-Koordinaten erfassen (y umrechnen, da LibGDX y=0 am unteren Bildschirmrand hat)
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            // Differenz zwischen Spielerposition und Touchpunkt
            float dx = touchX - (local.x + local.getWidth()/2f);
            float dy = touchY - (local.y + local.getHeight()/2f);
            // Rotation der Figur in Richtung Touchpunkt setzen (Bogenmaß -> Grad umgerechnet)
            local.rotation = (float)Math.toDegrees(Math.atan2(dy, dx)) - 10f;
            // Entfernung zum Touchpunkt berechnen
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            if (distance > 5f) {  // nur bewegen, wenn Zielpunkt nicht beinahe erreicht
                // Normierte Bewegungsrichtung berechnen
                float nx = dx / distance;
                float ny = dy / distance;
                // Position des Spielers um Schritt in Richtung Touchpunkt verschieben
                local.x += nx * speed * deltaTime;
                local.y += ny * speed * deltaTime;
            }
        }
    }

    public void dispose() {
        // Alle geladenen Ressourcen freigeben
        floorTexture.dispose();
        playerTexture.dispose();
        // (SpriteBatch wird im Main.dispose freigegeben)
    }

    // Möglichkeit: Weitere Methoden, z.B. um neue Spieler hinzuzufügen oder Positionen von extern zu aktualisieren.
}
