package com.example.jeu8dames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PieceChangerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pieces);
    }

    public void backToGame(View view) {
        finish();
    }

    public void changePieces(View view) {
        int piecesID = 0;
        String tag = view.getTag().toString();
        switch (tag) {
            case "imageView1":
                piecesID = R.drawable.queen;
                break;
            case "imageView2":
                piecesID = R.drawable.black_queen;
                break;
            case "imageView3":
                piecesID = R.drawable.queen2;
                break;
            case "imageView4":
                piecesID = R.drawable.queen3;
                break;
            default:
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("piecesID", piecesID);
        setResult(RESULT_OK, intent);
        finish();
    }


}
