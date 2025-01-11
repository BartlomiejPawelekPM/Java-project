package com.example.logowanie;

import javafx.stage.Stage;
import com.example.logowanie.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Loginy {
    public Loginy(Stage primaryStage, Main main) {
        Label usernameLabel = new Label("Nazwa użytkownika:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Hasło:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Zaloguj");
        Hyperlink registerLink = new Hyperlink("Zarejestruj się");
        Label messageLabel = new Label();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registerLink, 1, 3);
        gridPane.add(messageLabel, 1, 4);

        // Obsługa logowania
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (authenticate(username, password)) {
                messageLabel.setText("Logowanie powiodło się!");
            } else {
                messageLabel.setText("Niepoprawne dane logowania.");
            }
        });
        registerLink.setOnAction(event -> main.openRegistrationWindow());

        // Ustawienie sceny
        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logowanie");
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}