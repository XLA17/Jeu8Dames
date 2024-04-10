package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private int lightSquareColorWithBorder;
    private int darkSquareColorWithBorder;
    private TableLayout chessboard;
    private List<int[]> victoryPossibilityList;
    private List<Integer> currentQueensPositions;
    private int countQueen;
    private TextView victoryPercentageView;
    private TextView invalidPlayMessage;
    private TextView textCountQueen;
    private ImageView showAttackQueensHelpView;
    private boolean showAttackQueensHelp;
    private int numberOfQueensHelp;
    private ImageView victoryPercentageHelpView;

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
        lightSquareColorWithBorder = R.drawable.background_light_with_border;
        darkSquareColorWithBorder = R.drawable.background_dark_with_border;
        victoryPossibilityList = new ArrayList<>();
        currentQueensPositions = new ArrayList<>();
        chessboard = findViewById(R.id.chessboard);
        victoryPercentageView = findViewById(R.id.victoryPercentage);
        ImageView cleanButton = findViewById(R.id.cleanButton);
        ImageView confirmButton = findViewById(R.id.confirmButton);
        countQueen = 0;
        invalidPlayMessage = findViewById(R.id.invalidPlayMessage);
        textCountQueen = findViewById(R.id.textCountQueen);
        showAttackQueensHelpView = findViewById(R.id.showAttackQueensHelp);
        victoryPercentageHelpView = findViewById(R.id.victoryPercentageHelp);
        ImageView queensPlacementHelpView = findViewById(R.id.queenPlacementHelp);
        showAttackQueensHelp = false;
        numberOfQueensHelp = 3;

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
                chessboardSquare.setOnClickListener(v -> {
                    if (chessboardSquare.getDrawable() == null) {
                        if (countQueen < 8) {
                            placeQueen(chessboardSquare, row, column);
                        }
                    }
                    else {
                        removeQueen(chessboardSquare, row, column);
                    }
                });
                chessboardRow.addView(chessboardSquare);
            }

            chessboard.addView(chessboardRow);
        }

        ButtonListener cleanButtonListener = new ButtonListener(this);
        cleanButton.setOnClickListener(v -> {
            String title = "Souhaitez vraiment retirer toutes les dames ?";
            String message = "Cette action est rédhibitoire.";
            DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    cleanChessboard();
                }
            };
            cleanButtonListener.showConfirmationDialogBoxRemovable(title, message, positiveAction);
        });
        cleanButtonListener.buttonPressAnimation(cleanButton, R.drawable.clean_button, R.drawable.clean_button_pressed);

        ButtonListener attackQueensHelpListener = new ButtonListener(this);
        showAttackQueensHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Cette action est rédhibitoire.";
            DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    showAttackQueensHelp = true;
                    for (int i = 0; i < 8; i++){
                        TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
                        for (int j = 0; j < 8; j++){
                            ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                            if ((int) chessboardSquare.getTag() > 10){
                                if ((i + j) % 2 == 0) {
                                    chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                                }
                                else {
                                    chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                                }
                            }
                        }
                    }
                    showAttackQueensHelpView.setColorFilter(Color.argb(63, 0, 0, 0));
                    showAttackQueensHelpView.setOnClickListener(null);
                    showAttackQueensHelpView.setOnTouchListener(null);
                }
            };
            attackQueensHelpListener.showConfirmationDialogBox(title, message, positiveAction);
        });
        attackQueensHelpListener.buttonPressAnimation(showAttackQueensHelpView, R.drawable.show_attack_queens_help, R.drawable.show_attack_queens_help_pressed);

        ButtonListener victoryPercentageHelpListener = new ButtonListener(this);
        victoryPercentageHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Cette action est rédhibitoire.";
            DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    victoryPercentageView.setVisibility(View.VISIBLE);
                    victoryPercentageHelpView.setColorFilter(Color.argb(63, 0, 0, 0));
                    victoryPercentageHelpView.setOnClickListener(null);
                    victoryPercentageHelpView.setOnTouchListener(null);
                }
            };
            victoryPercentageHelpListener.showConfirmationDialogBox(title, message, positiveAction);
        });
        victoryPercentageHelpListener.buttonPressAnimation(victoryPercentageHelpView, R.drawable.victory_percent_help_button, R.drawable.victory_percent_help_button_pressed);

        ButtonListener queensPlacementHelpListener = new ButtonListener(this);
        queensPlacementHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Les dames seront réinitialisées.";
            DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    placeRandomQueens(numberOfQueensHelp);
                }
            };
            queensPlacementHelpListener.showConfirmationDialogBox(title, message, positiveAction);
        });
        queensPlacementHelpListener.buttonPressAnimation(queensPlacementHelpView, R.drawable.queen_placement_help, R.drawable.queen_placement_help_pressed);

        ButtonListener confirmButtonListener = new ButtonListener(this);
        confirmButton.setOnClickListener(v -> {
            String title = "Souhaitez vraiment confirmer ce jeu ?";
            String message = "";
            DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    confirmPlay();
                }
            };
            confirmButtonListener.showConfirmationDialogBoxRemovable(title, message, positiveAction);
            confirmPlay();
        });
        confirmButtonListener.buttonPressAnimation(confirmButton, R.drawable.confirm_button, R.drawable.confirm_button_pressed);
    }

    private void placeQueen(ImageView chessboardSquare, int row, int column){
        int squarePosition = (column+1) * 10 + row+1;
        int tag = (int) chessboardSquare.getTag();
        tag += 10;
        chessboardSquare.setTag(tag);
        countQueen++;
        updateQueenCount();
        currentQueensPositions.add(squarePosition);
        if (BrowseAttackedSquares(row, column, true)){
            if ((int) chessboardSquare.getTag() >= 11) {
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                }
                else {
                    chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                }
            }
        }
        victoryPercentageView.setText(calculateVictoryPercentage() + "%");
        chessboardSquare.setImageResource(R.drawable.queen);
    }

    private void removeQueen(ImageView chessboardSquare, int row, int column){
        int squarePosition = (column+1) * 10 + row+1;
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

    // Méthode pour mettre à jour le nombre de reines placées
    private void updateQueenCount() {
        textCountQueen.setText(8 - countQueen + "/8");
    }

    private void cleanChessboard(){
        for (int i = 0; i < 8; i++){
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
            for (int j = 0; j < 8; j++){
                ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                chessboardSquare.setTag(0);
                chessboardSquare.setImageDrawable(null);
                if ((i + j) % 2 == 0) {
                    chessboardSquare.setBackgroundColor(lightSquareColor);
                } else {
                    chessboardSquare.setBackgroundColor(darkSquareColor);
                }
            }
        }
        countQueen = 0;
        updateQueenCount();
        victoryPercentageView.setText("100%");
        currentQueensPositions = new ArrayList<>();
    }

    private void showAttackQueensHelp(){
        showAttackQueensHelp = true;
        for (int i = 0; i < 8; i++){
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
            for (int j = 0; j < 8; j++){
                ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                if ((int) chessboardSquare.getTag() > 10){
                    if ((i + j) % 2 == 0) {
                        chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                    }
                    else {
                        chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                    }
                }
            }
        }
        showAttackQueensHelpView.setColorFilter(Color.argb(63, 0, 0, 0));
        showAttackQueensHelpView.setOnClickListener(null);
        showAttackQueensHelpView.setOnTouchListener(null);
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
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                }
                else {
                    chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                }
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

    private ImageView getChessboardSquare(int row, int column){
        TableRow chessboardRow = (TableRow) chessboard.getChildAt(row);
        return (ImageView) chessboardRow.getChildAt(column);
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

    private void placeRandomQueens(int number){
        int randomPossibilityIndex = (int) (Math.random() * 92);
        int[] randomPossibility = victoryPossibilityList.get(randomPossibilityIndex);
        cleanChessboard();
        ArrayList<Integer> indexQueens = new ArrayList<>();
        while (indexQueens.size() < number) {
            int valeur = (int) (Math.random() *8);
            if (!indexQueens.contains(valeur)) {
                indexQueens.add(valeur);
            }
        }
        for (int i = 0; i < number; i++){
            int row = randomPossibility[indexQueens.get(i)];
            int column = indexQueens.get(i);
            ImageView chessboardSquare = getChessboardSquare(row, column);
            placeQueen(chessboardSquare, row, column);
        }
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
                        isVictory = false;
                    }
                }
            }
            intent.putExtra("isVictory", isVictory);
            startActivity(intent);
        }
        else {
            invalidPlayMessage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> invalidPlayMessage.setVisibility(View.INVISIBLE), 2000);
        }
    }
}