package com.reminisense.featherq.kiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(this);

        Intent intent = new Intent();
        intent.setClassName("com.reminisense.featherq.kiosk",
                "com.reminisense.featherq.kiosk.activities.LoginActivity");
        startActivity(intent);
        finish();
    }
}
