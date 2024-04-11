package com.example.jeu8dames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * L'activité BackgroundChangerActivity permet à l'utilisateur de changer l'arrière-plan du jeu.
 */
public class BackgroundChangerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
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
     * Change l'arrière-plan du jeu en fonction de l'option sélectionnée.
     *
     * @param view La vue qui a déclenché l'événement.
     */
    public void changeBackground(View view) {
        // Récupère le tag de la vue pour identifier l'option sélectionnée
        String tag = view.getTag().toString();
        Log.d("BackgroundChangerActivity", "Tag: " + tag);

        // Change l'arrière-plan en fonction de l'option sélectionnée
        switch (tag) {
            case "imageView1":
                Material.setBackground(R.drawable.main_background);
                break;
            case "imageView2":
                Material.setBackground(R.drawable.background2);
                break;
            case "imageView3":
                Material.setBackground(R.drawable.background3);
                break;
            case "imageView4":
                Material.setBackground(R.drawable.background4);
                break;
            default:
                break;
        }

        // Termine cette activité après avoir changé l'arrière-plan
        finish();
    }
}
