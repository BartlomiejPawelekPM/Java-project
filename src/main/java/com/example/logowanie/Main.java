package com.example.logowanie;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private LanguageManager languageManager;
    private boolean isLoggedIn = false;
    private Theme themeManager;
    private int userId;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        MySQLConnection.testConnection();
        //MySQLConnection.migratePasswordsToHash();

        languageManager = new LanguageManager();
        themeManager = new Theme();


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
            new MenuAplikacji(primaryStage, languageManager, themeManager, this, userId);
        } else {
            openLoginWindow();
        }
    }

    public void openProfileWindow(int userId){
        Profile profile = new Profile(primaryStage, languageManager, themeManager, this, userId);
        profile.initializeProfileView();

    }

    public void setUserId(int userId) {
        this.userId = userId;
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
