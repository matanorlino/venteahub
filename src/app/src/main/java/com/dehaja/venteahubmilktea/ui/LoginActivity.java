package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dehaja.venteahubmilktea.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registerOnClick(View view) {
        Intent registerIntent = new Intent("android.intent.action.REGISTER");
        startActivity(registerIntent);
    }

}