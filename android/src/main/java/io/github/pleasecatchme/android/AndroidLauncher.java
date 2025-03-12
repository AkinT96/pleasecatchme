package io.github.pleasecatchme.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import io.github.pleasecatchme.Main; // Dein Core-Spielcode!

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Main(), config); // 🕹️ Starte dein Spiel!
    }
}
