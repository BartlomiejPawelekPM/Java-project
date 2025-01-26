package com.example.logowanie;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Theme {
    private boolean isDarkMode;

    public Theme() {
        // Domyślnie ustawiamy jasny motyw
        this.isDarkMode = false;
    }

    public boolean isDark() {
        return isDarkMode;
    }

    // Metoda do zmiany motywu
    public void toggleTheme() {
        isDarkMode = !isDarkMode;  // Zmieniamy tryb na ciemny/jasny
    }

    // Metoda do zastosowania motywu na scenie
    public void applyTheme(Scene scene) {
        if (isDarkMode) {
            scene.getRoot().setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #ffffff;");
        } else {
            scene.getRoot().setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #000000;");
        }

        applyThemeToBlocks(scene);
    }

    private void applyThemeToBlocks(Scene scene) {
        scene.getRoot().lookupAll(".block-panel").forEach(node -> {
            if (isDarkMode) {
                node.setStyle("-fx-background-color: #2e2e2e;");
            } else {
                node.setStyle("-fx-background-color: #f0f0f0;");
            }
        });
    }

}

