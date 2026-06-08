package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ─────────────────────────────────────────
    // CHANGE PASSWORD TO YOUR MYSQL PASSWORD
    // ─────────────────────────────────────────

    private static final String URL =
        "jdbc:mysql://localhost:3306/admission_db";

    private static final String USER =
        "root";

    private static final String PASSWORD =
        "Prabaldas@123";  // ← change this

    private static Connection connection = null;

    // ─────────────────────────────────────────
    // GET CONNECTION
    // ─────────────────────────────────────────

    public static Connection getConnection() {

        try {

            if (connection == null ||
                connection.isClosed()) {

                Class.forName(
                    "com.mysql.cj.jdbc.Driver"
                );

                connection =
                    DriverManager.getConnection(
                        URL, USER, PASSWORD
                    );

                System.out.println(
                    "✔ DB Connected"
                );
            }

        } catch (ClassNotFoundException e) {

            System.out.println(
                "✘ Driver Not Found: " +
                e.getMessage()
            );

        } catch (SQLException e) {

            System.out.println(
                "✘ Connection Failed: " +
                e.getMessage()
            );
        }

        return connection;
    }
}