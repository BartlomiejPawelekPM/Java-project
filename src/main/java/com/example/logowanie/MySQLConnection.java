package com.example.logowanie;

import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/studio_nagran";
    private static final String USER = "root"; // Użytkownik MySQL
    private static final String PASSWORD = ""; // Twoje hasło MySQL (lub puste, jeśli brak)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Połączenie z bazą danych
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Połączono z bazą danych!");

            // Przykład zapytania SELECT
            String query = "SELECT * FROM uzytkownicy";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Wyświetlanie wyników
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");

                System.out.println("ID: " + id + ", imie: " + name + ", nazwisko: " + nazwisko);
            }

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych:");
            e.printStackTrace();
        } finally {
            // Zamknięcie połączenia
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Połączenie zamknięte.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
