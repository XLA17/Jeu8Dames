package com.example.jeu8dames;

/**
 * La classe Material contient les configurations visuelles de l'application.
 */
public class Material {
    // Variables statiques pour stocker les valeurs des ressources visuelles
    private static int queenImage = R.drawable.queen;
    private static int lightColor = R.color.beige;
    private static int darkColor = R.color.brown;
    private static int background = R.drawable.main_background;
    private static int backgroundLightWithColor = R.drawable.background_beige_with_border;
    private static int backgroundDarkWithColor = R.drawable.background_brown_with_border;
    private static CosmeticListener listener;

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
     * Renvoie l'ID de l'arrière-plan sombre actuellement défini.
     *
     * @return  L'ID de l'arrière-plan sombre avec border.
     */
    public static int getBackgroundLightWithColor(){
        return backgroundLightWithColor;
    }

    /**
     * Renvoie l'ID de l'arrière-plan clair actuellement défini.
     *
     * @return  L'ID de l'arrière-plan clair avec border.
     */
    public static int getBackgroundDarkWithColor(){
        return backgroundDarkWithColor;
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
     * Définit l'ID de l'arrière-plan avec couleur claire.
     *
     * @param backgroundLightWithColor L'ID du nouvel arrière-plan avec couleur claire.
     */
    public static void setBackgroundLightWithColor(int backgroundLightWithColor) {
        Material.backgroundLightWithColor = backgroundLightWithColor;
        // Si un observateur est défini, déclenche l'événement de changement d'arrière-plan
        if (listener != null) {
            listener.onBackgroundChanged();
        }
    }

    /**
     * Définit l'ID de l'arrière-plan avec couleur sombre.
     *
     * @param backgroundDarkWithColor L'ID du nouvel arrière-plan avec couleur sombre.
     */
    public static void setBackgroundDarkWithColor(int backgroundDarkWithColor) {
        Material.backgroundDarkWithColor = backgroundDarkWithColor;
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
    public static void setMaterialChangeListener(CosmeticListener materialChangeListener) {
        listener = materialChangeListener;
    }
}
