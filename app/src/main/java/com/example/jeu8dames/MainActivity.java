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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int lightSquareColor;
    private int darkSquareColor;
    private TableLayout chessboard;
    private List<int[]> victoryPossibilityList;
    private List<Integer> currentQueensPositions;
    private int countQueen;
    private TextView victoryPercentageView;
    private TextView invalidPlayMessage;
    private TextView textCountQueen;
    private ImageView showAttackQueensHelpView;
    private boolean showAttackQueensHelp;

    /**
     * Initializes the activity.
     * Sets up the chessboard, event listeners, and game variables.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization of variables
        lightSquareColor = getResources().getColor((R.color.beige), getTheme());
        darkSquareColor = getResources().getColor((R.color.brown), getTheme());
        victoryPossibilityList = new ArrayList<>();
        currentQueensPositions = new ArrayList<>();
        chessboard = findViewById(R.id.chessboard);
        victoryPercentageView = findViewById(R.id.victoryPercentage);
        ImageView confirmButton = findViewById(R.id.confirmButton);
        countQueen = 0;
        invalidPlayMessage = findViewById(R.id.invalidPlayMessage);
        textCountQueen = findViewById(R.id.textCountQueen);
        showAttackQueensHelpView = findViewById(R.id.showAttackQueensHelp);
        ImageView victoryPercentageHelpView = findViewById(R.id.victoryPercentageHelp);
        showAttackQueensHelp = false;

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

        victoryPossibilityList.add(s1);
        victoryPossibilityList.add(s2);
        victoryPossibilityList.add(s3);
        victoryPossibilityList.add(s4);
        victoryPossibilityList.add(s5);
        victoryPossibilityList.add(s6);
        victoryPossibilityList.add(s7);
        victoryPossibilityList.add(s8);
        victoryPossibilityList.add(s9);
        victoryPossibilityList.add(s10);
        victoryPossibilityList.add(s11);
        victoryPossibilityList.add(s12);

        for (int i = 0; i < 12; i++){
            int[] p = victoryPossibilityList.get(i);
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
                victoryPossibilityList.add(p3);
                victoryPossibilityList.add(p4);
                victoryPossibilityList.add(p6);
                victoryPossibilityList.add(p8);
            }
            victoryPossibilityList.add(p2);
            victoryPossibilityList.add(p5);
            victoryPossibilityList.add(p7);
        }

        float scale = getResources().getDisplayMetrics().density;
        int sizeSquare = (int) (40 * scale + 0.5f);
        int marginSquare = (int) (2 * scale + 0.5f);

        for (int i = 0; i < 8; i++) {
            TableRow chessboardRow = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            chessboardRow.setLayoutParams(layoutParams);

            for (int j = 0; j < 8; j++) {
                ImageView chessboardSquare = new ImageView(this);
                if ((i+j) % 2 == 0){
                    chessboardSquare.setBackgroundColor(lightSquareColor);
                }
                else {
                    chessboardSquare.setBackgroundColor(darkSquareColor);
                }
                chessboardSquare.setLayoutParams(new TableRow.LayoutParams(sizeSquare, sizeSquare));
                chessboardSquare.setPadding(marginSquare,marginSquare,marginSquare,marginSquare);
                chessboardSquare.setTag(0);
                int column = j;
                int row = i;
                int squarePosition = (column+1) * 10 + row+1;
                chessboardSquare.setOnClickListener(v -> {
                    if (chessboardSquare.getDrawable() == null) {
                        if (countQueen < 8) {
                            int tag = (int) chessboardSquare.getTag();
                            tag += 10;
                            chessboardSquare.setTag(tag);
                            countQueen++;
                            updateQueenCount();
                            Log.i("TAG", "onCreate: " + squarePosition);
                            currentQueensPositions.add(squarePosition);
                            if (BrowseAttackedSquares(row, column, true)){
                                if ((int) chessboardSquare.getTag() >= 11) {
                                    chessboardSquare.setBackgroundResource(R.drawable.background_light_with_border);
                                }
                            }
                            victoryPercentageView.setText(calculateVictoryPercentage() + "%");
                            chessboardSquare.setImageResource(R.drawable.queen);
                        }
                    }
                    else {
                        countQueen--;
                        updateQueenCount();
                        currentQueensPositions.remove((Object) squarePosition);
                        int tag = (int) chessboardSquare.getTag();
                        tag -= 10;
                        if ((row + column) % 2 == 0) {
                            chessboardSquare.setBackgroundColor(lightSquareColor);
                        } else {
                            chessboardSquare.setBackgroundColor(darkSquareColor);
                        }
                        BrowseAttackedSquares(row, column, false);
                        chessboardSquare.setTag(tag);
                        victoryPercentageView.setText(calculateVictoryPercentage() + "%");
                        chessboardSquare.setImageDrawable(null);
                    }
                });
                chessboardRow.addView(chessboardSquare);
            }

            chessboard.addView(chessboardRow);
        }

        showAttackQueensHelpView.setOnClickListener(v -> {
            showAttackQueensHelp = true;
            for (int i = 0; i < 8; i++){
                TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
                for (int j = 0; j < 8; j++){
                    ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                    if ((int) chessboardSquare.getTag() > 10){
                        chessboardSquare.setBackgroundResource(R.drawable.background_light_with_border);
                    }
                }
            }
            showAttackQueensHelpView.setOnClickListener(null);
        });

        victoryPercentageHelpView.setOnClickListener(v -> {
            victoryPercentageView.setVisibility(View.VISIBLE);
            victoryPercentageHelpView.setOnClickListener(null);
        });

        confirmButton.setOnClickListener(v -> confirmPlay());
    }

    // Méthode pour mettre à jour le nombre de reines placées
    private void updateQueenCount() {
        textCountQueen.setText(8 - countQueen + "/8");
    }

    private boolean BrowseAttackedSquares(int row, int column, boolean isAttacking){
        boolean areQueensAttacked = false;
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Directions pour les diagonales

        for (int i = 0; i < 8; i++){
            if (i != column){
                if (updateChessboardSquare(row, i, isAttacking)){
                    areQueensAttacked = true;
                }
            }
            if (i != row){
                if (updateChessboardSquare(i, column, isAttacking)){
                    areQueensAttacked = true;
                }
            }
        }

        for (int[] direction : directions) {
            int tmpRow = row + direction[0];
            int tmpColumn = column + direction[1];

            while (tmpRow >= 0 && tmpRow < 8 && tmpColumn >= 0 && tmpColumn < 8) {
                if (updateChessboardSquare(tmpRow, tmpColumn, isAttacking)){
                    areQueensAttacked = true;
                }
                tmpRow += direction[0];
                tmpColumn += direction[1];
            }
        }
        return areQueensAttacked;
    }

    private boolean updateChessboardSquare(int row, int column, boolean increment){
        ImageView chessboardSquare = getChessboardSquare(row, column);
        updateSquareTag(chessboardSquare, increment);
        if (increment){
            if (showAttackQueensHelp && (int) chessboardSquare.getTag() >= 11){
                chessboardSquare.setBackgroundResource(R.drawable.background_light_with_border);
                return true;
            }
        }
        else {
            if (showAttackQueensHelp && (int) chessboardSquare.getTag() == 10){
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundColor(lightSquareColor);
                } else {
                    chessboardSquare.setBackgroundColor(darkSquareColor);
                }
                return true;
            }
        }
        return false;
    }

    private void updateSquareTag(ImageView caseEchiquier, boolean increment){
        int tag = (int) caseEchiquier.getTag();
        tag += increment ? 1 : -1;
        caseEchiquier.setTag(tag);
    }

    private ImageView getChessboardSquare(int ligne, int colonne){
        TableRow row = (TableRow) chessboard.getChildAt(ligne);
        return (ImageView) row.getChildAt(colonne);
    }

    private int calculateVictoryPercentage(){
        int countPossibility = 0;
        if (victoryPossibilityList.size() == 0){
            return 100;
        }
        for (int[] victoryPossibility : victoryPossibilityList) {
            boolean isPossible = true;
            for (Integer queenPosition : currentQueensPositions) {
                int column = queenPosition / 10 - 1;
                int row = queenPosition - (column * 10) - 11;
                Log.i("TAG", "calculateVictoryPercentage: " + column + ";" + row);
                Log.i("TAG", "VP calculateVictoryPercentage: " + victoryPossibility[column]);
                if (victoryPossibility[column] != row){
                    isPossible = false;
                }
            }
            if (isPossible){
                countPossibility++;
            }
        }
        return Math.round((float) countPossibility / 92 * 100);
    }

    private void confirmPlay(){
        if (countQueen == 8){
            Intent intent = new Intent(MainActivity.this, WinLooseActivity.class);
            boolean isVictory = true;
            for (int i = 0; i < 8; i++){
                TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
                for (int j = 0; j < 8; j++){
                    ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                    if ((int) chessboardSquare.getTag() > 10){
                        Log.i("TAG", "confirmPlay: " + (int) chessboardSquare.getTag());
                        isVictory = false;
                    }
                }
            }
            intent.putExtra("isVictory", isVictory);
            startActivity(intent);
        }
        else {
            /*invalidPlayMessage.setText("Veuillez placer toutes les dames.");

            // Supprimer le texte au bout de 2 secondes.
            new Handler().postDelayed(() -> invalidPlayMessage.setText(""), 2000);*/

            invalidPlayMessage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> invalidPlayMessage.setVisibility(View.INVISIBLE), 2000);
        }
    }
}