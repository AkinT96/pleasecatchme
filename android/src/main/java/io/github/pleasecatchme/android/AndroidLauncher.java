package io.github.pleasecatchme.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.pleasecatchme.Main;
import android.util.Log;

public class AndroidLauncher extends AndroidApplication {
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.
        initialize(new Main(), configuration);

        // Firebase initialisieren
        database = FirebaseDatabase.getInstance("https://pleasecatchme-47488-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference();

        // Beispiel: Daten in die Datenbank schreiben
        writeTestData();
    }


    private void writeTestData() {
        database.child("test").setValue("Hello Firebase")
            .addOnSuccessListener(aVoid -> Log.d("FirebaseTest", "✅ Daten erfolgreich geschrieben!"))
            .addOnFailureListener(e -> Log.e("FirebaseTest", "❌ Fehler beim Schreiben: " + e.getMessage()));
    }

}
