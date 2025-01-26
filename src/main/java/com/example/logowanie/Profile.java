package com.example.logowanie;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.logowanie.Main;

public class Profile {
    private final Stage stage;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Main main;

    public Profile(Stage stage, LanguageManager languageManager, Theme themeManager, Main main) {
        this.stage = stage;
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.main = main;

        initializeProfileView();
    }

    private void initializeProfileView() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        Label profileLabel = new Label(languageManager.getText("menu.profile"));
        profileLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #4682B4;");

        Button backButton = new Button(languageManager.getText("menu.back"));
        backButton.setOnAction(e -> goBackToMenu());

        root.getChildren().addAll(profileLabel,  backButton);

        Scene scene = new Scene(root, 900, 600);
        themeManager.applyTheme(scene);
        stage.setTitle(languageManager.getText("menu.profile"));
        stage.setScene(scene);
        stage.show();
    }

    private void goBackToMenu() {
        try {
            new Menuadmin(stage, languageManager, themeManager, main);
        } catch (Exception e) {
            showError("Error returning to menu: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageManager.getText("error.title"));
        alert.setHeaderText(languageManager.getText("error.header"));
        alert.setContentText(message);
        alert.showAndWait();
    }
}

