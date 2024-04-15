package com.example.jeu8dames;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

/**
 * L'activité BoardChangerActivity permet à l'utilisateur de changer les couleurs du plateau de jeu.
 */
public class BoardChangerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_board);

        // Récupère les lignes du plateau de jeu
        TableRow row1 = findViewById(R.id.row1);
        TableRow row2 = findViewById(R.id.row2);
        TableRow row3 = findViewById(R.id.row3);
        TableRow row4 = findViewById(R.id.row4);

        // Ajoute un écouteur de clic à chaque ligne pour changer les couleurs du plateau
        row1.setOnClickListener(v -> changeBoardColor(row1));
        row2.setOnClickListener(v -> changeBoardColor(row2));
        row3.setOnClickListener(v -> changeBoardColor(row3));
        row4.setOnClickListener(v -> changeBoardColor(row4));
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
     * Change les couleurs du plateau de jeu en fonction de la ligne sélectionnée.
     *
     * @param row La ligne du plateau de jeu à modifier.
     */
    private void changeBoardColor(TableRow row) {
        // Récupère le tag de la ligne pour identifier l'option sélectionnée
        String tag = (String) row.getTag();
        switch (tag) {
            case "row1":
                Material.setLightColor(R.color.beige);
                Material.setDarkColor(R.color.brown);
                Material.setBackgroundLightWithColor(R.drawable.background_beige_with_border);
                Material.setBackgroundDarkWithColor(R.drawable.background_brown_with_border);
                break;
            case "row2":
                Material.setLightColor(R.color.white);
                Material.setDarkColor(R.color.black);
                Material.setBackgroundLightWithColor(R.drawable.background_white_with_border);
                Material.setBackgroundDarkWithColor(R.drawable.background_black_with_border);
                break;
            case "row3":
                Material.setLightColor(R.color.beige);
                Material.setDarkColor(R.color.black);
                Material.setBackgroundLightWithColor(R.drawable.background_beige_with_border);
                Material.setBackgroundDarkWithColor(R.drawable.background_black_with_border);
                break;
            case "row4":
                Material.setLightColor(R.color.white);
                Material.setDarkColor(R.color.blue);
                Material.setBackgroundLightWithColor(R.drawable.background_white_with_border);
                Material.setBackgroundDarkWithColor(R.drawable.background_blue_with_border);
                break;
            default:
                break;
        }

        // Termine cette activité après avoir changé les couleurs du plateau
        finish();
    }
}
