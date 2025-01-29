package com.example.logowanie;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
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
import javafx.util.Duration;

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

        usernameField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #B3B3B3; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-prompt-text-fill: #B3B3B3;"
        );

        passwordField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #B3B3B3; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-prompt-text-fill: #B3B3B3;"
        );

        confirmPasswordField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #B3B3B3; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-prompt-text-fill: #B3B3B3;"
        );

        registerButton.setStyle("-fx-background-color: #1DB954; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        backToLoginLink.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-underline: true;");
        backToLoginLink.setFont(new Font("Arial", 12));

        usernameField.setPromptText("Login");
        passwordField.setPromptText("Hasło");
        confirmPasswordField.setPromptText("Potwierdź hasło");

        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #1DB954; " +       // Kolor podświetlenia
                                "-fx-border-radius: 5px; " +          // Zaokrąglone rogi
                                "-fx-border-width: 2px; " +           // Grubość obramowania
                                "-fx-text-fill: white; " +             // Kolor tekstu
                                "-fx-font-size: 18px; " +              // Rozmiar czcionki
                                "-fx-prompt-text-fill: #B3B3B3;"      // Kolor tekstu w podpowiedzi
                );
            } else {
                usernameField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #B3B3B3; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
                );
            }
        });

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #1DB954; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
                );
            } else {
                passwordField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #B3B3B3; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
                );
            }
        });

        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                confirmPasswordField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #1DB954; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
                );
            } else {
                confirmPasswordField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #B3B3B3; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
                );
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.setAlignment(Pos.CENTER);

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setMaxWidth(Double.MAX_VALUE);

        ImageView logoView = new ImageView(Logo.getLogoImage());
        logoView.setPreserveRatio(true);

        Circle clip = new Circle(50, 50, 50);
        logoView.setClip(clip);

        logoView.setFitWidth(100);
        logoView.setFitHeight(100);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(logoView);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setRate(0.3);
        rotateTransition.setDuration(Duration.seconds(5));

        rotateTransition.play();

        Glow glowEffect = new Glow(0.0);
        logoView.setEffect(glowEffect);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glowEffect.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(glowEffect.levelProperty(), 0.8)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glowEffect.levelProperty(), 0.0))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        logoBox.setStyle("-fx-background-color: #468254; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 8px;");

        Text NaszaMarka = new Text("JBTSound");
        NaszaMarka.setFont(new Font("Arial", 20));
        NaszaMarka.setFill(Color.web("#1DB954"));

        NaszaMarka.setStroke(Color.BLACK);
        NaszaMarka.setStrokeWidth(2);

        logoBox.getChildren().addAll(logoView, NaszaMarka);
        gridPane.add(logoBox, 1, 0);

        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(registerButton, 1, 4);
        gridPane.add(messageLabel, 1, 5);
        gridPane.add(backToLoginLink, 1, 6);

        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setHalignment(backToLoginLink, HPos.CENTER);
        GridPane.setHalignment(messageLabel, HPos.CENTER);

        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));
        messageLabel.setTextFill(Color.WHITE);
        backToLoginLink.setTextFill(Color.WHITE);

        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));

        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE); 

        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            MSController msController = new MSController();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Pola są puste!");
                messageLabel.setTextFill(Color.RED);
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Hasła nie pasują!");
                messageLabel.setTextFill(Color.RED);
                return;
            }

            if (MSController.register(username, password)) {
                messageLabel.setText("Rejestracja zakończona sukcesem!");
                messageLabel.setTextFill(Color.GREEN);

                main.openMainMenuWindow();
            } else {
                messageLabel.setText("Błąd podczas rejestracji lub użytkownik już istnieje.");
                messageLabel.setTextFill(Color.RED);
            }
        });


        backToLoginLink.setOnAction(event -> main.openLoginWindow());

        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rejestracja");
        primaryStage.show();
    }
}
