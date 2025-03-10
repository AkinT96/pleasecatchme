package io.github.pleasecatchme.android;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.pleasecatchme.Main;
import io.github.pleasecatchme.android.firebaseAuth.Auth;

public class AndroidLauncher extends AndroidApplication {
    private DatabaseReference database;
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth.authenticateUser();
        startGame();



        // Nutzer authentifizieren, bevor das Spiel gestartet wird
    }



    private void startGame() {
        runOnUiThread(() -> {
            AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
            configuration.useImmersiveMode = true;
            initialize(new Main(), configuration);
        });
    }
}
