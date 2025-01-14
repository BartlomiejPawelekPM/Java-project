package com.example.logowanie;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

        usernameField.setPrefWidth(200);
        passwordField.setPrefWidth(200);
        confirmPasswordField.setPrefWidth(200);
        registerButton.setPrefWidth(200);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.setAlignment(Pos.CENTER);

        //Image logoImage = new Image("");
        //ImageView logoView = new ImageView(logoImage);
        //logoView.setFitWidth(100);
        //logoView.setPreserveRatio(true);

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPasswordLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(registerButton, 1, 3);
        gridPane.add(backToLoginLink, 1, 4);
        gridPane.add(messageLabel, 1, 5);

        usernameLabel.setFont(new Font("Arial", 14));
        passwordField.setFont(new Font("Arial", 14));
        confirmPasswordLabel.setFont(new Font("Arial", 14));
        messageLabel.setFont(new Font("Arial", 14));
        registerButton.setFont(new Font("Arial", 14));
        backToLoginLink.setFont(new Font("Arial", 12));

        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));

        usernameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        confirmPasswordLabel.setTextFill(Color.WHITE);
        messageLabel.setTextFill(Color.WHITE);
        backToLoginLink.setTextFill(Color.WHITE);

        usernameField.setStyle("-fx-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-text-fill: #B3B3B3;");
        confirmPasswordField.setStyle("-fx-text-fill: #B3B3B3;");

        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white;"));

        backToLoginLink.setStyle("-fx-underline: true");

        // Obsługa rejestracji
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Pola są puste!");
                return;
            }

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
        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rejestracja");
        primaryStage.show();
    }

    private boolean registerUser(String username, String password) {
        System.out.println("Rejestrowanie użytkownika: " + username);
        return true;
    }
}
