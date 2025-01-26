package com.example.logowanie;

import javafx.fxml.FXML;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MSController {

    // Dodanie loggera
    private static final Logger logger = LoggerFactory.getLogger(MSController.class);
    private static final LanguageManager languageManager = new LanguageManager();

    public static boolean login(String username, String password) {
        // Zapytanie do bazy danych po użytkowniku
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("haslo");
                System.out.println("Sprawdzam dane dla użytkownika: " + username);
                System.out.println("Hash zapisany w bazie: " + storedHashedPassword);

                // Porównanie hasła za pomocą BCrypt
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    System.out.println("Hasła pasują!");
                    return true; // Logowanie powiodło się
                } else {
                    System.out.println("Hasła się nie zgadzają.");
                }
            } else {
                System.out.println("Użytkownik " + username + " nie istnieje.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Jeśli użytkownik nie istnieje lub hasło nie pasuje
    }

    public static boolean register(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        logger.info("Attempting to register user: {}", username);

        // Sprawdzamy, czy użytkownik już istnieje
        if (doesUserExist(username)) {
            logger.warn("User already exists: {}", username);
            return false; // Użytkownik już istnieje
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "INSERT INTO uzytkownicy (nazwa_uzytkownika, haslo, permisje, email, zdjecie, Saldo, Imie, Nazwisko, Adres)" +
                    "VALUES (?, ?, ?, '', '', 0.00, '', '', '')";
            System.out.println("Query: " + query);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setInt(3, 2);

            System.out.println("Parameters: username=" + username + ", hashedPassword=" + hashedPassword);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Database error during registration attempt for username: " + username, e);
            return false;
        }
    }

    // Sprawdzenie, czy użytkownik już istnieje
    private static boolean doesUserExist(String username) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true; // Użytkownik już istnieje
            }
        } catch (SQLException e) {
            logger.error("Error checking if user exists: {}", username, e);
        }
        return false; // Użytkownik nie istnieje
    }
}
