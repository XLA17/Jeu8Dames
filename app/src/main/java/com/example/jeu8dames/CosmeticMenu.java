package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * L'activité CosmeticMenu permet à l'utilisateur de modifier les éléments cosmétiques du jeu.
 */
public class CosmeticMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_menu);
    }

    /**
     * Lance l'activité pour changer l'arrière-plan du jeu.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void changeBackground(View view) {
        Intent intent = new Intent(this, BackgroundChangerActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité pour changer le plateau du jeu.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void changeBoard(View view) {
        Intent intent = new Intent(this, BoardChangerActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité pour changer les pièces du jeu.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void changePieces(View view) {
        Intent intent = new Intent(this, QueenChangerActivity.class);
        startActivity(intent);
    }

    /**
     * Retourne au jeu en fermant cette activité.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void backToGame(View view) {
        finish();
    }
}
