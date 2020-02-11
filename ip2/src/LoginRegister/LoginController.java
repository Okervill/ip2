/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginRegister;

import SQL.SQLHandler;
import ip2.Shaker;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class LoginController implements Initializable {

    @FXML
    private TextField inputUser;
    @FXML
    private TextField inputPass;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
        String username = inputUser.getText();
        String password = inputPass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        SQLHandler sql = new SQLHandler();
        //Get all users
        ArrayList<String> user = sql.searchUsersTable(username);

        if (user.size() < 5) {
            loginFailed();
        } else if (password.equals(user.get(4))) {
            System.out.print("\nSuccess");
        } else {
            loginFailed();
        }
    }

    public void loginFailed() {
        Shaker shaker = new Shaker(loginButton);
        shaker.shake();
        inputUser.requestFocus();
    }

    @FXML
    private void loadRegister(ActionEvent event) {
    }

}
