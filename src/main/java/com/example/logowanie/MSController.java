package com.example.logowanie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MSController {

    private static final Logger logger = LogManager.getLogger(MSController.class);

    public static boolean login(String username, String password) {
        logger.info("Attempting to log in with username: {}", username);

        try (Connection connection = MySQLConnection.getConnection()) {
            logger.info("Connected to the database successfully.");

            String debugQuery = "SELECT * FROM uzytkownicy WHERE username = 'admin'";
            PreparedStatement debugStatement = connection.prepareStatement(debugQuery);
            ResultSet debugResultSet = debugStatement.executeQuery();

            while (debugResultSet.next()) {
                logger.info("Debug - Found user in database:");
                logger.info("Username: {}", debugResultSet.getString("username"));
                logger.info("Password (hashed): {}", debugResultSet.getString("password"));
            }

            String query = "SELECT * FROM uzytkownicy WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password"); // Pobierz hasło z bazy danych
                logger.info("Found user: {}", username);

                // Porównanie hasła wprowadzonego przez użytkownika z hasłem zapisanym w bazie
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    logger.info("Login successful for username: {}", username);
                    return true; // Logowanie powiodło się
                } else {
                    logger.warn("Invalid password for username: {}", username);
                }
            } else {
                logger.warn("User not found: {}", username);
            }
        } catch (SQLException e) {
            logger.error("Database error during login attempt for username: {}", username, e);
        }
        return false; // Logowanie nieudane
    }

    public static boolean register(String username, String password) {
        logger.info("Attempting to register user: {}", username);

        if (doesUserExist(username)) {
            logger.warn("User already exists: {}", username);
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "INSERT INTO uzytkownicy (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Registration successful for username: {}", username);
                return true;
            } else {
                logger.warn("Registration failed for username: {}", username);
            }
        } catch (SQLException e) {
            logger.error("Database error during registration attempt for username: {}", username, e);
        }
        return false;
    }

    private static boolean doesUserExist(String username) {
        logger.info("Checking if user exists: {}", username);

        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM uzytkownicy WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            logger.error("Database error while checking if user exists: {}", username, e);
        }
        return false;
    }
}