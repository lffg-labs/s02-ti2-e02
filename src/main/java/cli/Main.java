package cli;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import core.users.User;
import core.users.UserDAO;
import lib.Database;

class Cli {
    private final Scanner scanner;
    private final Connection connection;
    private final UserDAO userDAO;

    public Cli() throws SQLException {
        scanner = new Scanner(System.in);
        connection = Database.getConnection();
        userDAO = new UserDAO(connection);
    }

    public boolean promptAndProceed() throws SQLException {
        System.out.println("Pick an option:");
        System.out.println("0 - Exit");
        System.out.println("1 - Fetch all users");
        System.out.println("2 - Fetch an user by its `username`");
        System.out.println("3 - Insert a new user");
        System.out.println("4 - Update an existing user");
        System.out.println("5 - Remove an user");

        String answer = getLine(">>> ");

        System.out.println("< " + answer);

        switch (answer) {
            case "0": {
                return false;
            }

            case "1": {
                List<User> users = userDAO.getAll();
                for (User user : users) {
                    System.out.println(user);
                }
                break;
            }

            case "2": {
                String username = getLine(">>> username: ");
                User user = userDAO.get(username);
                if (user == null) {
                    System.out.println(String.format(
                            "User with username `%s` not found.", username));
                } else {
                    System.out.println(user);
                }
                break;
            }

            case "3": {
                String username = getLine(">>> username: ");
                String name = getLine(">>> full name: ");
                userDAO.insert(new User(username, name));
                System.out.println("ok.");
                break;
            }

            default: {
                System.out.println("Not supported, or invalid command.");
            }
        }

        return true;
    }

    private String getLine(String prompt) {
        try {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in == "") {
                return null;
            }
            return in;
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return "";
    }
}

public class Main {
    public static void main(String[] args) throws SQLException {
        Cli cli = new Cli();
        while (cli.promptAndProceed()) {
            System.out.print("\n");
        }
    }
}
