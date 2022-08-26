package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        String host = env("PGHOST");
        String database = env("PGDATABASE");
        String user = env("PGUSER");
        String password = env("PGPASSWORD");
        String url = String.format("jdbc:postgresql://%s/%s", host, database);

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (connection == null) {
            throw new RuntimeException("Missing connection");
        }

        return connection;
    }

    private static String env(String name) {
        String val = System.getenv(name);
        if (val == null) {
            throw new RuntimeException(String.format(
                    "Missing required environment variable `%s`", name));
        }
        return val;
    }
}
