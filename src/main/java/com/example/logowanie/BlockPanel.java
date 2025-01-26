package com.example.logowanie;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class BlockPanel {
    public static HBox createBlocksPanel() {
        HBox panel = new HBox(15);

        panel.getStyleClass().add("block-panel");
        for (int i = 0; i < 4; i++) {
            VBox block = createSquareBlock();
            panel.getChildren().add(block);

            HBox.setHgrow(block, Priority.ALWAYS);
        }
        return panel;
    }

    private static VBox createSquareBlock() {
        VBox block = new VBox();
        block.setPrefSize(100, 100);
        block.setStyle("-fx-background-color: #ADD8E6; -fx-border-radius: 20;");

        //block.setMaxWidth(Double.MAX_VALUE);

        return block;
    }
}
