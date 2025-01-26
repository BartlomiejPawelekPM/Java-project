package com.example.logowanie;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.example.logowanie.Main;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ToolBar {
    private final HBox toolBar;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Bottom bottomSection;
    private final Main main;

    public ToolBar(LanguageManager languageManager, Theme themeManager, Bottom bottomSection, Main main) {
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.bottomSection = bottomSection;
        this.toolBar = createToolBar();
        this.main = main;
    }

    private HBox createToolBar() {
        HBox toolBar = new HBox(10);
        toolBar.setStyle("-fx-padding: 10; -fx-background-color: #F0F0F0;");

        Label logoLabel = createLogoLabel();
        Label jbtsoundLabel = new Label("JBTSound");
        jbtsoundLabel.setStyle("-fx-font-size: 18; -fx-text-fill: #4682B4;");

        Button utworyButton = createToolBarButton("menu.utwory", "🎵");
        Button changeThemeButton = createToolBarButton("menu.theme", "🌙");
        Button changeLanguageButton = createToolBarButton("menu.language", "🌍");
        Button regulationsButton = createToolBarButton("menu.regulations", "📜");

        Button profileButton = new Button("👤");
        profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-font-size: 16;");
        profileButton.setOnAction(e -> main.openProfileWindow());

        changeThemeButton.setOnAction(e -> {
            themeManager.toggleTheme();
            themeManager.applyTheme(toolBar.getScene());
        });


        changeLanguageButton.setOnAction(actionEvent -> {
            boolean isEnglish = languageManager.isEnglish();
            languageManager.setLanguage(!isEnglish);

            utworyButton.setText("🎵 " + languageManager.getText("menu.utwory"));
            changeThemeButton.setText("🌙 " + languageManager.getText("menu.theme"));
            changeLanguageButton.setText("🌍 " + languageManager.getText("menu.language"));
            regulationsButton.setText("📜 " + languageManager.getText("menu.regulations"));

            bottomSection.updateBiggestHitsText();

        });

        regulationsButton.setOnAction(actionEvent -> showRegulations());

        HBox leftSide = new HBox(10, logoLabel, jbtsoundLabel, utworyButton, changeThemeButton, changeLanguageButton, regulationsButton);
        leftSide.setSpacing(10);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox rightSide = new HBox(profileButton);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        toolBar.getChildren().addAll(leftSide, spacer, rightSide);

        return toolBar;
    }

    private Label createLogoLabel() {
        Label label = new Label();
        try {
            Image logoImage = new Image(getClass().getResource("/logo.png").toExternalForm());
            ImageView logoImageView = new ImageView(logoImage);
            logoImageView.setFitHeight(40);
            logoImageView.setPreserveRatio(true);
            label.setGraphic(logoImageView);
        } catch (NullPointerException e) {
            label.setText("Logo");
            label.setStyle("-fx-font-size: 16; -fx-text-fill: #3C3C3C;");
        }
        return label;
    }

    private Button createToolBarButton(String textKey, String icon) {
        String text = languageManager.getText(textKey);
        Button button = new Button(icon + " " + text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-font-size: 16;");
        return button;
    }

    public HBox getToolBar() {
        return toolBar;
    }

    private void showRegulations() {
        String fileName = languageManager.isEnglish() ? "regulamin_en.txt" : "regulamin.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder regulationsContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                regulationsContent.append(line).append("\n");
            }

            Stage regulationsStage = new Stage();
            regulationsStage.setTitle(languageManager.getText("menu.regulations"));

            TextArea textArea = new TextArea(regulationsContent.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setStyle("-fx-font-size: 14; -fx-font-family: 'Segoe UI';");

            if (themeManager.isDark()) {
                textArea.setStyle("-fx-control-inner-background: #323232; -fx-text-fill: white; -fx-font-size: 14;");
            } else {
                textArea.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 14;");
            }

            Button closeButton = new Button(languageManager.getText("menu.cancel"));
            closeButton.setStyle("-fx-font-size: 14;");
            closeButton.setOnAction(e -> regulationsStage.close());

            VBox layout = new VBox(10, textArea, closeButton);
            layout.setPadding(new Insets(10));
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 600, 400);
            themeManager.applyTheme(scene); // Dostosowanie motywu
            regulationsStage.setScene(scene);

            regulationsStage.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getText("menu.regulations.error"));
            alert.showAndWait();
        }
    }

}