package com.example.logowanie;

import com.example.logowanie.LanguageManager;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Bottom {
    private final VBox bottomSection;
    private final LanguageManager languageManager;

    public Bottom(LanguageManager languageManager) {
        this.languageManager = languageManager;
        this.bottomSection = createBottomSection();
    }

    private VBox createBottomSection() {
        VBox bottomPanel = new VBox(20);
        bottomPanel.setStyle("-fx-padding: 20;");

        String biggestHitsText = languageManager.getText("menu.biggest.hits");

        Label titleLabel = new Label(biggestHitsText);
        titleLabel.setStyle("-fx-font-size: 40; -fx-text-fill: #4682B4; -fx-alignment: center; -fx-padding: 10;");
        bottomPanel.getChildren().add(titleLabel);

        HBox blocksPanel = BlockPanel.createBlocksPanel();
        bottomPanel.getChildren().add(blocksPanel);

        return bottomPanel;
    }

    public VBox getBottomSection() {
        return bottomSection;
    }

    public void updateBiggestHitsText() {
        String updatedText = languageManager.getText("menu.biggest.hits");
        Label titleLabel = (Label) bottomSection.getChildren().get(0);
        titleLabel.setText(updatedText);
    }
}
