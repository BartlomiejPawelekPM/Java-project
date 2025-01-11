package com.example.logowanie;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Rejestracja {
    public Rejestracja(Stage primaryStage, Main main) {
        Label usernameLabel = new Label("Nazwa użytkownika:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Hasło:");
        PasswordField passwordField = new PasswordField();
        Label confirmPasswordLabel = new Label("Potwierdź hasło:");
        PasswordField confirmPasswordField = new PasswordField();
        Button registerButton = new Button("Zarejestruj");
        Hyperlink backToLoginLink = new Hyperlink("Powrót do logowania");
        Label messageLabel = new Label();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPasswordLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(registerButton, 1, 3);
        gridPane.add(backToLoginLink, 1, 4);
        gridPane.add(messageLabel, 1, 5);

        // Obsługa rejestracji
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Hasła nie pasują!");
                return;
            }

            if (registerUser(username, password)) {
                messageLabel.setText("Rejestracja zakończona sukcesem!");
            } else {
                messageLabel.setText("Błąd podczas rejestracji.");
            }
        });

        // Powrót do logowania
        backToLoginLink.setOnAction(event -> main.openLoginWindow());

        // Ustawienie sceny
        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rejestracja");
        primaryStage.show();
    }

    private boolean registerUser(String username, String password) {
        System.out.println("Rejestrowanie użytkownika: " + username);
        return true;
    }
}
