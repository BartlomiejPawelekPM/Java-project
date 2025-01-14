package com.example.logowanie;

import javafx.stage.Stage;
import com.example.logowanie.Main;
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

public class Loginy {
    public Loginy(Stage primaryStage, Main main) {
        Label usernameLabel = new Label("Nazwa użytkownika:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Hasło:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Zaloguj");
        Hyperlink registerLink = new Hyperlink("Zarejestruj się");
        Label messageLabel = new Label();

        usernameField.setPrefWidth(200);
        passwordField.setPrefWidth(200);
        loginButton.setPrefWidth(200);

        usernameLabel.setFont(new Font("Arial", 14));
        passwordLabel.setFont(new Font("Arial", 14));
        loginButton.setFont(new Font("Arial", 14));
        registerLink.setFont(new Font("Arial", 12));

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
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registerLink, 1, 3);
        gridPane.add(messageLabel, 1, 4);

        usernameLabel.setAlignment(Pos.CENTER);
        passwordLabel.setAlignment(Pos.CENTER);
        messageLabel.setAlignment(Pos.CENTER);
        loginButton.setAlignment(Pos.CENTER);
        registerLink.setAlignment(Pos.CENTER);

        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));

        usernameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        messageLabel.setTextFill(Color.WHITE);
        registerLink.setTextFill(Color.WHITE);

        usernameField.setStyle("-fx-text-fill: #B3B3B3;");
        passwordField.setStyle("-fx-text-fill: #B3B3B3;");

        loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white;");

        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white;"));

        registerLink.setStyle("-fx-underline: true;");

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
        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logowanie");
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}