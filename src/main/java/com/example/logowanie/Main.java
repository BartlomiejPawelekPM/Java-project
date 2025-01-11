package com.example.logowanie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        openLoginWindow();
    }

    public void openLoginWindow() {
        new Loginy(primaryStage, this);
    }

    public void openRegistrationWindow() {
        new Rejestracja(primaryStage, this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
