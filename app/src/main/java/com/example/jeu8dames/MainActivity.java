package com.example.jeu8dames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    List<int[]> victoryPossibilities = new ArrayList<>();
    int[] currentPossibility = {-1, -1, -1, -1, -1, -1, -1, -1};
    TableLayout tl;
    int countQueen = 0;
    MediaPlayer mediaPlayer;
    ImageView toggleSoundButton; // Changement du type de bouton à ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize media player and start playing audio
        mediaPlayer = MediaPlayer.create(this, R.raw.lofi); // Change "your_audio_file" to the name of your audio file in res/raw
        mediaPlayer.setLooping(true); // Loop the audio
        mediaPlayer.start();

        // Initialize toggle sound button
        toggleSoundButton = findViewById(R.id.sonButton); // Récupérer le ImageView associé à l'ID sonButton

        tl = findViewById(R.id.tableLayout);

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

        for (int i = 0; i < 8; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            for (int j = 0; j < 8; j++) {
                ImageView imageView = new ImageView(this);
                int colorRes = (i % 2 == 0) ? ((j % 2 == 0) ? R.color.beige : R.color.brown)
                        : ((j % 2 == 0) ? R.color.brown : R.color.beige);
                imageView.setBackgroundColor(getResources().getColor(colorRes));
                imageView.setLayoutParams(new TableRow.LayoutParams(100, 100));
                int colonne = j;
                int ligne = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countQueen < 8) {
                            if (imageView.getDrawable() == null) {
                                currentPossibility[colonne] = ligne;
                                countQueen++;
                                imageView.setImageResource(R.drawable.queen);
                            }
                            else {
                                currentPossibility[colonne] = 0;
                                countQueen--;
                                imageView.setImageDrawable(null);
                            }
                        }
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

    public void toggleSound(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}
