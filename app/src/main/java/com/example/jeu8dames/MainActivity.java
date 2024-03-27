package com.example.jeu8dames;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<int[]> victoryPossibilities = new ArrayList<>();
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
        victoryPossibilities.add(p1);
        victoryPossibilities.add(p2);
        victoryPossibilities.add(p3);
        victoryPossibilities.add(p4);

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
                            boolean isVictory;
                            if (isArrayInListArray(victoryPossibilities, currentPossibility)){
                                Intent intent = new Intent(MainActivity.this, WinLooseActivity.class);
                                intent.putExtra("isVictory", true);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this, WinLooseActivity.class);
                                intent.putExtra("isVictory", false);
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

    public void openChangeBackgroundActivity(View view) {
        Intent intent = new Intent(this, BackgroundChangerActivity.class);
        int requestCode = 1; // This can be any integer value
        startActivityForResult(intent, requestCode);
    }

    public void openCosmeticMenu(View view) {
            Intent intent = new Intent(this, CosmeticMenu.class);
            int requestCode = 1; // This can be any integer value
            startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int backgroundResId = data.getIntExtra("backgroundResId", 0);
            if (backgroundResId != 0) {
                findViewById(R.id.relativeLayout).setBackgroundResource(backgroundResId);
            }
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