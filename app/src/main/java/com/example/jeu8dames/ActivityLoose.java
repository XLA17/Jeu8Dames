package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityLoose extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loose_layout);
    }

    public void recommencer(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
