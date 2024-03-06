package com.example.jeu8dames; // Remplacez par le nom de votre package

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityLoose extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loose_layout); // Assurez-vous que activity_menu correspond au nom de votre fichier layout
    }
}
