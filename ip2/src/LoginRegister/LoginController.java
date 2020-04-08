/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginRegister;

import AdminHomePage.AdminHome;
import SQL.SQLHandler;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import ip2.Hash;
import ip2.Shaker;
import ip2.SwitchWindow;
import ip2.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField inputUser;
    @FXML
    private JFXPasswordField inputPass;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
        String username = inputUser.getText().trim();
        String password = inputPass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            inputUser.getStyleClass().add("wrong");
            return;
        }

        Hash h = new Hash();
        SQLHandler sql = new SQLHandler();
        ArrayList<String> user = sql.searchUsersTable(username);

        if (user.size() < 9) {
            loginFailed();
        } else if (!h.verifyHash(password, user.get(7))) {
            loginFailed();
        } else {
            login(username);
        }
    }

    public void loginFailed() {
        Shaker shaker = new Shaker(loginButton);
        shaker.shake();
        inputPass.setText("");
        inputUser.requestFocus();
    }

    public void login(String user) throws SQLException {
        User currentUser = new User(user);
        String userType = currentUser.getUserType();
        if (userType.equals("true")) {
            SwitchWindow.switchWindow((Stage) loginButton.getScene().getWindow(), new AdminHome(currentUser));
        } else {
            SwitchWindow.switchWindow((Stage) loginButton.getScene().getWindow(), new UserHome(currentUser));
        }
    }

    @FXML
    private void loadRegister(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) registerButton.getScene().getWindow(), new RegisterUser());
    }

    @FXML
    private void swapFocusPassword(ActionEvent event) {
        inputPass.requestFocus();
    }

}
