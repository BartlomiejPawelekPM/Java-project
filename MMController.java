package com.example.logowanie;
//Main menu controller
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MMController {

    private final LanguageManager languageManager = new LanguageManager(); // Instancja LanguageManager

    @FXML
    private Button languageButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        // Ustawienie początkowego tekstu na podstawie języka
        updateTexts();

        // Nasłuchiwanie zmian języka i dynamiczna aktualizacja tekstów
        languageManager.isEnglishProperty().addListener((obs, oldVal, newVal) -> updateTexts());

        // Zmiana języka po kliknięciu przycisku
        languageButton.setOnAction(e -> languageManager.setLanguage(!languageManager.isEnglish()));
    }

    private void updateTexts() {
        // Ustawienie tekstów dla elementów interfejsu
        languageButton.setText(languageManager.getText("menu.language"));
        titleLabel.setText(languageManager.getText("menu.select.option"));
    }
}

