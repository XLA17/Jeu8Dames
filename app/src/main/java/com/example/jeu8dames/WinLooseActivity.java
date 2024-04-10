package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;


public class WinLooseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_loose_layout);

        TextView textView = findViewById(R.id.message);
        ImageView restartButton = findViewById(R.id.restartButton);
        ImageView quitButton = findViewById(R.id.quitButton);
        Intent intent = getIntent();

        if (intent != null) {
            boolean isVictory = intent.getBooleanExtra("isVictory", false);

            if (isVictory){
                textView.setText("Bravo ! Vous avez gagné !");
            }
            else {
                textView.setText("Vous avez perdu !");
            }
        }

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
            }
        });
    }

    public void restart(){
        startActivity(new Intent(this, MenuActivity.class));
    }
    public void quit() {
        finishAffinity();
    }


}


