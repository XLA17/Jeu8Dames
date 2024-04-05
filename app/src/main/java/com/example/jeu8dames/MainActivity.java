package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int backgroundChessSquareLight;
    private int backgroundChessSquareDark;
    private List<int[]> victoryPossibilities;
    private List<Integer> currentPossibility;
    private TableLayout tl;
    private ImageView validerButton;
    private int countQueen;
    private TextView messageTextView;
    private TextView textCountQueen;
    private ImageView imageViewAideLignesAttaque;
    private boolean aideLignesAttaque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundChessSquareLight = getColor(R.color.beige);
        backgroundChessSquareDark = getColor(R.color.brown);
        victoryPossibilities = new ArrayList<>();
        currentPossibility = new ArrayList<>();
        tl = findViewById(R.id.tableLayout);
        validerButton = findViewById(R.id.validerButton);
        countQueen = 0;
        messageTextView = findViewById(R.id.messageTextView);
        textCountQueen = findViewById(R.id.textCountQueen);
        imageViewAideLignesAttaque = findViewById(R.id.aideLignesAttaque);
        aideLignesAttaque = false;

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

        float scale = getResources().getDisplayMetrics().density;
        int sizeSquare = (int) (40 * scale + 0.5f);
        int marginSquare = (int) (2 * scale + 0.5f);

        for (int i = 0; i < 8; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            for (int j = 0; j < 8; j++) {
                ImageView imageView = new ImageView(this);
                if ((i+j) % 2 == 0){
                    imageView.setBackgroundColor(backgroundChessSquareLight);
                }
                else {
                    imageView.setBackgroundColor(backgroundChessSquareDark);
                }
                imageView.setLayoutParams(new TableRow.LayoutParams(sizeSquare, sizeSquare));
                imageView.setPadding(marginSquare,marginSquare,marginSquare,marginSquare);
                imageView.setTag(0);
                int colonne = j;
                int ligne = i;
                int position = (colonne+1) * 10 + ligne+1;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageView.getDrawable() == null) {
                            if (countQueen < 8) {
                                currentPossibility.add(position);
                                int tag = (int) imageView.getTag();
                                tag += 10;
                                imageView.setTag(tag);
                                countQueen++;
                                updateQueenCount();
                                if (afficherDamesAttaquees(ligne, colonne)){
                                    if ((int) imageView.getTag() >= 11) {
                                        imageView.setBackgroundResource(R.drawable.background_light_with_border);
                                    }
                                }
                                imageView.setImageResource(R.drawable.queen);
                                Log.i("TAG", "Valeur de tag (ajout dame): " + (int) imageView.getTag());
                            }
                        }
                        else {
                            currentPossibility.remove((Object) position);
                            countQueen--;
                            updateQueenCount();
                            int tag = (int) imageView.getTag();
                            tag -= 10;
                            if ((ligne + colonne) % 2 == 0) {
                                imageView.setBackgroundColor(backgroundChessSquareLight);
                            } else {
                                imageView.setBackgroundColor(backgroundChessSquareDark);
                            }
                            desafficherDamesAttaquees(ligne, colonne);
                            imageView.setTag(tag);
                            imageView.setImageDrawable(null);
                            Log.i("TAG", "Valeur de tag (rm dame): " + (int) imageView.getTag());
                        }
                    }
                });
                row.addView(imageView);
            }

            tl.addView(row);
        }

        imageViewAideLignesAttaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aideLignesAttaque = true;
                for (int i = 0; i < 8; i++){
                    for (int j = 0; j < 8; j++){
                        TableRow ligneEchiquier = (TableRow) tl.getChildAt(i);
                        ImageView caseEchiquier = (ImageView) ligneEchiquier.getChildAt(j);
                        if ((int) caseEchiquier.getTag() > 10){
                            caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                        }
                    }
                }
                imageViewAideLignesAttaque.setOnClickListener(null);
            }
        });

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

    private boolean afficherDamesAttaquees(int ligne, int colonne){
        ImageView caseEchiquier;
        boolean attaqueAuMoins1Dame = false;
        for (int i = 0; i < 8; i++){
            if (i != colonne){
                caseEchiquier = getCaseEchiquier(ligne, i);
                incrementerTagCaseEchiquier(caseEchiquier);
                if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                    caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                    attaqueAuMoins1Dame = true;
                }
            }
            if (i != ligne){
                caseEchiquier = getCaseEchiquier(i, colonne);
                incrementerTagCaseEchiquier(caseEchiquier);
                if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                    caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                    attaqueAuMoins1Dame = true;
                }
            }
        }
        int lignetmp = ligne;
        int colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 0){
            lignetmp--;
            colonnetmp--;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            incrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                attaqueAuMoins1Dame = true;
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 7) {
            lignetmp--;
            colonnetmp++;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            incrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                attaqueAuMoins1Dame = true;
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 0) {
            lignetmp++;
            colonnetmp--;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            incrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                attaqueAuMoins1Dame = true;
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 7) {
            lignetmp++;
            colonnetmp++;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            incrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() >= 11){
                caseEchiquier.setBackgroundResource(R.drawable.background_light_with_border);
                attaqueAuMoins1Dame = true;
            }
        }
        return attaqueAuMoins1Dame;
    }

    private void incrementerTagCaseEchiquier(ImageView caseEchiquier){
        int tag = (int) caseEchiquier.getTag();
        tag++;
        caseEchiquier.setTag(tag);
    }

    private ImageView getCaseEchiquier(int ligne, int colonne){
        TableRow row = (TableRow) tl.getChildAt(ligne);
        return (ImageView) row.getChildAt(colonne);
    }

    private void desafficherDamesAttaquees(int ligne, int colonne){
        ImageView caseEchiquier;
        for (int i = 0; i < 8; i++) {
            if (i != colonne) {
                caseEchiquier = getCaseEchiquier(ligne, i);
                decrementerTagCaseEchiquier(caseEchiquier);
                if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                    if ((ligne + i) % 2 == 0) {
                        caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                    } else {
                        caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                    }
                }
            }
            if (i != ligne){
                caseEchiquier = getCaseEchiquier(i, colonne);
                decrementerTagCaseEchiquier(caseEchiquier);
                if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                    if ((i + colonne) % 2 == 0) {
                        caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                    } else {
                        caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                    }
                }
            }
        }
        int lignetmp = ligne;
        int colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 0){
            lignetmp--;
            colonnetmp--;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            decrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                if ((ligne + colonne) % 2 == 0) {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                } else {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 0 && colonnetmp != 7) {
            lignetmp--;
            colonnetmp++;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            decrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                if ((ligne + colonne) % 2 == 0) {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                } else {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 0) {
            lignetmp++;
            colonnetmp--;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            decrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                if ((ligne + colonne) % 2 == 0) {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                } else {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                }
            }
        }
        lignetmp = ligne;
        colonnetmp = colonne;
        while (lignetmp != 7 && colonnetmp != 7) {
            lignetmp++;
            colonnetmp++;
            caseEchiquier = getCaseEchiquier(lignetmp, colonnetmp);
            decrementerTagCaseEchiquier(caseEchiquier);
            if (aideLignesAttaque && (int) caseEchiquier.getTag() == 10){
                if ((ligne + colonne) % 2 == 0) {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareLight);
                } else {
                    caseEchiquier.setBackgroundColor(backgroundChessSquareDark);
                }
            }
        }
    }

    private void decrementerTagCaseEchiquier(ImageView caseEchiquier){
        int tag = (int) caseEchiquier.getTag();
        tag--;
        caseEchiquier.setTag(tag);
    }

    // à changer ! parcourir la grille et si un tag > 10 alors PERDU
    public void validerGrille(){
        if (countQueen == 8){
            Intent intent = new Intent(MainActivity.this, WinLooseActivity.class);
            int[] currentPossibilityArray = {-1,-1,-1,-1,-1,-1,-1,-1};
            for (Integer position : currentPossibility) {
                int colonne = (position/10) - 1;
                int ligne = position - ((colonne+1)*10) - 1;
                if (currentPossibilityArray[colonne] == -1){
                    currentPossibilityArray[colonne] = ligne;
                }
                else {
                    intent.putExtra("isVictory", false);
                    startActivity(intent);
                }
            }
            if (isArrayInListArray(victoryPossibilities, currentPossibilityArray)){
                intent.putExtra("isVictory", true);
                startActivity(intent);
            }
            else {
                intent.putExtra("isVictory", false);
                startActivity(intent);
            }
        }
        else {
            messageTextView.setText("Veuillez placer toutes les dames.");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageTextView.setText(""); // Supprimer le texte
                }
            }, 2000);
        }
    }
}