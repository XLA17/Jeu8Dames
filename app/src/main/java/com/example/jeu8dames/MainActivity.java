package com.example.jeu8dames;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<int[]> possibilitesVictoire = new ArrayList<>();
    int[] currentPossibility = {0, 0, 0, 0, 0, 0, 0, 0};
    TableLayout tl;
    int countQueen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tl = findViewById(R.id.tableLayout);

        int[] p1 = {4,9,21,32,34,47,51,62};
        int[] p2 = {7,12,18,32,38,41,51,61};
        int[] p3 = {3,14,18,31,33,44,56,61};
        int[] p4 = {4,14,24,27,33,47,53,58};
        possibilitesVictoire.add(p1);
        possibilitesVictoire.add(p2);
        possibilitesVictoire.add(p3);
        possibilitesVictoire.add(p4);

        for (int i = 0; i < 8; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            for (int j = 1; j <= 8; j++) {
                ImageView imageView = new ImageView(this);
                int index = 8 * i + j;
                int colorRes = (i % 2 == 0) ? ((j % 2 == 0) ? R.color.beige : R.color.brown)
                        : ((j % 2 == 0) ? R.color.brown : R.color.beige);
                imageView.setBackgroundColor(getResources().getColor(colorRes));
                imageView.setLayoutParams(new TableRow.LayoutParams(100, 100));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countQueen < 8) {
                            if (imageView.getDrawable() == null) {
                                currentPossibility[countQueen] = index;
                                countQueen++;
                                imageView.setImageResource(R.drawable.queen);
                            }
                            else {
                                currentPossibility[countQueen] = 0;
                                countQueen--;
                                imageView.setImageDrawable(null);
                            }
                        }
                        if (countQueen == 8){
                            if (isArrayInListArray(possibilitesVictoire, currentPossibility)){
                                Intent intent = new Intent(MainActivity.this, Victoire.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this, ActivityLoose.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
                row.addView(imageView);
            }

            tl.addView(row);
        }
    }

    private boolean isArrayInListArray(List<int[]> arrays, int[] array) {
        for (int i = 0; i < arrays.size(); i++){
            int[] array2 = arrays.get(i);
            if (array == null || array2 == null || array2.length != array.length) {
                return false;
            }
            Arrays.sort(array);
            Arrays.sort(array2);
            if (Arrays.equals(array, array2)){
                return true;
            }
        }
        return false;
    }
}