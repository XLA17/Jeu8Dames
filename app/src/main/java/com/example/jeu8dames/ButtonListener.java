package com.example.jeu8dames;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Cette classe gère les événements de clic et les animations des boutons dans l'application.
 */
public class ButtonListener {
    private final AppCompatActivity activity;
    private boolean isRemoved;

    /**
     * Constructeur de la classe ButtonListener.
     *
     * @param activity L'activité parent à laquelle le ButtonListener est attaché.
     */
    public ButtonListener(AppCompatActivity activity) {
        this.activity = activity;
        this.isRemoved = false;
    }

    /**
     * Affiche une boîte de dialogue de confirmation.
     *
     * @param title          Le titre de la boîte de dialogue.
     * @param message        Le message à afficher dans la boîte de dialogue.
     * @param positiveAction L'action à effectuer lorsque le bouton "Confirmer" est cliqué.
     */
    public void showConfirmationDialogBox(String title, String message, DialogInterface.OnClickListener positiveAction) {
        // Construction de la boîte de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Confirmer", positiveAction)
                .setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        // Personnalisation de l'apparence des boutons
        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
            negativeButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
        });
        dialog.show();
    }

    /**
     * Affiche une boîte de dialogue de confirmation avec une option pour ne plus l'afficher.
     *
     * @param title          Le titre de la boîte de dialogue.
     * @param message        Le message à afficher dans la boîte de dialogue.
     * @param positiveAction L'action à effectuer lorsque le bouton "Confirmer" est cliqué.
     */
    public void showConfirmationDialogBoxRemovable(String title, String message, DialogInterface.OnClickListener positiveAction) {
        // Vérifie si la boîte de dialogue a déjà été retirée
        if (!this.isRemoved) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogBoxView = inflater.inflate(R.layout.custom_dialog_box_layout, null);
            CheckBox checkBox = dialogBoxView.findViewById(R.id.checkbox_ne_plus_afficher);
            builder.setView(dialogBoxView)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Confirmer", positiveAction)
                    .setNegativeButton("Annuler", (dialog, which) -> {
                        // Vérifie si l'option "Ne plus afficher" est cochée
                        if (checkBox.isChecked()) {
                            isRemoved = true;
                        }
                        dialog.cancel();
                    });

            // Si l'option "Ne plus afficher" est cochée, retire la boîte de dialogue
            if (checkBox.isChecked()) {
                this.isRemoved = true;
            }
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialogInterface -> {
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));
                negativeButton.setTextColor(activity.getResources().getColor(android.R.color.black, activity.getTheme()));

                // Action lors du clic sur le bouton "Confirmer"
                positiveButton.setOnClickListener(v -> {
                    positiveAction.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    // Vérifie si l'option "Ne plus afficher" est cochée
                    if (checkBox.isChecked()) {
                        isRemoved = true;
                    }
                    dialog.cancel();
                });
            });
            dialog.show();
        } else {
            // Si la boîte de dialogue a été retirée, exécute l'action positive directement
            positiveAction.onClick(null, DialogInterface.BUTTON_POSITIVE);
        }
    }

    /**
     * Applique une animation lorsqu'un bouton est pressé.
     *
     * @param imageView     L'image représentant le bouton.
     * @param resID         L'ID de l'image lorsque le bouton n'est pas pressé.
     * @param resID_pressed L'ID de l'image lorsque le bouton est pressé.
     */
    @SuppressLint("ClickableViewAccessibility")
    public void buttonPressAnimation(ImageView imageView, int resID, int resID_pressed) {
        Animation pressed_button = AnimationUtils.loadAnimation(activity, R.anim.pressed_button);
        Animation released_button = AnimationUtils.loadAnimation(activity, R.anim.released_button);
        pressed_button.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setScaleY(0.95f);
                imageView.setImageResource(resID_pressed);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        imageView.setOnTouchListener((v, event) -> {
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
