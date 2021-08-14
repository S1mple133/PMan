package me.s1mple133.controller;

import me.s1mple133.database.Database;
import me.s1mple133.model.PasswordOverview;
import me.s1mple133.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasswordController {
    private static PasswordController instance;

    private PasswordController() {

    }

    public static PasswordController getInstance() {
        if (instance == null)
            instance = new PasswordController();

        return instance;
    }

    /**
     * Returns user overview
     *
     * @param user
     * @return
     */
    public List<PasswordOverview> getUserOverview(User user) {
        List<PasswordOverview> result = new ArrayList<>();
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement prst = conn.prepareStatement("SELECT P_ID, P_USER, P_APPLICATION FROM P_Passwords WHERE P_USER_ID = ?")) {
            prst.setString(1, user.getId().toString());
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                result.add(new PasswordOverview(
                        rs.getString("P_USER"),
                        rs.getString("P_APPLICATION"),
                        rs.getString("P_ID")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * inserts a new password overview
     * @param user
     * @param overview
     */
    public boolean insertUserOverview(User user, PasswordOverview overview) {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement prst = conn.prepareStatement("INSERT INTO P_Passwords(P_USER_ID, P_ID, P_USER, P_APPLICATION, P_PASS) VALUES(?,?,?,?,?)")) {
            prst.setString(1, user.getId().toString());
            prst.setString(2, overview.getId().toString());
            prst.setString(3, overview.getUser().toString());
            prst.setString(4, overview.getApplication().toString());
            prst.setString(5, "");
            prst.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public String getPasswordForUser() {
        return null;
    }
}
