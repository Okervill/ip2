/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import ip2.Hash;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLHandler {

    Connection conn = SQLHandler.getConn();
    PreparedStatement query;

    public SQLHandler() {

    }

    //----------------------//
    // CONNECT TO SQLITE DB //
    //----------------------//
    public static Connection getConn() {

        String url = "jdbc:sqlite:src/SQL/ip2.db";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
            return null;
        }
        return conn;
    }

    //-----------------------------//
    // ADD NEW DATA TO LOGIN TABLE //
    //-----------------------------//
    public void createUser(String userid, String firstname, String surname, String username, String password, String admin) throws SQLException {

        String sql = "INSERT INTO users (userid, firstname, surname, username, password, isadmin) VALUES(?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);
        
        query.setString(1, userid);
        query.setString(2, firstname);
        query.setString(3, surname);
        query.setString(4, username);
        query.setString(5, password);
        query.setString(6, admin);

        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL USERNAMES FROM USERS TABLE //
    //------------------------------------//
    public ArrayList getAllUsers() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT username FROM users";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("username"));
        }

        query.close();
        return output;
    }

    public ArrayList searchUsersTable(String searchQuery) throws SQLException {
        
        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT userid, firstname, surname, username, password, isadmin FROM users WHERE username = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getString("userid")));
            output.add((rs.getString("firstname")));
            output.add((rs.getString("surname")));
            output.add((rs.getString("username")));
            output.add((rs.getString("password")));
            output.add((rs.getString("isadmin")));
        }
        return output;
    }
}
