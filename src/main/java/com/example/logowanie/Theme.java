package com.example.logowanie;

import javafx.scene.Scene;

public class Theme {
    private boolean isDarkMode;

    public Theme() {
        this.isDarkMode = false;
    }

    public boolean isDark() {
        return isDarkMode;
    }

    public void toggleTheme() {
        isDarkMode = !isDarkMode;
    }

    public void applyTheme(Scene scene) {
        if (isDarkMode) {
            scene.getRoot().setStyle("-fx-background-color: #1e1e2f; -fx-text-fill: #e0e0e0;");
        } else {
            scene.getRoot().setStyle("-fx-background-color: #f5f7fa; -fx-text-fill: #2d2d2d;");
        }

        applyThemeToBlocks(scene);
    }

    private void applyThemeToBlocks(Scene scene) {
        scene.getRoot().lookupAll(".block-panel").forEach(node -> {
            if (isDarkMode) {
                node.setStyle(
                        "-fx-background-color: #252539;" +
                                "-fx-border-color: #3e3e55;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;" +
                                "-fx-text-fill: #e0e0e0;"
                );
            } else {
                node.setStyle(
                        "-fx-background-color: #ffffff;" +
                                "-fx-border-color: #dcdfe6;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);" +
                                "-fx-text-fill: #2d2d2d;"
                );
            }
        });

        scene.getRoot().lookupAll(".button").forEach(node -> {
                node.setStyle(
                        "-fx-background-color: #3e3e55;" +
                                "-fx-text-fill: white;" +
                                "-fx-border-radius: 30;" +
                                "-fx-background-radius: 30;" +
                                "-fx-padding: 8 15;" +
                                "-fx-font-weight: bold;" +
                                "-fx-border-color: white;" +
                                "-fx-border_width: 1;"
                );
        });
    }
}