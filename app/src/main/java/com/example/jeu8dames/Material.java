package com.example.jeu8dames;

/**
 * La classe Material contient les configurations visuelles de l'application.
 */
public class Material {
    /**
     * L'interface MaterialChangeListener permet d'écouter les changements de configuration visuelle.
     */
    public interface MaterialChangeListener {
        /**
         * Appelée lorsqu'une nouvelle image de dame est définie.
         */
        void onQueenImageChanged();

        /**
         * Appelée lorsqu'une nouvelle couleur est définie.
         */
        void onColorChanged();

        /**
         * Appelée lorsqu'un nouvel arrière-plan est défini.
         */
        void onBackgroundChanged();
    }

    // Variables statiques pour stocker les valeurs des ressources visuelles
    private static int queenImage = R.drawable.queen;
    private static int lightColor = R.color.beige;
    private static int darkColor = R.color.brown;
    private static int background = R.drawable.main_background;
    private static MaterialChangeListener listener;

    /**
     * Renvoie l'ID de l'image de la dame actuellement définie.
     *
     * @return L'ID de l'image de la dame.
     */
    public static int getQueenImage() {
        return queenImage;
    }

    /**
     * Renvoie l'ID de la couleur claire actuellement définie.
     *
     * @return L'ID de la couleur claire.
     */
    public static int getLightColor() {
        return lightColor;
    }

    /**
     * Renvoie l'ID de la couleur sombre actuellement définie.
     *
     * @return L'ID de la couleur sombre.
     */
    public static int getDarkColor() {
        return darkColor;
    }

    /**
     * Renvoie l'ID de l'arrière-plan actuellement défini.
     *
     * @return L'ID de l'arrière-plan.
     */
    public static int getBackground() {
        return background;
    }

    /**
     * Définit l'ID de l'image de la dame.
     *
     * @param resId L'ID de la nouvelle image de la dame.
     */
    public static void setQueenImage(int resId) {
        queenImage = resId;
        // Si un observateur est défini, déclenche l'événement de changement d'image de la dame
        if (listener != null) {
            listener.onQueenImageChanged();
        }
    }

    /**
     * Définit l'ID de la couleur claire.
     *
     * @param resId L'ID de la nouvelle couleur claire.
     */
    public static void setLightColor(int resId) {
        lightColor = resId;
        // Si un observateur est défini, déclenche l'événement de changement de couleur
        if (listener != null) {
            listener.onColorChanged();
        }
    }

    /**
     * Définit l'ID de la couleur sombre.
     *
     * @param resId L'ID de la nouvelle couleur sombre.
     */
    public static void setDarkColor(int resId) {
        darkColor = resId;
        // Si un observateur est défini, déclenche l'événement de changement de couleur
        if (listener != null) {
            listener.onColorChanged();
        }
    }

    /**
     * Définit l'ID de l'arrière-plan.
     *
     * @param resId L'ID du nouvel arrière-plan.
     */
    public static void setBackground(int resId) {
        background = resId;
        // Si un observateur est défini, déclenche l'événement de changement d'arrière-plan
        if (listener != null) {
            listener.onBackgroundChanged();
        }
    }

    /**
     * Définit l'observateur pour les changements de matériel.
     *
     * @param materialChangeListener L'observateur à définir.
     */
    public static void setMaterialChangeListener(MaterialChangeListener materialChangeListener) {
        listener = materialChangeListener;
    }
}
