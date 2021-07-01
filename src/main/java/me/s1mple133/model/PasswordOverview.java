package me.s1mple133.model;

public class PasswordOverview {
    private String user;
    private String application;
    private String id;

    public PasswordOverview(String user, String application, String id) {
        this.user = user;
        this.application = application;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public String getApplication() {
        return application;
    }

    public String getId() {
        return id;
    }
}
