package com.example.jeu8dames; // Remplacez par le nom de votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_introduction);
    }

    // Cette méthode est appelée lorsque le bouton est cliqué.
    public void start(View view) {
        Intent intent = new Intent(this, JeuPrincipal.class);
        startActivity(intent);
    }

}