package com.example.logowanie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private LanguageManager languageManager;
    private boolean isLoggedIn = false;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        MySQLConnection.testConnection();
        //MySQLConnection.migratePasswordsToHash();

        languageManager = new LanguageManager();

        openLoginWindow();
    }

    public void openLoginWindow() {
        new Loginy(primaryStage, this);
    }

    public void openRegistrationWindow() {
        new Rejestracja(primaryStage, this);
    }

    public void openMainMenuWindow() {
        if (isLoggedIn) {
            new Menuadmin(primaryStage, languageManager);
        } else {
            openLoginWindow();
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void settLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
