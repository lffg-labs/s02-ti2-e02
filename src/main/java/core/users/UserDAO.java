package core.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAll() throws SQLException {
        PreparedStatement s = connection.prepareStatement(
                "SELECT id, username, name, created_at FROM users");
        ResultSet res = s.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while (res.next()) {
            list.add(setToUser(res));
        }
        return list;
    }

    public User get(String username) throws SQLException {
        PreparedStatement s = connection.prepareStatement(
                "SELECT id, username, name, created_at FROM users WHERE username = ?");
        s.setString(1, username);
        ResultSet res = s.executeQuery();
        res.next();
        return setToUser(res);
    }

    public void insert(User user) throws SQLException {
        PreparedStatement s = connection.prepareStatement(
                "INSERT INTO users (id, username, name, created_at) VALUES (?, ?, ?, ?)");
        s.setObject(1, user.id, java.sql.Types.OTHER);
        s.setString(2, user.username);
        s.setString(3, user.name);
        s.setObject(4, user.created_at);
        s.execute();
    }

    private User setToUser(ResultSet res) throws SQLException {
        return new User(
                res.getObject("id", UUID.class),
                res.getString("username"),
                res.getString("name"),
                res.getObject("created_at", OffsetDateTime.class));
    }
}
