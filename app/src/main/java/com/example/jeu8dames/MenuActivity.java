package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_introduction);
    }
    public void start(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
