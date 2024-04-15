package com.example.jeu8dames;

/**
 * L'interface CosmeticListener permet d'écouter les changements de configuration visuelle.
 */
public interface CosmeticListener {
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
