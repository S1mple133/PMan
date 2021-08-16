package me.s1mple133.controller;

import me.s1mple133.database.Database;
import me.s1mple133.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserController {
    private static UserController instance;
    private List<User> loggedUsers;

    private final int SESSION_DURATION = 5000000;

    private UserController() {
        loggedUsers = new ArrayList<>();
    }

    public static UserController getInstance() {
        if(instance == null)
            instance = new UserController();
        return instance;
    }

    /**
     * Logs user in
     * Returns null if not successful
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {

        try(PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement("" +
                "SELECT U_ID FROM P_USER WHERE U_NAME=? AND U_PASS=?");) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    User actUser = new User(UUID.fromString(rs.getString("U_ID")), username, password);

                    loggedUsers.add(actUser);

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    loggedUsers.remove(actUser);
                                }
                            },
                            SESSION_DURATION
                    );

                    return actUser;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Returns user by session id
     * @param sessionID
     * @return
     */
    public User getUserBySessionID(String sessionID) {
        for(User u : loggedUsers) {
            if(u.getSessionID().toString().equals(sessionID)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Registers user and returns  if successfull
     * @param username
     * @param password
     * @param email
     * @return
     */
    public boolean register(String username, String password, String email) {
        try(PreparedStatement preparedStatement = Database.getInstance().getConnection()
                .prepareStatement("INSERT INTO P_USER(u_id, u_name, u_pass, u_email) VALUES(?,?,?,?)")) {
            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()));
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
