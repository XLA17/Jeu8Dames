package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CosmeticMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_menu);
    }

    public void changeBackground(View view) {
        Intent intent = new Intent(this, BackgroundChangerActivity.class);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int backgroundResId = data.getIntExtra("backgroundResId", 0);
            if (backgroundResId != 0) {
                Intent intent = new Intent();
                intent.putExtra("backgroundResId", backgroundResId);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            int piecesID = data.getIntExtra("piecesID", 0);
            if (piecesID != 0) {
                // Obtenez une référence à MainActivity et appelez setSelectedPieceID()
                MainActivity mainActivity = MainActivity.getInstance();
                if (mainActivity != null) {
                    mainActivity.setSelectedPieceID(piecesID);
                    finish();
                }
            }
        }
    }

    public void changeBoard(View view) {
        Intent intent = new Intent(this, BoardChangerActivity.class);
        int requestCode = 2;
        startActivityForResult(intent, requestCode);
    }

    public void changePieces(View view) {
        Intent intent = new Intent(this, PieceChangerActivity.class);
        int requestCode = 3;
        startActivityForResult(intent, requestCode);
    }

    public void backToGame(View view) {
        finish();
    }
}