package com.example.jeu8dames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
}
