package com.example.logowanie;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class MySQLConnection {
    // Parametry połączenia z bazą danych
    private static final String URL = "jdbc:mysql://localhost:3306/studio_nagran";  // Adres bazy danych
    private static final String USER = "root"; // Nazwa użytkownika MySQL
    private static final String PASSWORD = ""; // Hasło do MySQL (lub puste, jeśli brak)

    // Metoda zwracająca połączenie z bazą danych
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Metoda sprawdzająca, czy użytkownik istnieje w bazie danych
    public static boolean doesUserExist(String username) {
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();  // Zwraca true, jeśli użytkownik istnieje
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false; // Zwraca false, jeśli użytkownik nie istnieje
    }

    // Metoda do weryfikacji loginu i hasła
    public static boolean verifyLogin(String username, String password) {
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("haslo");  // Hasło z bazy
                    // Sprawdzanie hasła za pomocą BCrypt
                    return BCrypt.checkpw(password, storedHashedPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false; // Zwraca false, jeśli użytkownik nie istnieje lub hasło jest nieprawidłowe
    }

    // Metoda do rejestracji użytkownika
    public static boolean registerUser(String username, String password) {
        if (doesUserExist(username)) {
            return false;  // Jeśli użytkownik już istnieje
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Hashowanie hasła
        String query = "INSERT INTO uzytkownicy (nazwa_uzytkownika, haslo) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Zwraca true, jeśli rejestracja zakończyła się sukcesem
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false; // Zwraca false, jeśli wystąpił błąd podczas rejestracji
    }

    // Testowanie połączenia z bazą danych
    public static void testConnection() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Połączenie z bazą danych zostało nawiązane!");
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
            e.printStackTrace(); // Drukuje szczegóły błędu
        }
    }

    //public static void migratePasswordsToHash() {
    //    String selectQuery = "SELECT id, haslo FROM uzytkownicy";
    //    String updateQuery = "UPDATE uzytkownicy SET haslo = ? WHERE id = ?";
   //     try (Connection connection = getConnection();
    //         PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
     //        PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
      //      try (ResultSet resultSet = selectStatement.executeQuery()) {
      //          while (resultSet.next()) {
       //             int userId = resultSet.getInt("id");
        //            String plainPassword = resultSet.getString("haslo");

                    // Sprawdzanie, czy hasło jest już zahashowane
        //            if (!plainPassword.startsWith("$2a$")) { // Hasła BCrypt zaczynają się od "$2a$"
         //               String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
         //               updateStatement.setString(1, hashedPassword);
         //               updateStatement.setInt(2, userId);
          //              updateStatement.executeUpdate();
          //          }
          //      }
          //  }
         //   System.out.println("Migracja haseł zakończona sukcesem.");
        //} catch (SQLException e) {
        //    System.out.println("Błąd podczas migracji haseł:");
        //    e.printStackTrace();
        //}
 //   }
}
