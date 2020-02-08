/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    //----------------------------//
    // ADD NEW DATA TO USER TABLE //
    //----------------------------//
    public void createUser(String firstname, String surname, String username, String password) throws SQLException {

        String sql = "INSERT INTO users (firstname, surname, username, password) VALUES(?,?,?,?)";

        query = conn.prepareStatement(sql);

        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, username);
        query.setString(4, password);

        query.executeUpdate();
        query.close();
    }
}
