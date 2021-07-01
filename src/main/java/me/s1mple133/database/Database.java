package me.s1mple133.database;

import javax.sql.DataSource;
import java.sql.*;

public class Database {

    static final String DATABASE = "postgres";
    static final String USERNAME = "app";
    static final String PASSWORD = "app";
    public static final String URL = "jdbc:postgresql://localhost:5432/" + DATABASE;
    private Connection conn;


    private static Database instance;

    private Database() {
        try {
            initDbIfNoDataExistent();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Database getInstance() {
        if(instance == null)
            instance = new Database();

        return instance;
    }

    public Connection getConnection(){
        try {
            if(conn == null || conn.isClosed())
                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void initDbIfNoDataExistent() throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();

        String   catalog          = null;
        String   schemaPattern    = null;
        String   tableNamePattern = "p_user";
        String[] types            = null;

        ResultSet result = databaseMetaData.getTables(
                catalog, schemaPattern, tableNamePattern, types );

        if(!result.next()) {
            dropAndCreateTables();
        }
    }

    private void dropAndCreateTables() {
        try {
            PreparedStatement stmt = getConnection().prepareStatement("" +
                    "CREATE TABLE P_User (" +
                    "    U_ID VARCHAR(255) NOT NULL PRIMARY KEY," +
                    "    U_NAME VARCHAR(255) NOT NULL," +
                    "    U_PASS VARCHAR(255) NOT NULL," +
                    "    U_EMAIL VARCHAR(255) NOT NULL" +
                    ");" +
                    "CREATE TABLE P_Passwords (" +
                    "                        P_ID VARCHAR(38) NOT NULL PRIMARY KEY," +
                    "                        P_PASS VARCHAR(255) NOT NULL," +
                    "                        P_USER VARCHAR(255) NOT NULL," +
                    "                        P_USER_ID VARCHAR(38) NOT NULL," +
                    "                        P_APPLICATION VARCHAR(255) NOT NULL" +
                    ");");
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
