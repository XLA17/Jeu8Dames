package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;

/**
 * Classe MainActivity pour le jeu des 8 dames.
 */
public class MainActivity extends AppCompatActivity implements Material.MaterialChangeListener {

    // Variables pour les couleurs et les éléments de l'interface utilisateur
    private int lightColor;
    private int darkColor;
    private int lightSquareColorWithBorder;
    private int darkSquareColorWithBorder;
    private int countQueen;
    private int numberOfQueensHelp;
    private int queenImage;
    private TableLayout chessboard;
    private List<int[]> victoryPossibilityList;
    private List<Integer> currentQueensPositions;
    private TextView victoryPercentageView;
    private TextView invalidPlayMessage;
    private TextView textCountQueen;
    private ImageView showAttackQueensHelpView;
    private ImageView victoryPercentageHelpView;
    private ImageView soundButton;
    private ImageView queenImageView;
    private MediaPlayer mediaPlayer;
    private ConstraintLayout backgroundView;
    private boolean showAttackQueensHelp;
    private boolean isMute;

    /**
     * Initialise l'activité.
     * Configure l'échiquier, les écouteurs d'événements et les variables du jeu.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des variables
        lightColor = getColor(Material.getLightColor());
        darkColor = getColor(Material.getDarkColor());
        lightSquareColorWithBorder = R.drawable.background_beige_with_border;
        darkSquareColorWithBorder = R.drawable.background_brown_with_border;
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
        soundButton = findViewById(R.id.soundButton);
        queenImage = R.drawable.queen;
        ImageView settingsButton = findViewById(R.id.settings);
        backgroundView = findViewById(R.id.constraintLayout);
        queenImageView = findViewById(R.id.queenImage);
        isMute = false;

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

        // Définit l'arrière-plan de la vue en utilisant une ressource de fond récupérée via la classe Material
        backgroundView.setBackgroundResource(Material.getBackground());

        // Définit la taille des carrés du damier et des marges en fonction de la densité de l'écran pour garantir une présentation adaptée
        float scale = getResources().getDisplayMetrics().density;
        int sizeSquare = (int) (40 * scale + 0.5f);
        int marginSquare = (int) (2 * scale + 0.5f);

        // Crée un damier de 8x8 avec des cases alternativement colorées représentant les cases du jeu de dames
        for (int i = 0; i < 8; i++) {
            TableRow chessboardRow = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            chessboardRow.setLayoutParams(layoutParams);

            for (int j = 0; j < 8; j++) {
                ImageView chessboardSquare = new ImageView(this);
                // Alterne les couleurs des cases en fonction de leur position sur le damier
                if ((i+j) % 2 == 0){
                    chessboardSquare.setBackgroundColor(lightColor);
                }
                else {
                    chessboardSquare.setBackgroundColor(darkColor);
                }
                // Définit la taille et les marges de chaque case du damier
                chessboardSquare.setLayoutParams(new TableRow.LayoutParams(sizeSquare, sizeSquare));
                chessboardSquare.setPadding(marginSquare,marginSquare,marginSquare,marginSquare);
                chessboardSquare.setTag(0);
                int column = j;
                int row = i;
                // Ajoute un listener à chaque case pour permettre aux utilisateurs de placer ou retirer des pièces
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
                // Ajoute la case au damier
                chessboardRow.addView(chessboardSquare);
            }
            // Ajoute la rangée de cases au damier
            chessboard.addView(chessboardRow);
        }

        // Configuration du bouton "Nettoyer" pour afficher une boîte de dialogue de confirmation avant de supprimer toutes les pièces du damier
        ButtonListener cleanButtonListener = new ButtonListener(this);
        cleanButton.setOnClickListener(v -> {
            String title = "Souhaitez vraiment retirer toutes les dames ?";
            String message = "Cette action est rédhibitoire.";
            DialogInterface.OnClickListener positiveAction = (dialog, which) -> cleanChessboard();
            cleanButtonListener.showConfirmationDialogBoxRemovable(title, message, positiveAction);
        });
        cleanButtonListener.buttonPressAnimation(cleanButton, R.drawable.clean_button, R.drawable.clean_button_pressed);

        // Configuration du bouton pour activer l'aide visuelle "Afficher les dames attaquées"
        ButtonListener attackQueensHelpListener = new ButtonListener(this);
        showAttackQueensHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Cette aide permet d'afficher les dames attaquées par d'autres dames.";
            @SuppressLint("ClickableViewAccessibility") DialogInterface.OnClickListener positiveAction = (dialog, which) -> {
                showAttackQueensHelp = true;
                // Affiche visuellement les dames attaquées sur le damier
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
                // Désactive le bouton après activation de l'aide
                showAttackQueensHelpView.setColorFilter(Color.argb(63, 0, 0, 0));
                showAttackQueensHelpView.setOnClickListener(null);
                showAttackQueensHelpView.setOnTouchListener(null);
            };
            attackQueensHelpListener.showConfirmationDialogBox(title, message, positiveAction);
        });
        attackQueensHelpListener.buttonPressAnimation(showAttackQueensHelpView, R.drawable.show_attack_queens_help, R.drawable.show_attack_queens_help_pressed);

        // Configurer le bouton pour activer l'aide visuelle "Afficher le pourcentage de victoire"
        ButtonListener victoryPercentageHelpListener = new ButtonListener(this);
        victoryPercentageHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Cette aide permet d'afficher le pourcentage de victoire selon la position des dames posées.";
            @SuppressLint("ClickableViewAccessibility") DialogInterface.OnClickListener positiveAction = (dialog, which) -> {
                victoryPercentageView.setVisibility(View.VISIBLE);
                // Désactive le bouton après activation de l'aide
                victoryPercentageHelpView.setColorFilter(Color.argb(63, 0, 0, 0));
                victoryPercentageHelpView.setOnClickListener(null);
                victoryPercentageHelpView.setOnTouchListener(null);
            };
            victoryPercentageHelpListener.showConfirmationDialogBox(title, message, positiveAction);
        });
        victoryPercentageHelpListener.buttonPressAnimation(victoryPercentageHelpView, R.drawable.victory_percent_help_button, R.drawable.victory_percent_help_button_pressed);

        // Configuration du bouton pour activer l'aide visuelle "Placer des dames aléatoirement"
        ButtonListener queensPlacementHelpListener = new ButtonListener(this);
        queensPlacementHelpView.setOnClickListener(v -> {
            String title = "Souhaitez vraiment activer cette aide ?";
            String message = "Les dames seront réinitialisées et 3 nouvelles dames seront placées aléatoirement selon une combinaison gagnante.";
            DialogInterface.OnClickListener positiveAction = (dialog, which) -> placeRandomQueens(numberOfQueensHelp);
            queensPlacementHelpListener.showConfirmationDialogBoxRemovable(title, message, positiveAction);
        });
        queensPlacementHelpListener.buttonPressAnimation(queensPlacementHelpView, R.drawable.queen_placement_help, R.drawable.queen_placement_help_pressed);

        // Configuration du bouton "Confirmer" pour permettre aux utilisateurs de valider leur configuration finale du jeu
        ButtonListener confirmButtonListener = new ButtonListener(this);
        confirmButton.setOnClickListener(v -> {
            if (countQueen == 8) {
                String title = "Souhaitez vraiment confirmer ce jeu ?";
                String message = "";
                DialogInterface.OnClickListener positiveAction = (dialog, which) -> confirmPlay();
                confirmButtonListener.showConfirmationDialogBox(title, message, positiveAction);
            }
            else {
                // Affiche un message d'erreur si le nombre de dames est incorrect
                invalidPlayMessage.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> invalidPlayMessage.setVisibility(View.INVISIBLE), 2000);
            }
        });
        confirmButtonListener.buttonPressAnimation(confirmButton, R.drawable.confirm_button, R.drawable.confirm_button_pressed);

        // Création et configuration du lecteur multimédia pour jouer de la musique de fond
        mediaPlayer = MediaPlayer.create(this, R.raw.lofi);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Configuration du bouton pour activer ou désactiver le son
        ButtonListener soundButtonListener = new ButtonListener(this);
        soundButton.setOnClickListener(v -> {
            toggleSound();
            if (isMute){
                soundButtonListener.buttonPressAnimation(soundButton, R.drawable.sound_on, R.drawable.sound_on_pressed);
            }
            else {
                soundButtonListener.buttonPressAnimation(soundButton, R.drawable.sound_off, R.drawable.sound_off_pressed);
            }
        });
        soundButtonListener.buttonPressAnimation(soundButton, R.drawable.sound_off, R.drawable.sound_off_pressed);

        // Configuration du bouton "Paramètres" pour ouvrir un menu cosmétique
        ButtonListener settingsListener = new ButtonListener(this);
        settingsButton.setOnClickListener(v -> openCosmeticMenu());
        settingsListener.buttonPressAnimation(settingsButton, R.drawable.settings, R.drawable.settings_pressed);

        // Configure un écouteur pour détecter les changements de matériel dans l'application
        Material.setMaterialChangeListener(this);
    }

    /**
     * Appelée lorsque l'arrière-plan est modifié.
     * Définit l'arrière-plan de la vue de fond en fonction du thème Material.
     */
    @Override
    public void onBackgroundChanged() {
        backgroundView.setBackgroundResource(Material.getBackground());
    }

    /**
     * Appelée lorsque la couleur est modifiée.
     * Met à jour les couleurs claire et sombre en fonction du thème Material
     * et définit la couleur de fond des cases de l'échiquier en conséquence.
     */
    @Override
    public void onColorChanged() {
        lightColor = getColor(Material.getLightColor());
        darkColor = getColor(Material.getDarkColor());
        for (int i = 0; i < 8; i++) {
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
            for (int j = 0; j < 8; j++) {
                ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                int tag = (int) chessboardSquare.getTag();
                if ((i + j) % 2 == 0) {
                    if (tag >= 11){
                        chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                    }
                    else {
                        chessboardSquare.setBackgroundColor(lightColor);
                    }
                } else {
                    if (tag >= 11){
                        chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                    }
                    else {
                        chessboardSquare.setBackgroundColor(darkColor);
                    }
                }
            }
        }
    }

    /**
     * Appelée lorsque l'image de la reine est modifiée.
     * Met à jour l'image de la reine dans la vue de l'icône de la reine
     * et définit l'image de la reine dans les cases de l'échiquier correspondantes.
     */
    @Override
    public void onQueenImageChanged() {
        queenImage = Material.getQueenImage();
        queenImageView.setImageResource(queenImage);
        for (Integer queenPosition : currentQueensPositions) {
            int column = queenPosition / 10 - 1;
            int row = queenPosition - (column * 10) - 11;
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(row);
            ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(column);
            chessboardSquare.setImageResource(queenImage);
        }
    }


    /**
     * Ouvre le menu cosmétique de l'application.
     */
    public void openCosmeticMenu() {
        Intent intent = new Intent(this, CosmeticMenu.class);
        startActivity(intent);
    }

    /**
     * Active ou désactive le son du jeu.
     */
    public void toggleSound() {
        if (!isMute) {
            mediaPlayer.pause();
            isMute = true;
        } else {
            mediaPlayer.start();
            isMute = false;
        }
    }

    /**
     * Place une reine sur le carré de l'échiquier spécifié.
     *
     * @param chessboardSquare Le carré de l'échiquier où placer la reine.
     * @param row              Ligne du carré de l'échiquier.
     * @param column           Colonne du carré de l'échiquier.
     */
    @SuppressLint("SetTextI18n")
    private void placeQueen(ImageView chessboardSquare, int row, int column) {
        int squarePosition = (column + 1) * 10 + row + 1;
        int tag = (int) chessboardSquare.getTag();
        tag += 10;
        chessboardSquare.setTag(tag);
        countQueen++;
        updateQueenCount();
        currentQueensPositions.add(squarePosition);
        if (BrowseAttackedSquares(row, column, true)) {
            if ((int) chessboardSquare.getTag() >= 11) {
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                } else {
                    chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                }
            }
        }
        victoryPercentageView.setText(calculateVictoryPercentage() + "%");
        chessboardSquare.setImageResource(queenImage);
    }

    /**
     * Retire une reine du carré de l'échiquier spécifié.
     *
     * @param chessboardSquare Le carré de l'échiquier où se trouve la reine à retirer.
     * @param row              Ligne du carré de l'échiquier.
     * @param column           Colonne du carré de l'échiquier.
     */
    @SuppressLint("SetTextI18n")
    private void removeQueen(ImageView chessboardSquare, int row, int column) {
        int squarePosition = (column + 1) * 10 + row + 1;
        countQueen--;
        updateQueenCount();
        currentQueensPositions.remove((Object) squarePosition);
        int tag = (int) chessboardSquare.getTag();
        tag -= 10;
        if ((row + column) % 2 == 0) {
            chessboardSquare.setBackgroundColor(lightColor);
        } else {
            chessboardSquare.setBackgroundColor(darkColor);
        }
        BrowseAttackedSquares(row, column, false);
        chessboardSquare.setTag(tag);
        victoryPercentageView.setText(calculateVictoryPercentage() + "%");
        chessboardSquare.setImageDrawable(null);
    }

    /**
     * Met à jour le nombre de reines placées.
     */
    @SuppressLint("SetTextI18n")
    private void updateQueenCount() {
        textCountQueen.setText(8 - countQueen + "/8");
    }

    /**
     * Nettoie l'échiquier en retirant toutes les reines et en réinitialisant les tags et les couleurs des carrés.
     */
    @SuppressLint("SetTextI18n")
    private void cleanChessboard() {
        for (int i = 0; i < 8; i++) {
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
            for (int j = 0; j < 8; j++) {
                ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                chessboardSquare.setTag(0);
                chessboardSquare.setImageDrawable(null);
                if ((i + j) % 2 == 0) {
                    chessboardSquare.setBackgroundColor(lightColor);
                } else {
                    chessboardSquare.setBackgroundColor(darkColor);
                }
            }
        }
        countQueen = 0;
        updateQueenCount();
        victoryPercentageView.setText("100%");
        currentQueensPositions = new ArrayList<>();
    }

    /**
     * Parcourt les carrés de l'échiquier attaqués par une reine à la position spécifiée.
     *
     * @param row         Ligne du carré de l'échiquier.
     * @param column      Colonne du carré de l'échiquier.
     * @param isAttacking Vrai si une reine est placée, faux si une reine est retirée.
     * @return Vrai si des reines sont attaquées ; sinon, faux.
     */
    private boolean BrowseAttackedSquares(int row, int column, boolean isAttacking) {
        boolean areQueensAttacked = false;
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Directions pour les diagonales

        for (int i = 0; i < 8; i++) {
            if (i != column) {
                if (updateChessboardSquare(row, i, isAttacking)) {
                    areQueensAttacked = true;
                }
            }
            if (i != row) {
                if (updateChessboardSquare(i, column, isAttacking)) {
                    areQueensAttacked = true;
                }
            }
        }

        for (int[] direction : directions) {
            int tmpRow = row + direction[0];
            int tmpColumn = column + direction[1];

            while (tmpRow >= 0 && tmpRow < 8 && tmpColumn >= 0 && tmpColumn < 8) {
                if (updateChessboardSquare(tmpRow, tmpColumn, isAttacking)) {
                    areQueensAttacked = true;
                }
                tmpRow += direction[0];
                tmpColumn += direction[1];
            }
        }
        return areQueensAttacked;
    }


    /**
     * Met à jour le carré de l'échiquier à la position spécifiée selon que la reine est placée ou retirée.
     * Met à jour la couleur de fond du carré pour indiquer s'il est attaqué par une reine.
     *
     * @param row       Ligne du carré de l'échiquier.
     * @param column    Colonne du carré de l'échiquier.
     * @param increment Si vrai, incrémente le tag du carré ; sinon, décrémente le tag.
     * @return Vrai si le carré est attaqué par une reine ; sinon, faux.
     */
    private boolean updateChessboardSquare(int row, int column, boolean increment) {
        ImageView chessboardSquare = getChessboardSquare(row, column);
        updateSquareTag(chessboardSquare, increment);
        if (increment) {
            if (showAttackQueensHelp && (int) chessboardSquare.getTag() >= 11) {
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundResource(lightSquareColorWithBorder);
                } else {
                    chessboardSquare.setBackgroundResource(darkSquareColorWithBorder);
                }
                return true;
            }
        } else {
            if (showAttackQueensHelp && (int) chessboardSquare.getTag() == 10) {
                if ((row + column) % 2 == 0) {
                    chessboardSquare.setBackgroundColor(lightColor);
                } else {
                    chessboardSquare.setBackgroundColor(darkColor);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Met à jour le tag du carré de l'échiquier.
     *
     * @param chessboardSquare Le carré de l'échiquier à mettre à jour.
     * @param increment        Si vrai, incrémente le tag ; sinon, décrémente le tag.
     */
    private void updateSquareTag(ImageView chessboardSquare, boolean increment) {
        int tag = (int) chessboardSquare.getTag();
        tag += increment ? 1 : -1;
        chessboardSquare.setTag(tag);
    }

    /**
     * Récupère le carré de l'échiquier à la position spécifiée.
     *
     * @param row    Ligne du carré de l'échiquier.
     * @param column Colonne du carré de l'échiquier.
     * @return Le carré de l'échiquier à la position spécifiée.
     */
    private ImageView getChessboardSquare(int row, int column) {
        TableRow chessboardRow = (TableRow) chessboard.getChildAt(row);
        return (ImageView) chessboardRow.getChildAt(column);
    }

    /**
     * Calcule le pourcentage de victoire en fonction de la position actuelle des reines sur l'échiquier.
     *
     * @return Le pourcentage de victoire.
     */
    private int calculateVictoryPercentage() {
        int countPossibility = 0;
        if (victoryPossibilityList.size() == 0) {
            return 100;
        }
        for (int[] victoryPossibility : victoryPossibilityList) {
            boolean isPossible = true;
            for (Integer queenPosition : currentQueensPositions) {
                int column = queenPosition / 10 - 1;
                int row = queenPosition - (column * 10) - 11;
                if (victoryPossibility[column] != row) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible) {
                countPossibility++;
            }
        }
        return Math.round((float) countPossibility / 92 * 100);
    }

    /**
     * Place un nombre spécifié de reines de manière aléatoire sur l'échiquier.
     *
     * @param number Le nombre de reines à placer.
     */
    private void placeRandomQueens(int number) {
        int randomPossibilityIndex = (int) (Math.random() * 92);
        int[] randomPossibility = victoryPossibilityList.get(randomPossibilityIndex);
        cleanChessboard();
        ArrayList<Integer> indexQueens = new ArrayList<>();
        while (indexQueens.size() < number) {
            int valeur = (int) (Math.random() * 8);
            if (!indexQueens.contains(valeur)) {
                indexQueens.add(valeur);
            }
        }
        for (int i = 0; i < number; i++) {
            int row = randomPossibility[indexQueens.get(i)];
            int column = indexQueens.get(i);
            ImageView chessboardSquare = getChessboardSquare(row, column);
            placeQueen(chessboardSquare, row, column);
        }
    }

    /**
     * Confirme le placement des reines et détermine si c'est une victoire ou une défaite.
     * Lance une nouvelle activité en conséquence.
     */
    private void confirmPlay() {
        Intent intent = new Intent(MainActivity.this, WinLooseActivity.class);
        boolean isVictory = true;
        for (int i = 0; i < 8; i++) {
            TableRow chessboardRow = (TableRow) chessboard.getChildAt(i);
            for (int j = 0; j < 8; j++) {
                ImageView chessboardSquare = (ImageView) chessboardRow.getChildAt(j);
                if ((int) chessboardSquare.getTag() > 10) {
                    isVictory = false;
                }
            }
        }
        intent.putExtra("isVictory", isVictory);
        finish();
        startActivity(intent);
    }


    /**
     * Met en pause la lecture de la musique lorsque l'activité est mise en pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    /**
     * Redémarre la lecture de la musique si elle n'est pas en mode muet et n'est pas déjà en cours de lecture lorsque l'activité démarre.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!isMute && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * Reprend la lecture de la musique si elle n'est pas en mode muet, si un lecteur multimédia existe et si elle n'est pas déjà en cours de lecture lorsque l'activité reprend.
     * Appelle des méthodes pour mettre à jour l'apparence.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!isMute && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        onBackgroundChanged();
        onColorChanged();
        onQueenImageChanged();
    }

    /**
     * Arrête la lecture de la musique et libère les ressources associées lorsque l'activité est arrêtée.
     */
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    /**
     * Redémarre la lecture de la musique en créant un nouveau lecteur multimédia avec la musique spécifiée et démarre la lecture en boucle lorsque l'activité redémarre.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer = MediaPlayer.create(this, R.raw.lofi);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * Supprime toutes les vues du layout de l'échiquier et arrête la lecture de la musique, libère les ressources associées si un lecteur multimédia existe, lorsque l'activité est détruite.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        chessboard.removeAllViews();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}