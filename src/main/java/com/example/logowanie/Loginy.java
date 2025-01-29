package com.example.logowanie;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
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
import javafx.util.Duration;


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

        loginButton.setStyle("-fx-background-color: #1DB954; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        registerLink.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-underline: true;");
        registerLink.setFont(new Font("Arial", 12));

        usernameField.setPromptText("Login");
        passwordField.setPromptText("Hasło");

        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameField.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: #1DB954; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-width: 2px; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-prompt-text-fill: #B3B3B3;"
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

        StackPane passwordStackPane = new StackPane();
        passwordStackPane.setPrefWidth(300);
        passwordStackPane.setPrefHeight(40);

        passwordStackPane.getChildren().addAll(passwordField);

        gridPane.add(passwordStackPane, 1, 2);

        gridPane.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.CENTER);

        gridPane.add(registerLink, 1, 8);
        GridPane.setHalignment(registerLink, HPos.CENTER);

        gridPane.add(messageLabel, 1, 5);
        GridPane.setHalignment(messageLabel, HPos.CENTER);

        gridPane.setPadding(new Insets(20, 20, 20, 20));

        messageLabel.setAlignment(Pos.CENTER);
        loginButton.setAlignment(Pos.CENTER);
        registerLink.setAlignment(Pos.CENTER);

        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#121212"), CornerRadii.EMPTY, null)));

        messageLabel.setTextFill(Color.WHITE);
        registerLink.setTextFill(Color.WHITE);

        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #1ed760; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;"));

        registerLink.setStyle("-fx-underline: true;");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            MSController msController = new MSController();

            System.out.println("Wynik logowania: " + MSController.login(username, password));


            if (MSController.login(username, password)) {
                messageLabel.setText("Logowanie powiodło się!");
                messageLabel.setTextFill(Color.GREEN);

                int loggedInUserId = MSController.getUserIdByUsername(username);
                if (loggedInUserId != -1) {
                    System.out.println("Zalogowano użytkownika o ID: " + loggedInUserId); // Dodajemy logowanie
                    main.setUserId(loggedInUserId); // Ustawiamy userId w Main
                } else {
                    System.out.println("Nie znaleziono użytkownika o nazwie: " + username); // Dodajemy logowanie
                }

                main.settLoggedIn(true);

                primaryStage.close();

                main.openMainMenuWindow();

            } else {
                messageLabel.setText("Niepoprawne dane logowania.");
                messageLabel.setTextFill(Color.RED);
            }
        });

        registerLink.setOnAction(event -> main.openRegistrationWindow());

        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logowanie");
        primaryStage.show();
    }
}
