package org.hinoob.blockinator.database;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        // sqlite
    }

    public void load() {
        File f = new File("database.db");

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + f.getAbsolutePath());

            this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
            this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS worlds (name TEXT, blocks LONGTEXT)");
            // craete test account
            this.connection.createStatement().execute("INSERT INTO users (username, password) VALUES ('test', 'test')");

            JsonArray array = new JsonArray();
            // 100x100 (height)
            // 100x50 dirt

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    JsonObject object = new JsonObject();
                    object.addProperty("x", i);
                    object.addProperty("y", j);
                    object.addProperty("id", "dirt");
                    array.add(object);
                }
            }

            this.connection.createStatement().execute("INSERT INTO worlds (name, blocks) VALUES ('test', '" + array.toString() + "')");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query, Object... params) {
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean accountExists(String username, String password) {
        try {
            ResultSet rs = query("SELECT * FROM users WHERE username = ? AND password = ?", username, password);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getAccountId(String username, String password) {
        try {
            ResultSet rs = query("SELECT * FROM users WHERE username = ? AND password = ?", username, password);
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public JsonArray getWorld(String name) {
        try {
            ResultSet rs = query("SELECT * FROM worlds WHERE name = ?", name);
            if (rs.next()) {
                return new Gson().fromJson(rs.getString("blocks"), JsonArray.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
