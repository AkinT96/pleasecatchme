package io.github.pleasecatchme.android.firebaseData;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Userdata {


    public void saveUserData(FirebaseUser user) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://pleasecatchme-47488-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference();

        database.child("users").child(user.getUid()).setValue("Neuer Nutzer")
            .addOnSuccessListener(aVoid -> Log.d("FirebaseDB", "✅ Nutzerdaten gespeichert"))
            .addOnFailureListener(e -> Log.e("FirebaseDB", "❌ Fehler beim Speichern: " + e.getMessage()));
    }
}
