package com.example.jeu8dames;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * L'activité QueenChangerActivity permet à l'utilisateur de changer l'image de la reine.
 */
public class QueenChangerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_queen);
    }

    /**
     * Termine cette activité et retourne au jeu.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void backToGame(View view) {
        finish();
    }

    /**
     * Change l'image de la reine en fonction de l'option sélectionnée.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void changeQueenImage(View view) {
        // Récupère le tag de l'image pour identifier l'option sélectionnée
        String tag = view.getTag().toString();
        switch (tag) {
            case "imageView1":
                Material.setQueenImage(R.drawable.queen);
                break;
            case "imageView2":
                Material.setQueenImage(R.drawable.black_queen);
                break;
            case "imageView3":
                Material.setQueenImage(R.drawable.queen2);
                break;
            case "imageView4":
                Material.setQueenImage(R.drawable.queen3);
                break;
            default:
                break;
        }

        // Termine cette activité après avoir changé l'image de la reine
        finish();
    }
}
