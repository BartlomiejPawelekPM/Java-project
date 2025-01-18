package com.example.logowanie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MSController {
    public static boolean login(String username, String password){
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM uzytkownicy WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Jeśli jest wynik, logowanie zakończone sukcesem
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register(String username, String password) {
        // Sprawdzenie, czy użytkownik już istnieje
        if (doesUserExist(username)) {
            return false; // Użytkownik już istnieje
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "INSERT INTO uzytkownicy (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);  // Hasło w formie niezaszyfrowanej (w prawdziwej aplikacji użyj szyfrowania)
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return true; // Rejestracja zakończona sukcesem
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Rejestracja nieudana
    }

    private static boolean doesUserExist(String username) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM uzytkownicy WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Jeśli użytkownik istnieje, zwróci true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // W przypadku błędu lub braku użytkownika
    }

}