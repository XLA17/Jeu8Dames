package com.example.jeu8dames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BackgroundChangerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
    }

    public void backToGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeBackground(View view) {
        int backgroundResId = 0;
        String tag = view.getTag().toString();
        switch (tag) {
            case "imageView1":
                backgroundResId = R.drawable.fond_principal;
                break;
            case "imageView2":
                backgroundResId = R.drawable.fond2;
                break;
            case "imageView3":
                backgroundResId = R.drawable.fond3;
                break;
            case "imageView4":
                backgroundResId = R.drawable.fond4;
                break;
            default:
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("backgroundResId", backgroundResId);
        setResult(RESULT_OK, intent);
        finish();
    }
}

