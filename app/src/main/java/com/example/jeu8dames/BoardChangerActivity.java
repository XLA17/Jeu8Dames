package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BoardChangerActivity extends AppCompatActivity {

    private int selectedFirstColor = 0;
    private int selectedSecondColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_changer);

        GridLayout boardGrid = findViewById(R.id.boardGrid);

        // Définissez les couleurs pour chaque case
        int[][] colors = {
                {R.color.beige, R.color.brown},
                {R.color.black, R.color.white},
                {R.color.yellow, R.color.black},
                {R.color.blue, R.color.white}
        };

        // Créez les cases du plateau
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                View cell = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                cell.setLayoutParams(params);

                // Définissez les couleurs pour chaque case
                int color1 = ContextCompat.getColor(this, colors[i][0]);
                int color2 = ContextCompat.getColor(this, colors[i][1]);
                cell.setBackgroundColor((j % 2 == 0) ? color1 : color2);

                // Ajoutez un OnClickListener pour chaque case
                final int finalI = i;
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFirstColor = colors[finalI][0];
                        selectedSecondColor = colors[finalI][1];
                        saveSelectedColors();
                    }
                });

                boardGrid.addView(cell);
            }
        }
    }

    private void saveSelectedColors() {
        Intent intent = new Intent();
        intent.putExtra("selectedFirstColor", selectedFirstColor);
        intent.putExtra("selectedSecondColor", selectedSecondColor);
        setResult(RESULT_OK, intent);
        finish();
    }
}
