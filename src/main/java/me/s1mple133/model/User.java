package me.s1mple133.model;

import me.s1mple133.controller.UserController;

import java.util.Timer;
import java.util.UUID;

public class User {
    private UUID id;
    private UUID sessionID;
    private String username;
    private String password;

    private Timer sessionTimer;

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sessionID = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSessionID() {
        return sessionID;
    }
}
