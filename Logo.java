package com.example.logowanie;

import javafx.scene.image.Image;

public class Logo {
    private static final Image logoImage = new Image(Logo.class.getResourceAsStream("/logo.png"));

    public static Image getLogoImage() {
        return logoImage;
    }
}
