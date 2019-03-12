package com.example.bob.health_helper.Base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void navigateForResultTo(Class to, int requestCode) {
        Intent intent = new Intent(this, to);
        startActivityForResult(intent, requestCode);
    }

    public void navigateTo(Class to) {
        Intent intent = new Intent(this, to);
        startActivity(intent);
        finish();
    }
}