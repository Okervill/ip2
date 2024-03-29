/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginRegister;

import SQL.SQLHandler;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField getFirstname;
    @FXML
    private JFXTextField getSurname;
    @FXML
    private JFXTextField getUsername;
    @FXML
    private JFXPasswordField getPassword;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private JFXCheckBox check13;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void register(ActionEvent event) throws SQLException {
        String firstname, surname, username, password;
        firstname = getFirstname.getText();

        surname = getSurname.getText();

        username = getUsername.getText();
        password = getPassword.getText();

        ArrayList<String> allUsers = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allUsers = sql.getAllUsers();
        if (!check13.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Age Restriction");
            alert.setHeaderText("Please confirm that you are 13 or over");
            alert.showAndWait();
            return;
        }
        if (allUsers.contains(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("This username is taken, please choose another");
            alert.showAndWait();
            return;
        }

        if (User.match(username) == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("Username cannot contain special characters, please choose another");
            alert.showAndWait();
            return;
        }
        if (User.match(password) == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Password cannot contain special characters, please choose another");
            alert.showAndWait();
        }
        if (password.length() < 8 || password.length() > 32) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Password must be between 8-32 characters");
            alert.showAndWait();
        }
        if (username.length() < 4) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("Username must be at least 4 characters");
            alert.showAndWait();
        }

        if (allUsers.contains(username) || User.match(password) == true || User.match(username) == true || (password.length() < 8 || password.length() > 32) || 
                username.length() < 4 || firstname.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || !check13.isSelected()) {
            registerFailed();

        } else {
            surname = surname.substring(0, 1).toUpperCase() + surname.substring(1).toLowerCase();
            firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
            Hash h = new Hash();
            password = h.hash(password);
            
            User.createUser(firstname, surname, username, password, "false", "0");

            SwitchWindow.switchWindow((Stage) registerButton.getScene().getWindow(), new Login());
        }
    }

    @FXML
    private void loadLoginPage(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) registerButton.getScene().getWindow(), new Login());
    }

    private void registerFailed() {
        Shaker shake = new Shaker(registerButton);
        shake.shake();
        getFirstname.requestFocus();
        getUsername.getStyleClass().add("wrong");
    }

    @FXML
    private void swapFocusSurname(ActionEvent event) {
        getSurname.requestFocus();
    }

    @FXML
    private void swapFocusUsername(ActionEvent event) {
        getUsername.requestFocus();
    }

    @FXML
    private void swapFocusPassword(ActionEvent event) {
        getPassword.requestFocus();
    }

}
