package com.nuts.my.drawnuts.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.app.MainActivity;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    private static final int RC_SIGN_IN = 123;

    private List<AuthUI.IdpConfig> authProviders =
        Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                      new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance()
            .getCurrentUser() == null) {
            startAuthentication();
        } else {
            onAuthenticated();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                onAuthenticated();
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG)
                    .show();
                startAuthentication();
            }
        }
    }

    private void startAuthentication() {
        startActivityForResult(AuthUI.getInstance()
                                   .createSignInIntentBuilder()
                                   .setAvailableProviders(authProviders)
                                   .build(), RC_SIGN_IN);
    }

    private void onAuthenticated() {
        MainActivity.startActivity(this);
    }
}

