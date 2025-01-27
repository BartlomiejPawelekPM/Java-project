package com.example.logowanie;

import java.math.BigDecimal;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/studio_nagran";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean doesUserExist(String username) {
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verifyLogin(String username, String password) {
        String query = "SELECT * FROM uzytkownicy WHERE nazwa_uzytkownika = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("haslo");

                    return BCrypt.checkpw(password, storedHashedPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        if (doesUserExist(username)) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String query = "INSERT INTO uzytkownicy (nazwa_uzytkownika, haslo) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        }
        return false;
    }

    public static void testConnection() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Połączenie z bazą danych zostało nawiązane!");
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
            e.printStackTrace(); // Drukuje szczegóły błędu
        }
    }

    public static void migratePasswordsToHash() {
        String selectQuery = "SELECT id, haslo FROM uzytkownicy";
        String updateQuery = "UPDATE uzytkownicy SET haslo = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String plainPassword = resultSet.getString("haslo");

                    if (!plainPassword.startsWith("$2a$")) {
                        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                        updateStatement.setString(1, hashedPassword);
                        updateStatement.setInt(2, userId);
                        updateStatement.executeUpdate();
                    }
                }
            }
            System.out.println("Migracja haseł zakończona sukcesem.");
        } catch (SQLException e) {
            System.out.println("Błąd podczas migracji haseł:");
            e.printStackTrace();
        }
    }

    public static User getUserProfile(int userId) {
        User user = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM uzytkownicy WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nazwa_uzytkownika"),
                        resultSet.getString("email"),
                        resultSet.getString("imie"),
                        resultSet.getString("nazwisko"),
                        resultSet.getString("adres"),
                        resultSet.getBigDecimal("saldo") // Poprawne pobranie salda jako BigDecimal
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean updateUserProfile(int userId, String username, String email, String imie, String nazwisko, String adres) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE uzytkownicy SET nazwa_uzytkownika = ?, email = ?, imie = ?, nazwisko = ?, adres = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, imie);
            statement.setString(4, nazwisko);
            statement.setString(5, adres);
            statement.setInt(6, userId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static BigDecimal getSaldo(int userId) {
        String query = "SELECT saldo FROM uzytkownicy WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("saldo");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }


    public static boolean updateSaldo(int userId, BigDecimal saldo) {
        String query = "UPDATE uzytkownicy SET saldo = saldo + ? WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, saldo);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
