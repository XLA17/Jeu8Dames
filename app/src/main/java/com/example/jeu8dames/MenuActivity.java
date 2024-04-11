package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_screen);

        ImageView startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> start());

        // Création du bouton pour gérer l'animation lors du clic.
        ButtonListener startListener = new ButtonListener(this);
        // Applique une animation au bouton.
        startListener.buttonPressAnimation(startButton, R.drawable.start_game_button, R.drawable.start_game_button_pressed);
    }

    /*
     * Méthode permettant de lancer l'activité principale.
     */
    public void start() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
