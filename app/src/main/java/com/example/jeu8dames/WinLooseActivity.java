package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class WinLooseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent != null) {
            boolean isVictory = intent.getBooleanExtra("isVictory", false);

            if (isVictory){
                setContentView(R.layout.win_layout);
            }
            else {
                setContentView(R.layout.loose_layout);
            }
        }
    }

    public void recommencer(){
        startActivity(new Intent(this, MenuActivity.class));
    }
}
