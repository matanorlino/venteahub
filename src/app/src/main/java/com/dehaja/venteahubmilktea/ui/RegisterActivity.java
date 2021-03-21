package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dehaja.venteahubmilktea.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void submitOnClick(View view) {
        Intent submitIntent = new Intent("android.intent.action.LOGIN");
        startActivity(submitIntent);
    }
}