package com.example.logowanie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MMController {

    private final LanguageManager languageManager = new LanguageManager();

    @FXML
    private Button languageButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        updateTexts();

        languageManager.isEnglishProperty().addListener((obs, oldVal, newVal) -> updateTexts());

        languageButton.setOnAction(e -> languageManager.setLanguage(!languageManager.isEnglish()));
    }

    private void updateTexts() {
        languageButton.setText(languageManager.getText("menu.language"));
        titleLabel.setText(languageManager.getText("menu.select.option"));
    }
}

