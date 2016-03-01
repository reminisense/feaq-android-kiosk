package com.reminisense.featherq.kiosk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.reminisense.featherq.kiosk.BuildConfig;
import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.managers.CacheManager;
import com.reminisense.featherq.kiosk.models.api.LoginEmail;
import com.reminisense.featherq.kiosk.models.api.UserInfo;
import com.reminisense.featherq.kiosk.utils.FeaqEndpoint;
import com.reminisense.featherq.kiosk.utils.RestClient;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String MESSAGE_LOGGING_IN = "Logging in...";
    private static final String MESSAGE_FAILED = "Failed to log in, please try again";
    private static final String MESSAGE_WELCOME = "Welcome!";
    private static final String MESSAGE_EMPTY = "";

    @Bind(R.id.edtEmail) EditText email;
    @Bind(R.id.edtPassword) EditText password;
    @Bind(R.id.btnLogin) AppCompatButton login;
    @Bind(R.id.btnFacebookLogin) LoginButton loginFacebook;
    @Bind(R.id.lblLoginMessage) TextView message;

    private CallbackManager callbackManager;
    private FeaqEndpoint apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Initialize API via Retrofit
        apiService = new RestClient().getApiService();

        // Setup Facebook login callbacks
        callbackManager = CallbackManager.Factory.create();
        loginFacebook.setReadPermissions(Arrays.asList("user_friends", "email", "public_profile"));
        loginFacebook.registerCallback(callbackManager, new LoginCallback());

        login.setOnClickListener(new LoginClickListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();

            loginByEmail(emailInput, passwordInput);
            setFieldsEnabled(false);
            setMessage(MESSAGE_LOGGING_IN);
        }
    }

    private class LoginCallback implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("SUCCESS", loginResult.getAccessToken().toString());
            AccessToken accessToken = loginResult.getAccessToken();
            String facebookId = accessToken.getUserId();
            loginFacebook.setEnabled(false);
            setMessage(MESSAGE_EMPTY);

            Intent intent = new Intent();
            intent.setClassName(BuildConfig.APPLICATION_ID,
                    BuildConfig.APPLICATION_ID + ".activities.KioskActivity");
            startActivity(intent);
            finish();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "Login cancelled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(LoginActivity.this, "Error logging in. Please try again.", Toast.LENGTH_LONG).show();
        }

    }

    private void loginByEmail(String email, String password) {
        LoginEmail loginEmail = new LoginEmail(email, password);

        Call<UserInfo> call = apiService.loginByEmail(loginEmail);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if ( response.code() == 200 ) {
                    UserInfo userInfo = response.body();
                    if ( userInfo.getSuccess() == 1 ) {
                        CacheManager.storeUserInfo(LoginActivity.this, userInfo);
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        setMessage(MESSAGE_WELCOME);

                        Intent intent = new Intent();
                        intent.setClassName(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID + ".activities.KioskActivity");
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error logging in, please try again", Toast.LENGTH_LONG).show();
                        setFieldsEnabled(true);
                        setMessage(MESSAGE_FAILED);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error connecting to server, please try again", Toast.LENGTH_LONG).show();
                    setFieldsEnabled(true);
                    setMessage(MESSAGE_FAILED);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error connecting to server, please try again", Toast.LENGTH_LONG).show();
                t.printStackTrace();
                setFieldsEnabled(true);
                setMessage(MESSAGE_FAILED);
            }
        });
    }

    private void setFieldsEnabled(boolean enabled) {
        if ( enabled ) {
            email.setEnabled(true);
            password.setEnabled(true);
            login.setEnabled(true);
            loginFacebook.setEnabled(true);
        } else {
            email.setEnabled(false);
            password.setEnabled(false);
            login.setEnabled(false);
            loginFacebook.setEnabled(false);
        }
    }

    private void setMessage(String content) {
        message.setText(content);
    }
}
