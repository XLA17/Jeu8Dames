package com.example.jeu8dames;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<int[]> victoryPossibilities = new ArrayList<>();
    int[] currentPossibility = {-1,-1,-1,-1,-1,-1,-1,-1};
    TableLayout tl;
    Button validerButton;
    int countQueen = 0;
    TextView messageTextView;
    TextView textCountQueen;
    boolean aideLignesAttaque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tl = findViewById(R.id.tableLayout);
        validerButton = findViewById(R.id.validerButton);
        messageTextView = findViewById(R.id.messageTextView);
        textCountQueen = findViewById(R.id.textCountQueen);
        aideLignesAttaque = true;

        int[] s1 = {1,3,5,7,2,0,6,4};
        int[] s2 = {0,6,3,5,7,1,4,2};
        int[] s3 = {0,6,4,7,1,3,5,2};
        int[] s4 = {3,0,4,7,1,6,2,5};
        int[] s5 = {4,0,7,3,1,6,2,5};
        int[] s6 = {2,0,6,4,7,1,3,5};
        int[] s7 = {4,0,3,5,7,1,6,2};
        int[] s8 = {6,0,2,7,5,3,1,4};
        int[] s9 = {4,0,7,5,2,6,1,3};
        int[] s10 = {4,6,0,3,1,7,5,2};
        int[] s11 = {5,2,0,7,3,1,6,4};
        int[] s12 = {4,2,0,6,1,7,5,3};

        victoryPossibilities.add(s1);
        victoryPossibilities.add(s2);
        victoryPossibilities.add(s3);
        victoryPossibilities.add(s4);
        victoryPossibilities.add(s5);
        victoryPossibilities.add(s6);
        victoryPossibilities.add(s7);
        victoryPossibilities.add(s8);
        victoryPossibilities.add(s9);
        victoryPossibilities.add(s10);
        victoryPossibilities.add(s11);
        victoryPossibilities.add(s12);

        for (int i = 0; i < 12; i++){
            int[] p = victoryPossibilities.get(i);
            int[] p2 = new int[8];
            int[] p3 = new int[8];
            int[] p4 = new int[8];
            int[] p5 = new int[8];
            int[] p6 = new int[8];
            int[] p7 = new int[8];
            int[] p8 = new int[8];
            for (int j = 0; j < 8; j++){
                int k = p[j];
                p2[k] = 7 - j; // rotation gauche 90°
                p3[7 - j] = 7 - k; // rotation 180°
                p4[7 - k] = j; // rotation droite 90°
                p5[j] = 7 - k; // translation horizontal
                p6[7 - j] = k; // translation verticale
                p7[k] = j; // translation diagonale 1
                p8[7 - k] = 7 - j; // translation diagonale 2
            }
            if (i != 11){
                victoryPossibilities.add(p3);
                victoryPossibilities.add(p4);
                victoryPossibilities.add(p6);
                victoryPossibilities.add(p8);
            }
            victoryPossibilities.add(p2);
            victoryPossibilities.add(p5);
            victoryPossibilities.add(p7);
        }

        for (int i = 0; i < 8; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            for (int j = 0; j < 8; j++) {
                ImageView imageView = new ImageView(this);
                if ((i+j) % 2 == 0){
                    imageView.setBackgroundColor(getResources().getColor(R.color.beige));
                }
                else {
                    imageView.setBackgroundColor(getResources().getColor(R.color.brown));
                }
                imageView.setLayoutParams(new TableRow.LayoutParams(100, 100));
                imageView.setPadding(5,5,5,5);
                imageView.setTag(0);
                int colonne = j;
                int ligne = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageView.getDrawable() == null) {
                            if (countQueen < 8) {
                                currentPossibility[colonne] = ligne;
                                countQueen++;
                                updateQueenCount();
                                if (aideLignesAttaque){
                                    affichageLignesAttaque(ligne, colonne);
                                }
                                imageView.setImageResource(R.drawable.queen);
                            }
                        }
                        else {
                            currentPossibility[colonne] = 0;
                            countQueen--;
                            updateQueenCount();
                            if (aideLignesAttaque){
                                suppressionLignesAttaque(ligne, colonne);
                            }
                            imageView.setImageDrawable(null);
                        }
                    }
                });
                row.addView(imageView);
            }

            tl.addView(row);
        }

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerGrille();
            }
        });
    }

    private boolean isArrayInListArray(List<int[]> arrays, int[] array) {
        for (int i = 0; i < arrays.size(); i++){
            int[] array2 = arrays.get(i);
            if (array == null | array2 == null || array2.length != array.length) {
                return false;
            }
            if (Arrays.equals(array, array2)){
                return true;
            }
        }
        return false;
    }

    // Méthode pour mettre à jour le nombre de reines placées
    private void updateQueenCount() {
        textCountQueen.setText(8 - countQueen + "/8");
    }

    private void affichageLignesAttaque(int ligne, int colonne){
        TableRow row = (TableRow) tl.getChildAt(ligne);
        for (int i = 0; i < 8; i++){
            TableRow eachRow = (TableRow) tl.getChildAt(i);
            ImageView imageView = (ImageView) row.getChildAt(i);
            int tag = (int) imageView.getTag();
            imageView.setTag(tag + 1);
            if ((ligne+i) % 2 == 0){
                imageView.setBackgroundColor(getColor(R.color.white));
            }
            else {
                imageView.setBackgroundColor(getColor(R.color.black));
            }
            if (i != ligne) {
                imageView = (ImageView) eachRow.getChildAt(colonne);
                tag = (int) imageView.getTag();
                imageView.setTag(tag + 1);
                if ((colonne + i) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.white));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.black));
                }
            }
        }
        int lignetmp = ligne;
        int colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 0){
            lignetmp--;
            colonnetmp--;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            imageView.setTag(tag + 1);
            if ((ligne+colonne) % 2 == 0){
                imageView.setBackgroundColor(getColor(R.color.white));
            }
            else {
                imageView.setBackgroundColor(getColor(R.color.black));
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 7) {
            lignetmp--;
            colonnetmp++;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            imageView.setTag(tag + 1);
            if ((ligne + colonne) % 2 == 0) {
                imageView.setBackgroundColor(getColor(R.color.white));
            } else {
                imageView.setBackgroundColor(getColor(R.color.black));
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 0) {
            lignetmp++;
            colonnetmp--;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            imageView.setTag(tag + 1);
            if ((ligne + colonne) % 2 == 0) {
                imageView.setBackgroundColor(getColor(R.color.white));
            } else {
                imageView.setBackgroundColor(getColor(R.color.black));
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 7) {
            lignetmp++;
            colonnetmp++;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            imageView.setTag(tag + 1);
            if ((ligne + colonne) % 2 == 0) {
                imageView.setBackgroundColor(getColor(R.color.white));
            } else {
                imageView.setBackgroundColor(getColor(R.color.black));
            }
        }
    }

    private void suppressionLignesAttaque(int ligne, int colonne){
        TableRow row = (TableRow) tl.getChildAt(ligne);
        for (int i = 0; i < 8; i++) {
            TableRow eachRow = (TableRow) tl.getChildAt(i);
            ImageView imageView = (ImageView) row.getChildAt(i);
            int tag = (int) imageView.getTag();
            tag--;
            imageView.setTag(tag);
            if (tag == 0) {
                if ((ligne + i) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.beige));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.brown));
                }
            }
            if (i != ligne) {
                imageView = (ImageView) eachRow.getChildAt(colonne);
                tag = (int) imageView.getTag();
                tag--;
                imageView.setTag(tag);
                if (tag == 0) {
                    if ((colonne + i) % 2 == 0) {
                        imageView.setBackgroundColor(getColor(R.color.beige));
                    } else {
                        imageView.setBackgroundColor(getColor(R.color.brown));
                    }
                }
            }
        }
        int lignetmp = ligne;
        int colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 0){
            lignetmp--;
            colonnetmp--;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            tag--;
            imageView.setTag(tag);
            if (tag == 0) {
                if ((ligne + colonne) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.beige));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.brown));
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 7) {
            lignetmp--;
            colonnetmp++;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            tag--;
            imageView.setTag(tag);
            if (tag == 0) {
                if ((ligne + colonne) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.beige));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.brown));
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 0) {
            lignetmp++;
            colonnetmp--;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            tag--;
            imageView.setTag(tag);
            if (tag == 0) {
                if ((ligne + colonne) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.beige));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.brown));
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 7) {
            lignetmp++;
            colonnetmp++;
            TableRow rowtmp = (TableRow) tl.getChildAt(lignetmp);
            ImageView imageView = (ImageView) rowtmp.getChildAt(colonnetmp);
            int tag = (int) imageView.getTag();
            tag--;
            imageView.setTag(tag);
            if (tag == 0) {
                if ((ligne + colonne) % 2 == 0) {
                    imageView.setBackgroundColor(getColor(R.color.beige));
                } else {
                    imageView.setBackgroundColor(getColor(R.color.brown));
                }
            }
        }
    }

    public void validerGrille(){
        if (countQueen == 8){
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
        else {
            messageTextView.setVisibility(View.VISIBLE);
        }
    }
}