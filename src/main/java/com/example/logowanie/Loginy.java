package com.example.logowanie;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.logowanie.Main;
import com.example.logowanie.MSController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
//import org.controlsfx.control.spreadsheet.Grid;

public class Loginy {
    public Loginy(Stage primaryStage, Main main) {

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Zaloguj");
        Hyperlink registerLink = new Hyperlink("Zarejestruj się");
        Label messageLabel = new Label();

        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(40);

        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(40);

        loginButton.setPrefWidth(200);
        loginButton.setPrefHeight(50);

        usernameField.setStyle("-fx-font-size: 18px; -fx-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-font-size: 18px; -fx-text-fill: #B3B3B3;");
        loginButton.setStyle("-fx-background-color: #1DB954; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        registerLink.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-underline: true;");
        registerLink.setFont(new Font("Arial", 12));

        usernameField.setPromptText("Login");
        passwordField.setPromptText("Hasło");

        usernameField.setStyle("-fx-prompt-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-prompt-text-fill: #B3B3B3;");

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
        gridPane.add(logoBox,1,0);

        gridPane.add(usernameField, 1, 1);

        StackPane passwordStackPane = new StackPane();
        passwordStackPane.setPrefWidth(300);
        passwordStackPane.setPrefHeight(40);

        passwordField.setPromptText("Hasło");

        Button togglePasswordVisibilityButton = new Button("👁️");
        togglePasswordVisibilityButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #B3B3B3; -fx-font-size: 20px; -fx-border-width: 0;");
        togglePasswordVisibilityButton.setMaxWidth(30);
        togglePasswordVisibilityButton.setMinWidth(30);

        togglePasswordVisibilityButton.setOnAction(event -> {
            if (passwordField.getText().isEmpty()) return; // Zapobiega zmianie ikony, gdy pole hasła jest puste

            if (passwordField.isVisible()) {
                passwordField.setVisible(false);  // Ukrywamy hasło
                passwordField.setManaged(false);  // Nie zarządzamy polem, aby nie zajmowało miejsca
                togglePasswordVisibilityButton.setText("🚫"); // Zmieniamy ikonę na przekreślone oczko
            } else {
                passwordField.setVisible(true);   // Pokazujemy hasło
                passwordField.setManaged(true);   // Ponownie zarządzamy polem
                togglePasswordVisibilityButton.setText("👁️"); // Zmieniamy ikonę na oczko
            }
        });

        passwordStackPane.getChildren().addAll(passwordField, togglePasswordVisibilityButton);

        gridPane.add(passwordStackPane, 1, 2);

        //gridPane.add(passwordField, 1, 2);

        gridPane.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.CENTER);

        gridPane.add(registerLink, 1, 8);
        GridPane.setHalignment(registerLink, HPos.CENTER);

        gridPane.add(messageLabel, 1, 5);
        GridPane.setHalignment(messageLabel, HPos.CENTER);

        gridPane.setPadding(new Insets(20,20,20,20));

        messageLabel.setAlignment(Pos.CENTER);
        loginButton.setAlignment(Pos.CENTER);
        registerLink.setAlignment(Pos.CENTER);

        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));

        messageLabel.setTextFill(Color.WHITE);
        registerLink.setTextFill(Color.WHITE);


        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));

        registerLink.setStyle("-fx-underline: true;");

        // Obsługa logowania
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            MSController msController = new MSController();

            if (MSController.login(username, password)) {
                messageLabel.setText("Logowanie powiodło się!");
                messageLabel.setTextFill(Color.GREEN);
            } else {
                messageLabel.setText("Niepoprawne dane logowania.");
                messageLabel.setTextFill(Color.RED);
            }
        });
        registerLink.setOnAction(event -> main.openRegistrationWindow());

        // Ustawienie sceny
        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logowanie");
        primaryStage.show();
    }
}