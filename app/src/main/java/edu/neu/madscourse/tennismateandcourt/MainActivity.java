package edu.neu.madscourse.tennismateandcourt;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseUser user;
    private DatabaseReference userRef;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        signIn();
    }

 /*       if (user != null) {
            // User is signed in
            onUserSignedIn();
        } else {
            // No user is signed in
            Log.d(TAG, "I'm here");
            signIn();
        }
*/

    private void onUserSignedIn() {
        String uid = user.getUid();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // User has completed the sign up process
                if (snapshot.hasChild(uid)) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                // User hasn't completed the sign up process
                } else {
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                // Test phone number: 1234567890, verification code: 123456
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setLogo(R.drawable.tennisappicon)
                .setTheme(R.style.Theme_TennisMateAndCourt)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            onUserSignedIn();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                Log.e(TAG, "Sign-in cancelled");
                return;
            }
            // Network error
            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                Log.e(TAG, "Network Error");
                return;
            }
            Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }
}