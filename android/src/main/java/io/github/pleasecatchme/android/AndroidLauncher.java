package io.github.pleasecatchme.android;
import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import io.github.pleasecatchme.android.startscreen.LandingPage;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starte die LandingPage für Authentifizierung
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
        finish(); // Beende AndroidLauncher, da er erst nach der Authentifizierung gebraucht wird
    }
}
