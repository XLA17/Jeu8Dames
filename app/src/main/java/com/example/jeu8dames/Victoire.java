package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Victoire extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_layout);
    }

    public void recommencer(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
