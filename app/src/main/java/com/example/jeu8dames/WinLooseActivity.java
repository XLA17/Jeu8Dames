package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;


public class WinLooseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_loose_layout);

        TextView textView = findViewById(R.id.message);
        ImageView retryButton = findViewById(R.id.RetryButton);

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

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommencer();
            }
        });
    }

    public void recommencer(){
        startActivity(new Intent(this, MenuActivity.class));
    }
    public void quitter(View view) {
        finishAffinity(); // Ferme toutes les activités de l'application
    }


}


