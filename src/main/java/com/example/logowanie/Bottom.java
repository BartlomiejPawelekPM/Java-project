package com.example.logowanie;

import com.example.logowanie.LanguageManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bottom {
    private final VBox bottomSection;
    private final LanguageManager languageManager;
    private Label titleLabel;

    public Bottom(LanguageManager languageManager) {
        this.languageManager = languageManager;
        this.bottomSection = createBottomSection();
    }

    private VBox createBottomSection() {
        VBox bottomPanel = new VBox(20);
        bottomPanel.setStyle("-fx-padding: 20;");

        String biggestHitsText = languageManager.getText("menu.biggest.hits");

        StackPane titleContainer = new StackPane();
        titleContainer.setMaxWidth(400);

        Rectangle backgroundBar = new Rectangle(400, 50);
        backgroundBar.setFill(Color.web("#1DB954"));


        Polygon cutoutTriangle = new Polygon();
        cutoutTriangle.getPoints().addAll(
                400.0, 0.0,
                340.0, 25.0,
                400.0, 50.0
        );

        Shape clippedBar = Shape.subtract(backgroundBar, cutoutTriangle);
        clippedBar.setFill(Color.web("#1DB954"));

        titleLabel = new Label(biggestHitsText);
        titleLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black; -fx-padding: 10;");
        titleLabel.setAlignment(Pos.CENTER_LEFT);

        titleContainer.getChildren().addAll(clippedBar, titleLabel);
        StackPane.setAlignment(titleLabel, Pos.CENTER_LEFT);

        bottomPanel.getChildren().add(titleContainer);

        HBox blocksPanel = BlockPanel.createBlocksPanel();
        bottomPanel.getChildren().add(blocksPanel);

        return bottomPanel;
    }

    public VBox getBottomSection() {
        return bottomSection;
    }

    public void updateBiggestHitsText() {
        String updatedText = languageManager.getText("menu.biggest.hits");
        titleLabel.setText(updatedText);
    }
}
