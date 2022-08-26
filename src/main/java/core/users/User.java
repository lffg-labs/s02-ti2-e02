package core.users;

import java.time.OffsetDateTime;
import java.util.UUID;

public class User {
    public final UUID id;
    public final String username;
    public final String name;
    public final OffsetDateTime created_at;

    public User(UUID id, String username, String name, OffsetDateTime created_at) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.created_at = created_at;
    }

    public User(String username, String name) {
        this(UUID.randomUUID(), username, name, OffsetDateTime.now());
    }

    @Override
    public String toString() {
        return String.format(
                "User { (%s) %s: %s (created_at %s) }",
                id, username, name, created_at);
    }
}
