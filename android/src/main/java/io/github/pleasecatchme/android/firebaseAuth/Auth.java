package io.github.pleasecatchme.android.firebaseAuth;

import android.util.Log;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth {
    private FirebaseAuth auth;

    public Auth() {
        auth = FirebaseAuth.getInstance();
    }

    public void authenticateUser(AuthCallback callback) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Log.d("FirebaseAuth", "✅ Bereits eingeloggt als: " + currentUser.getUid());
            callback.onAuthComplete(currentUser);
        } else {
            auth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Log.d("FirebaseAuth", "✅ Erfolgreich anonym eingeloggt: " + user.getUid());
                        callback.onAuthComplete(user);
                    } else {
                        Log.e("FirebaseAuth", "❌ Authentifizierung fehlgeschlagen: ", task.getException());
                        callback.onAuthFailed(task.getException());
                    }
                });
        }
    }

    public interface AuthCallback {
        void onAuthComplete(FirebaseUser user);
        void onAuthFailed(Exception e);
    }
}
