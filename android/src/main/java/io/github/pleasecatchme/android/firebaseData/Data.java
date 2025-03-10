package io.github.pleasecatchme.android.firebaseData;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Data {
    private DatabaseReference database;


    private void saveUserData(FirebaseUser user) {
        database.child("users").child(user.getUid()).setValue("Neuer Nutzer")
            .addOnSuccessListener(aVoid -> Log.d("FirebaseDB", "✅ Nutzerdaten gespeichert"))
            .addOnFailureListener(e -> Log.e("FirebaseDB", "❌ Fehler beim Speichern: " + e.getMessage()));
    }
}
