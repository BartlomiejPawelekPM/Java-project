package com.example.logowanie;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class Menuadmin {
    private boolean isDarkTheme = false;
    private final LanguageManager languageManager;
    private final Map<Button, String> buttonTextMap = new HashMap<>();

    private Label logoLabel;
    private Label jbtsoundLabel;
    private Button utworyButton;
    private Button changeThemeButton;
    private Button changeLanguageButton;
    private Button regulationsButton;
    private Label titleLabel;
    private HBox blocksPanel;

    private VBox root;
    private VBox bottomPanel;

    public Menuadmin(Stage primaryStage, LanguageManager languageManager) {
        this.languageManager = languageManager;
        try {
            primaryStage.setTitle("JBT Sound");

            // Root layout (VBox to align everything)
            root = new VBox();
            root.setSpacing(20);
            root.setStyle("-fx-padding: 20;");

            // ToolBar
            HBox toolBar = new HBox();
            toolBar.setSpacing(10);

            // Logo and Title
            Image logoImage = new Image(getClass().getResource("/logo.png").toExternalForm());
            logoLabel = new Label();
            ImageView logoImageView = new ImageView(logoImage);

            logoImageView.setFitHeight(40);
            logoImageView.setPreserveRatio(true);
            logoLabel.setGraphic(logoImageView);

            jbtsoundLabel = new Label("JBTSound");
            jbtsoundLabel.setFont(new Font("Segoe UI", 18));
            jbtsoundLabel.setTextFill(Color.rgb(70, 130, 180));

            // Buttons
            utworyButton = createToolBarButton("menu.utwory", "🎵");
            changeThemeButton = createToolBarButton("menu.theme", "🌙");
            changeLanguageButton = createToolBarButton("menu.language", "🌍");
            regulationsButton = createToolBarButton("menu.regulations", "📜");

            toolBar.getChildren().addAll(logoLabel, jbtsoundLabel, utworyButton, changeThemeButton, changeLanguageButton, regulationsButton);

            // Add the toolbar to the root layout
            root.getChildren().add(toolBar);

            // Title
            titleLabel = new Label(languageManager.getText("menu.biggest.hits"));
            titleLabel.setFont(new Font("Segoe UI", 40));
            titleLabel.setTextFill(Color.rgb(70, 130, 180));

            // Bottom section (blocks)
            bottomPanel = new VBox(20);
            bottomPanel.setStyle("-fx-padding: 20;");
            bottomPanel.getChildren().add(titleLabel);

            // Blocks panel initialization
            blocksPanel = new HBox(15); // Dodajemy zmienną jako pole
            blocksPanel.getChildren().addAll(createSquareBlock(), createSquareBlock(), createSquareBlock(), createSquareBlock());
            bottomPanel.getChildren().add(blocksPanel);

            // Add bottom section to root layout
            root.getChildren().add(bottomPanel);

            // Set the scene
            Scene scene = new Scene(root, 900, 600);
            primaryStage.setScene(scene);

            // Button actions
            changeThemeButton.setOnAction(e -> showThemeMenu());
            changeLanguageButton.setOnAction(e -> showLanguageMenu());
            utworyButton.setOnAction(e -> showSongMenu());
            regulationsButton.setOnAction(e -> showRegulations());

            applyTheme();
            updateTexts();

            // Observe language changes
            languageManager.isEnglishProperty().addListener((observable, oldValue, newValue) -> updateTexts());

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędu
            Platform.exit();  // Zakończenie aplikacji w przypadku błędu
        }
    }

    private Button createToolBarButton(String textKey, String icon) {
        String text = languageManager.getText(textKey);
        Button button = new Button(icon + " " + text);
        button.setFont(new Font("Segoe UI", 16));
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C;");
        buttonTextMap.put(button, textKey);
        return button;
    }

    private void applyTheme() {
        if (isDarkTheme) {
            // Ustawienie ciemniejszego tła dla root
            root.setStyle("-fx-background-color: #2C2C2C;");  // Ciemne tło
            bottomPanel.setStyle("-fx-background-color: #2C2C2C;");

            // Ustawienie koloru tekstu na ciemno dla elementów
            jbtsoundLabel.setTextFill(Color.rgb(204, 204, 204));
            titleLabel.setTextFill(Color.rgb(204, 204, 204));
            changeThemeButton.setStyle("-fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px;");
            changeLanguageButton.setStyle("-fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px;");
            utworyButton.setStyle("-fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px;");
            regulationsButton.setStyle("-fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px;");

            // Przyciski podświetlone na hover
            changeThemeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px; -fx-cursor: hand;");
            changeLanguageButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px; -fx-cursor: hand;");
            utworyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px; -fx-cursor: hand;");
            regulationsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #CCC; -fx-border-color: #444; -fx-border-width: 2px; -fx-cursor: hand;");

        } else {
            // Jasne tło dla root
            root.setStyle("-fx-background-color: #F0F0F0;");  // Jasne tło
            bottomPanel.setStyle("-fx-background-color: #F0F0F0;");

            // Kolor tekstu dla ciemnego motywu
            jbtsoundLabel.setTextFill(Color.rgb(70, 130, 180));
            titleLabel.setTextFill(Color.rgb(70, 130, 180));
            changeThemeButton.setStyle("-fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px;");
            changeLanguageButton.setStyle("-fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px;");
            utworyButton.setStyle("-fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px;");
            regulationsButton.setStyle("-fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px;");

            // Przyciski podświetlone na hover
            changeThemeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px; -fx-cursor: hand;");
            changeLanguageButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px; -fx-cursor: hand;");
            utworyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px; -fx-cursor: hand;");
            regulationsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-border-color: #888; -fx-border-width: 2px; -fx-cursor: hand;");
        }
    }

    private void showThemeMenu() {
        ContextMenu themeMenu = new ContextMenu();

        MenuItem lightItem = new MenuItem(languageManager.getText("theme.light"));
        lightItem.setOnAction(e -> changeTheme(false));

        MenuItem darkItem = new MenuItem(languageManager.getText("theme.dark"));
        darkItem.setOnAction(e -> changeTheme(true));

        themeMenu.getItems().addAll(lightItem, darkItem);
        themeMenu.show(changeThemeButton, 0, changeThemeButton.getHeight());

        themeMenu.show(changeThemeButton, Side.BOTTOM, 0, 0);
    }

    private void changeTheme(boolean isDark) {
        isDarkTheme = isDark;
        applyTheme();
    }

    private void showLanguageMenu() {
        ContextMenu languageMenu = new ContextMenu();

        MenuItem englishItem = new MenuItem("English");
        englishItem.setOnAction(e -> languageManager.setLanguage(true));

        MenuItem polishItem = new MenuItem("Polski");
        polishItem.setOnAction(e -> languageManager.setLanguage(false));

        languageMenu.getItems().addAll(englishItem, polishItem);

        // Ustawienie pozycji menu przy przycisku (zmiana na mouse event)
        languageMenu.show(changeLanguageButton, Side.BOTTOM, 0, 0);
    }

    private void updateTexts() {
        changeThemeButton.setText(languageManager.getText("menu.theme"));
        regulationsButton.setText(languageManager.getText("menu.regulations"));
        changeLanguageButton.setText(languageManager.getText("menu.language"));
        utworyButton.setText(languageManager.getText("menu.utwory"));
        titleLabel.setText(languageManager.getText("menu.biggest.hits"));
    }

    private void showSongMenu() {
        // Tworzymy ContextMenu dla utworów
        ContextMenu songMenu = new ContextMenu();

        // Tworzymy poszczególne pozycje w menu
        MenuItem addSongItem = new MenuItem(languageManager.getText("menu.add.song"));
        //addSongItem.setOnAction(e -> addSong());

        MenuItem removeSongItem = new MenuItem(languageManager.getText("menu.remove.song"));
        //removeSongItem.setOnAction(e -> removeSongFromList());

        MenuItem createAlbumItem = new MenuItem(languageManager.getText("menu.create.album"));
        //createAlbumItem.setOnAction(e -> createAlbum());

        MenuItem songListItem = new MenuItem(languageManager.getText("menu.song.list"));
        //songListItem.setOnAction(e -> showSongList());

        // Dodajemy pozycje do menu
        songMenu.getItems().addAll(addSongItem, removeSongItem, createAlbumItem, songListItem);

        // Ustalamy miejsce wyświetlenia menu (na przycisku utwory)
        utworyButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                songMenu.show(utworyButton, event.getScreenX(), event.getScreenY());
            }
        });

    }

    private void showRegulations() {
        String fileName = languageManager.isEnglish() ? "regulamin_en.txt" : "regulamin.txt";
        System.out.println("Otwieram plik: " + fileName); // Logowanie

        Stage dialogStage = new Stage();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder regulationsContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                regulationsContent.append(line).append("\n");
            }

            // Tworzymy TextArea do wyświetlania regulaminu
            TextArea textArea = new TextArea(regulationsContent.toString());
            textArea.setEditable(false);  // Tekst nie będzie edytowalny
            textArea.setWrapText(true);   // Automatyczne zawijanie linii
            textArea.setFont(Font.font("Segoe UI", 14));  // Czcionka zgodna z motywami
            textArea.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");  // Ustawienie czcionki

            // Ustawiamy kolory na podstawie motywu
            if (isDarkTheme) {
                textArea.setStyle("-fx-background-color: #323232; -fx-text-fill: white;");
            } else {
                textArea.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            }

            // Ustawiamy ScrollPane
            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setFitToWidth(true);  // Dopasowanie szerokości

            // Tworzymy przycisk "Zamknij"
            Button closeButton = new Button(languageManager.getText("menu.cancel"));
            closeButton.setFont(Font.font("Segoe UI", 14));
            closeButton.setOnAction(e -> dialogStage.close());  // Zamknięcie okna po kliknięciu

            // Tworzymy panel przycisków
            VBox buttonPanel = new VBox(10);
            buttonPanel.getChildren().add(closeButton);

            // Tworzymy główny panel okna
            BorderPane dialogPanel = new BorderPane();
            dialogPanel.setCenter(scrollPane);  // Panel z tekstem
            dialogPanel.setBottom(buttonPanel);  // Panel z przyciskiem

            // Tworzymy nowe okno (Stage) dla regulaminu
            dialogStage.setTitle(languageManager.getText("menu.regulations"));
            dialogStage.initModality(Modality.APPLICATION_MODAL);  // Okno modalne, nie pozwala na interakcję z głównym oknem
            dialogStage.setScene(new Scene(dialogPanel, 600, 400));  // Ustawiamy scenę o zadanej wielkości
            dialogStage.show();  // Wyświetlenie okna

        } catch (IOException e) {
            e.printStackTrace(); // Logowanie błędu

            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(languageManager.getText("menu.regulations.error"));
                alert.showAndWait();
            });
        }
    }


    private VBox createSquareBlock() {
        VBox block = new VBox();
        block.setPrefSize(100, 100);
        block.setStyle("-fx-background-color: #ADD8E6; -fx-border-radius: 20;");
        return block;
    }
}
