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

    private static final Logger logger = LoggerFactory.getLogger(MSController.class);
    private static final LanguageManager languageManager = new LanguageManager();

    public static boolean login(String username, String password) {
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("haslo");
                System.out.println("Sprawdzam dane dla użytkownika: " + username);
                System.out.println("Hash zapisany w bazie: " + storedHashedPassword);

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    System.out.println("Hasła pasują!");
                    return true;
                } else {
                    System.out.println("Hasła się nie zgadzają.");
                }
            } else {
                System.out.println("Użytkownik " + username + " nie istnieje.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        logger.info("Attempting to register user: {}", username);

        if (doesUserExist(username)) {
            logger.warn("User already exists: {}", username);
            return false;
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


    private static boolean doesUserExist(String username) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error checking if user exists: {}", username, e);
        }
        return false;
    }

    public static int getUserIdByUsername(String username) {
        String query = "SELECT id FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    System.out.println("Znaleziono userId: " + userId);
                    return userId;
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania userId:");
            e.printStackTrace();
        }
        return -1;
    }
}
