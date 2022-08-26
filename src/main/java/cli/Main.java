package cli;

import java.sql.Connection;
import java.sql.SQLException;

import lib.Database;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = Database.getConnection();
        System.out.println("Hello, world! Connected: " + conn);
    }
}
