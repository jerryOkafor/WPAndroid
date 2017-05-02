package me.jerryhanks.parkgidi.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.home.MainActivity;
import timber.log.Timber;

public class AuthActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startMainActivity();
        } else {
            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()

                            .createSignInIntentBuilder()
                            .setAllowNewEmailAccounts(true)
                            .setIsSmartLockEnabled(false)
                            .setTosUrl("https://parkgidi.com/terms-of-service.html")
                            .setTheme(R.style.FirebaseAuthTheme)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    private void startMainActivity() {
        startActivity(MainActivity.createIntent(this));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.i("POT: I am called");
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                startMainActivity(response);
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showMessage(R.string.sign_in_cancelled);
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showMessage(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showMessage(R.string.unknown_error);
                    return;
                }
            }

            showMessage(R.string.unknown_sign_in_response);
        }
    }

    private void startMainActivity(IdpResponse response) {
        startMainActivity();
    }

    private void showMessage(int id) {
        Toasty.info(this, getString(id), Toast.LENGTH_LONG).show();
    }
}
