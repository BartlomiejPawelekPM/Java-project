package com.example.logowanie;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.logowanie.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import com.example.logowanie.MSController;

public class Rejestracja {
    public Rejestracja(Stage primaryStage, Main main) {

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        TextField mail = new TextField();
        Button registerButton = new Button("Zarejestruj");
        Hyperlink backToLoginLink = new Hyperlink("Powrót do logowania");
        Label messageLabel = new Label();

        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(40);

        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(40);

        confirmPasswordField.setPrefWidth(300);
        confirmPasswordField.setPrefHeight(40);

        registerButton.setPrefWidth(200);
        registerButton.setPrefHeight(50);

        usernameField.setStyle("-fx-font-size: 18px; -fx-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-font-size: 18px; -fx-text-fill: #B3B3B3;");
        confirmPasswordField.setStyle("-fx-font-size: 18px; -fx-text-fill: #B3B3B3;");
        registerButton.setStyle("-fx-background-color: #1DB954; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        backToLoginLink.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-underline: true;");
        backToLoginLink.setFont(new Font("Arial", 12));

        usernameField.setPromptText("Login");
        passwordField.setPromptText("Hasło");
        confirmPasswordField.setPromptText("Potwierdź hasło");

        usernameField.setStyle("-fx-prompt-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-prompt-text-fill: #B3B3B3;");
        confirmPasswordField.setStyle("-fx-prompt-text-fill: #B3B3B3;");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setMaxWidth(Double.MAX_VALUE);

        ImageView logoView = new ImageView(Logo.getLogoImage());
        logoView.setPreserveRatio(true);

        Text NaszaMarka = new Text("JBTSound");
        NaszaMarka.setFont(new Font("Arial", 20));
        NaszaMarka.setFill(Color.web("#1DB954"));

        double logoWidth = NaszaMarka.getBoundsInLocal().getWidth();
        logoView.setFitWidth(logoWidth);

        logoBox.getChildren().addAll(logoView, NaszaMarka);

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());

        TextField visibleConfirmPasswordField = new TextField();
        visibleConfirmPasswordField.setManaged(false);
        visibleConfirmPasswordField.setVisible(false);
        visibleConfirmPasswordField.textProperty().bindBidirectional(confirmPasswordField.textProperty());

        Button togglePasswordVisibilityButton = new Button("👁️");
        togglePasswordVisibilityButton.setOnAction(event -> {
            boolean isPasswordVisible = visiblePasswordField.isVisible();
            visiblePasswordField.setVisible(!isPasswordVisible);
            visiblePasswordField.setManaged(!isPasswordVisible);
            passwordField.setVisible(isPasswordVisible);
            passwordField.setManaged(isPasswordVisible);
        });

        Button toggleConfirmPasswordVisibilityButton = new Button("👁️");
        toggleConfirmPasswordVisibilityButton.setOnAction(event -> {
            boolean isPasswordVisible = visibleConfirmPasswordField.isVisible();
            visibleConfirmPasswordField.setVisible(!isPasswordVisible);
            visibleConfirmPasswordField.setManaged(!isPasswordVisible);
            confirmPasswordField.setVisible(isPasswordVisible);
            confirmPasswordField.setManaged(isPasswordVisible);
        });

        HBox passwordBox = new HBox(5, passwordField, visiblePasswordField, togglePasswordVisibilityButton);
        HBox confirmPasswordBox = new HBox(5, confirmPasswordField, visibleConfirmPasswordField, toggleConfirmPasswordVisibilityButton);

        gridPane.add(logoBox, 1, 0);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(registerButton, 1, 4);
        GridPane.setHalignment(registerButton, HPos.CENTER);

        gridPane.add(backToLoginLink, 1, 8);
        GridPane.setHalignment(backToLoginLink, HPos.CENTER);

        gridPane.add(messageLabel, 1, 5);
        GridPane.setHalignment(messageLabel, HPos.CENTER);

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));

        messageLabel.setTextFill(Color.WHITE);
        backToLoginLink.setTextFill(Color.WHITE);

        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));

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

            if (MSController.register(username, password)) {
                messageLabel.setText("Rejestracja zakończona sukcesem!");
            } else {
                messageLabel.setText("Błąd podczas rejestracji lub użytkownik już istnieje.");
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

}
