package io.github.pleasecatchme.android.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.pleasecatchme.R;
import io.github.pleasecatchme.android.AndroidLauncher;

public class LandingPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private CredentialManager credentialManager;
    private ExecutorService executorService;
    private static final String WEB_CLIENT_ID = "DEIN_WEB_CLIENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        auth = FirebaseAuth.getInstance();
        credentialManager = CredentialManager.create(this);
        executorService = Executors.newSingleThreadExecutor();

        Button btnGoogleLogin = findViewById(R.id.btn_google_login);
        Button btnPlay = findViewById(R.id.btn_play);

        btnGoogleLogin.setOnClickListener(v -> signInWithGoogle());

        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, AndroidLauncher.class);
            startActivity(intent);
            finish();
        });

        // Automatische Anmeldung für wiederkehrende Nutzer aktivieren
        autoSignIn();
    }

    private void signInWithGoogle() {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build();

        credentialManager.getCredentialAsync(
            this, // Korrekte Reihenfolge: zuerst der Context
            request,
            null, // Kein `CancellationSignal` nötig
            executorService,
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(GetCredentialResponse getCredentialResponse) {

                }

                @Override
                public void onError(@NonNull GetCredentialException e) {

                }

            }
        );
    }

    private void autoSignIn() {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true) // Nur autorisierte Konten automatisch auswählen
            .setServerClientId(WEB_CLIENT_ID)
            .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build();

        credentialManager.getCredentialAsync(
            this,
            request,
            null, // Kein `CancellationSignal` nötig
            executorService,
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(GetCredentialResponse getCredentialResponse) {

                }

                @Override
                public void onError(@NonNull GetCredentialException e) {

                }
            }
        );
    }

    private void handleSignIn(GetCredentialResponse result) {
        try {
            GoogleIdTokenCredential credential = GoogleIdTokenCredential.createFrom(result.getCredential().getData());
            firebaseAuthWithGoogle(credential.getIdToken());
        } catch (Exception e) {
            Log.e("GoogleSignIn", "❌ Fehler beim Abrufen des Google ID-Tokens", e);
        }
    }

    private void handleFailure(Exception e) {
        if (e instanceof GetCredentialException) {
            Log.e("GoogleSignIn", "❌ Keine gespeicherten Anmeldedaten gefunden.");
        } else {
            Log.e("GoogleSignIn", "❌ Google Sign-In fehlgeschlagen", e);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    Log.d("GoogleSignIn", "✅ Erfolgreich angemeldet als: " + user.getDisplayName());
                    Toast.makeText(LandingPage.this, "Angemeldet als " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("GoogleSignIn", "❌ Anmeldung fehlgeschlagen", task.getException());
                    Toast.makeText(LandingPage.this, "Fehler bei der Anmeldung", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
