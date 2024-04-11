package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * L'activité WinLooseActivity affiche un message indiquant si le joueur a gagné ou perdu,
 * et fournit des boutons pour redémarrer ou quitter le jeu.
 */
public class WinLooseActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_loose_layout);

        // Récupère les éléments de l'interface utilisateur
        TextView textView = findViewById(R.id.message);
        ImageView restartButton = findViewById(R.id.restartButton);
        ImageView quitButton = findViewById(R.id.quitButton);
        Intent intent = getIntent();

        // Vérifie si l'intention contient une information sur la victoire
        if (intent != null) {
            boolean isVictory = intent.getBooleanExtra("isVictory", false);

            // Affiche le message approprié en fonction de la victoire ou de la défaite
            if (isVictory) {
                textView.setText("Bravo ! Vous avez gagné !");
            } else {
                textView.setText("Vous avez perdu !");
            }
        }

        // Définit les actions des boutons de redémarrage et de quitter
        restartButton.setOnClickListener(v -> restart());
        ButtonListener restartListener = new ButtonListener(this);
        restartListener.buttonPressAnimation(restartButton, R.drawable.restart_game_button, R.drawable.restart_game_button_pressed);
        quitButton.setOnClickListener(v -> quit());
        ButtonListener quitListener = new ButtonListener(this);
        quitListener.buttonPressAnimation(quitButton, R.drawable.quit_game_button, R.drawable.quit_game_button_pressed);
    }

    /**
     * Redémarre le jeu en démarrant une nouvelle activité de menu.
     */
    public void restart() {
        finish();
        startActivity(new Intent(this, MenuActivity.class));
    }

    /**
     * Quitte l'application en fermant toutes les activités de l'application.
     */
    public void quit() {
        finishAffinity();
    }
}
