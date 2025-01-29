package com.example.logowanie;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlockPanel {
    public static HBox createBlocksPanel() {
        HBox panel = new HBox(15);

        panel.getStyleClass().add("block-panel");
        for (int i = 0; i < 4; i++) {
            VBox block = createSquareBlock(i);
            panel.getChildren().add(block);

            HBox.setHgrow(block, Priority.ALWAYS); // Dostosowanie szerokości
        }
        return panel;
    }

    private static VBox createSquareBlock(int index) {
        VBox block = new VBox();
        block.setPrefSize(100, 100);
        block.setStyle("-fx-background-color: #ADD8E6; -fx-border-radius: 20; -fx-alignment: center");

        ImageView imageView = new ImageView();

        Image image = null;
        switch (index) {
            case 0:
                image = new Image(BlockPanel.class.getResourceAsStream("/paris.jpg"));
                break;
            case 1:
                image = new Image(BlockPanel.class.getResourceAsStream("/sou.jpg"));
                break;
            case 2:
                image = new Image(BlockPanel.class.getResourceAsStream("/bagieta.jpg"));
                break;
            case 3:
                image = new Image(BlockPanel.class.getResourceAsStream("/prblm.jpg"));
                break;
        }

        if (image != null) {
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
        }

        block.getChildren().add(imageView);
        return block;
    }
}
