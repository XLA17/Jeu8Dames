package com.example.jeu8dames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CosmeticMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_menu2);
    }

    /*public void changeBackground(View view) {
        Intent intent = new Intent(this, BackgroundChangerActivity.class);
        startActivity(intent);
    }*/

    public void changeBoard(View view) {
        // Implement board changer logic
    }

    public void changePieces(View view) {
        // Implement piece changer logic
    }
}
