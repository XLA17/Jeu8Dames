package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ButtonListener {
    private AppCompatActivity activity;
    private boolean isRemoved;

    public ButtonListener(AppCompatActivity activity) {
        this.activity = activity;
        this.isRemoved = false;
    }

    public void showConfirmationDialogBox(String title, String message, DialogInterface.OnClickListener positiveAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Confirmer", positiveAction)
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
                negativeButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
            }
        });
        dialog.show();
    }

    public void showConfirmationDialogBoxRemovable(String title, String message, DialogInterface.OnClickListener positiveAction) {
        Log.i("TAG", "isRemoved: " + isRemoved);
        if (!this.isRemoved){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogBoxView = inflater.inflate(R.layout.custom_dialog_box_layout, null);
            CheckBox checkBox = dialogBoxView.findViewById(R.id.checkbox_ne_plus_afficher);
            builder.setView(dialogBoxView)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Confirmer", positiveAction)
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            if (checkBox.isChecked()){
                this.isRemoved = true;
            }
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    positiveButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
                    negativeButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));

                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveAction.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            if (checkBox.isChecked()){
                                isRemoved = true;
                            }
                            dialog.cancel();
                        }
                    });
                }
            });
            dialog.show();
        }
        else {
            positiveAction.onClick(null, DialogInterface.BUTTON_POSITIVE);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void buttonPressAnimation(ImageView imageView, int resID, int resID_pressed){
        Animation pressed_button = AnimationUtils.loadAnimation(activity, R.anim.pressed_button);
        Animation released_button = AnimationUtils.loadAnimation(activity, R.anim.released_button);
        pressed_button.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Ne rien faire ici
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setScaleY(0.95f);
                imageView.setImageResource(resID_pressed);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Ne rien faire ici
            }
        });

        imageView.setOnTouchListener((v, event) -> {
            Animation animation;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Lorsque le bouton est pressé
                    imageView.startAnimation(pressed_button);
                    imageView.setPivotX((float) imageView.getWidth() / 2);
                    imageView.setPivotY(imageView.getHeight());
                    return true;
                case MotionEvent.ACTION_UP:
                    // Lorsque le bouton est relâché
                    imageView.startAnimation(released_button);
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                    imageView.setImageResource(resID);
                    imageView.performClick();
                    return true;
            }
            return false;
        });
    }
}
