package com.example.logowanie;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.HBox.setHgrow;

public class Profile {
    private final Stage stage;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Main main;
    private final int userId;
    private Label saldoLabel;

    private static List<Song> purchasedSongs = new ArrayList<Song>();

    public Profile(Stage stage, LanguageManager languageManager, Theme themeManager, Main main, int userId) {
        this.stage = stage;
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.main = main;
        this.userId = userId;

        initializeProfileView();
    }

    public void initializeProfileView() {
        BorderPane root = new BorderPane();

        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setStyle("-fx-padding: 10; -fx-background-color: black;");

        Button backButton = new Button();
        backButton.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-pref-width: 50px; " +
                        "-fx-pref-height: 50px;"
        );
        SVGPath homeIcon = new SVGPath();
        homeIcon.setContent("M12,2 L22,12 L18,12 L18,22 L14,22 L14,16 L10,16 L10,22 L6,22 L6,12 L2,12 Z");
        homeIcon.setFill(Color.WHITE);
        backButton.setGraphic(homeIcon);
        backButton.setOnAction(e -> goBackToMenu());

        Button logoutButton = new Button();
        logoutButton.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-pref-width: 50px; " +
                        "-fx-pref-height: 50px;"
        );
        SVGPath exitIcon = new SVGPath();
        exitIcon.setContent("M4,8 L12,16 L20,8 Z");
        exitIcon.setFill(Color.WHITE);
        logoutButton.setGraphic(exitIcon);
        logoutButton.setOnAction(e -> main.openLoginWindow());

        Button addFundsButton = new Button();
        addFundsButton.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-pref-width: 50px; " +
                        "-fx-pref-height: 50px;" +
                        "-fx-padding: 5px;"
        );
        SVGPath walletIcon = new SVGPath();
        walletIcon.setContent("M4 2C2.9 2 2 2.9 2 4V16C2 17.1 2.9 18 4 18H20C21.1 18 22 17.1 22 16V4C22 2.9 21.1 2 20 2H4ZM20 16H4V8H20V16ZM4 6H20V4H4V6Z");
        walletIcon.setFill(Color.BROWN);
        walletIcon.setStroke(Color.BLACK);
        walletIcon.setStrokeWidth(2);
        walletIcon.setScaleX(1.5);
        walletIcon.setScaleY(1.5);
        addFundsButton.setGraphic(walletIcon);
        addFundsButton.setOnAction(e -> openAddFundsWindow());

        Button shoppinCart = new Button();
        shoppinCart.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-pref-width: 50px; " +
                        "-fx-pref-height: 50px;"
        );
        SVGPath cartIcon = new SVGPath();
        cartIcon.setContent("M7,4C7,3.44772 7.44772,3 8,3C8.55228,3 9,3.44772 9,4C9,4.55228 8.55228,5 8,5C7.44772,5 7,4.55228 7,4ZM3,2C2.44772,2 2,2.44772 2,3V6C2,6.55228 2.44772,7 3,7H4.40698C5.26446,7 6.0408,7.50313 6.31299,8.29668L7.50125,11.2636C7.82256,12.0766 8.77634,12.5 9.77777,12.5H16.2222C17.2236,12.5 18.1774,12.0766 18.4987,11.2636L19.687,8.29668C19.9592,7.50313 20.7355,7 21.593,7H23C23.5523,7 24,6.55228 24,6V3C24,2.44772 23.5523,2 23,2H3ZM19.687,6H4.40698L5.59525,8.29668C5.86745,9.09023 6.64379,9.59333 7.50125,9.59333H16.2222C17.0797,9.59333 17.8561,9.09023 18.1283,8.29668L19.687,6Z");
        cartIcon.setFill(Color.WHITE);
        shoppinCart.setGraphic(cartIcon);
        shoppinCart.setOnAction(e -> showCartWindow());

        top.getChildren().addAll(backButton, new Wyrownaj(), shoppinCart,addFundsButton, logoutButton);

        root.setTop(top);

        Label profileLabel = new Label();
        profileLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #4682B4;");

        User user = MySQLConnection.getUserProfile(userId);

        if (user != null) {
            HBox profileBox = new HBox(20);
            profileBox.setAlignment(Pos.CENTER);

            Image profileImage = new Image(getClass().getResourceAsStream("/user_image.png"));
            ImageView profileImageView = new ImageView(profileImage);
            profileImageView.setFitWidth(150);
            profileImageView.setFitHeight(150);
            profileImageView.setPreserveRatio(true);
            profileImageView.setClip(new Circle(75, 75, 75));

            profileImageView.setOnMouseClicked(e -> changeProfileImage(profileImageView));

            VBox leftSection = new VBox(10);
            leftSection.setAlignment(Pos.CENTER);
            leftSection.getChildren().addAll(profileImageView);

            saldoLabel = new Label("Saldo: " + user.getSaldo() + " zł");
            saldoLabel.setStyle("-fx-font-size: 18; -fx-text-fill: #4682B4;");
            leftSection.getChildren().add(saldoLabel);

            VBox rightSection = new VBox(10);
            rightSection.setAlignment(Pos.TOP_LEFT);

            TextField usernameField = new TextField(user.getUsername());
            TextField emailField = new TextField(user.getEmail());
            TextField imieField = new TextField(user.getImie());
            TextField nazwiskoField = new TextField(user.getNazwisko());
            TextField adresField = new TextField(user.getAdres());

            rightSection.getChildren().addAll(
                    new Label("Nazwa użytkownika: "), usernameField,
                    new Label("Email: "), emailField,
                    new Label("Imię: "), imieField,
                    new Label("Nazwisko: "), nazwiskoField,
                    new Label("Adres: "), adresField
            );

            usernameField.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #B3B3B3; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-prompt-text-fill: #B3B3B3;"
            );

            emailField.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #B3B3B3; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-prompt-text-fill: #B3B3B3;"
            );

            imieField.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #B3B3B3; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-prompt-text-fill: #B3B3B3;"
            );

            nazwiskoField.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #B3B3B3; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-prompt-text-fill: #B3B3B3;"
            );

            adresField.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #B3B3B3; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-prompt-text-fill: #B3B3B3;"
            );

            profileBox.getChildren().addAll(leftSection, rightSection);

            VBox content = new VBox(15);
            content.setAlignment(Pos.TOP_CENTER);
            content.setStyle("-fx-padding: 20;");
            content.getChildren().addAll(profileBox);

            Button saveButton = new Button("Zapisz zmiany");
            saveButton.setStyle(
                    "-fx-background-color: #468254; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-background-radius: 8px;"
            );

            saveButton.setOnAction(e -> {

                String updatedUsername = usernameField.getText();
                String updatedEmail = emailField.getText();
                String updatedImie = imieField.getText();
                String updatedNazwisko = nazwiskoField.getText();
                String updatedAdres = adresField.getText();

                boolean success = MySQLConnection.updateUserProfile(userId, updatedUsername, updatedEmail, updatedImie, updatedNazwisko, updatedAdres);

                if (success) {
                    showInfo("Dane zostały zaktualizowane.");
                } else {
                    showError("Wystąpił błąd podczas zapisywania danych.");
                }
            });
            content.getChildren().add(saveButton);

            root.setCenter(content);
        } else {
            showError(languageManager.getText("profile.user.not.found"));
        }

        Scene scene = new Scene(root, 900, 600);
        themeManager.applyTheme(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void changeProfileImage(ImageView profileImageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String userImageDirectory = System.getProperty("user.home") + File.separator + ".yourAppName" + File.separator + "images";
                File directory = new File(userImageDirectory);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File destinationFile = new File(directory, selectedFile.getName());

                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Image newProfileImage = new Image(destinationFile.toURI().toString());
                profileImageView.setImage(newProfileImage);

                updateProfileImageInDatabase(userId, destinationFile.getAbsolutePath());

            } catch (IOException e) {
                showError("Wystąpił problem przy zapisie zdjęcia.");
            }
        }
    }

    private void updateProfileImageInDatabase(int userId, String imagePath) {
        String query = "UPDATE uzytkownicy SET zdjecie = ? WHERE id = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, imagePath);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showInfo("Zdjęcie zostało zapisane.");
            } else {
                showError("Nie udało się zaktualizować zdjęcia.");
            }
        } catch (SQLException e) {
            showError("Błąd przy zapisie zdjęcia do bazy.");
        }
    }


    private void openAddFundsWindow() {
        Stage addFundsStage = new Stage();
        VBox addFundsRoot = new VBox(15);
        addFundsRoot.setAlignment(Pos.CENTER);
        addFundsRoot.setStyle("-fx-padding: 20;");

        Label promptLabel = new Label("Podaj kwotę do dodania:");
        TextField amountField = new TextField();
        amountField.setPromptText("Wprowadź kwotę");

        Button confirmButton = new Button("Dodaj");

        confirmButton.setOnAction(e -> {
            try {
                double amountToAdd = Double.parseDouble(amountField.getText());
                if (amountToAdd > 0) {

                    BigDecimal amountBigDecimal = BigDecimal.valueOf(amountToAdd);

                    boolean success = MySQLConnection.updateSaldo(userId, amountBigDecimal);
                    if (success) {
                        BigDecimal newSaldo = MySQLConnection.getSaldo(userId);
                        saldoLabel.setText("Saldo: " + newSaldo); // Odświeżenie salda
                        showInfo("Środki zostały dodane.");
                        addFundsStage.close();
                    } else {
                        showError("Nie udało się dodać środków.");
                    }
                } else {
                    showError("Kwota musi być większa niż 0.");
                }
            } catch (NumberFormatException ex) {
                showError("Nieprawidłowy format kwoty.");
            }
        });

        addFundsRoot.getChildren().addAll(promptLabel, amountField, confirmButton);

        Scene addFundsScene = new Scene(addFundsRoot, 300, 200);
        themeManager.applyTheme(addFundsScene);
        addFundsStage.setTitle("Dodaj środki");
        addFundsStage.setScene(addFundsScene);
        addFundsStage.show();
    }

    private void goBackToMenu() {
        try {
            new MenuAplikacji(stage, languageManager, themeManager, main, userId);
        } catch (Exception e) {
            showError("Error returning to menu: " + e.getMessage());
        }
    }

    public static void showCartWindow() {
        Stage cartStage = new Stage();
        VBox cartContent = new VBox(10);
        cartContent.setAlignment(Pos.CENTER);
        cartContent.setStyle("-fx-padding: 20;");

        if (purchasedSongs.isEmpty()) {
            cartContent.getChildren().add(new Label("Koszyk jest pusty"));
        } else {
            for (Song song : purchasedSongs) {
                Label songLabel = new Label(song.getTitle());
                cartContent.getChildren().add(songLabel);
            }
        }

        Scene cartScene = new Scene(cartContent, 300, 400);
        cartStage.setTitle("Koszyk");
        cartStage.setScene(cartScene);
        cartStage.show();
    }

    public static void addToCart(Song song) {
        purchasedSongs.add(song);
    }

    public static class Song {
        private final String title;

        public Song(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(languageManager.getText("error.title"));
        alert.setHeaderText(languageManager.getText("error.header"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class Wyrownaj extends Region {
        public Wyrownaj() {
            setMinWidth(Region.USE_PREF_SIZE);
            setMaxWidth(Double.MAX_VALUE);
            setHgrow(this, Priority.ALWAYS);
        }
    }
}