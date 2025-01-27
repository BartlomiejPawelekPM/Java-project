package com.example.logowanie;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import com.example.logowanie.Main;

public class Menuadmin {
    private Stage primaryStage;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Main main;
    private int userId;

    public Menuadmin(Stage primaryStage, LanguageManager languageManager, Theme themeManager, Main main, int userId) {
        this.primaryStage = primaryStage;
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.main = main;
        this.userId = userId;

        try {
            initializeUI(main);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI(Main main) {
        BorderPane root = new BorderPane();

        Bottom bottomSection = new Bottom(languageManager);

        ToolBar toolBar = new ToolBar(languageManager, themeManager, bottomSection, main, userId);
        root.setTop(toolBar.getToolBar());


        root.setBottom(bottomSection.getBottomSection());

        Scene scene = new Scene(root, 900, 600);
        themeManager.applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



