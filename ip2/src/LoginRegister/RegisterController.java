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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();

        surname = getSurname.getText();
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1).toLowerCase();
        username = getUsername.getText();
        password = getPassword.getText();

        ArrayList<String> allUsers = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allUsers = sql.getAllUsers();
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher match = pattern.matcher(username);
        Matcher match2 = pattern.matcher(password);
        boolean valUser = match.find();
        boolean valPass = match2.find();

        if (allUsers.contains(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("This username is taken, please choose another");
            alert.showAndWait();
            return;
        }

        if (valUser == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("Username cannot contain special characters, please choose another");
            alert.showAndWait();
            return;
        }
        if (valPass == true) {
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

        if (firstname.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || !check13.isSelected()) {
            registerFailed();

        } else {
            Hash h = new Hash();
            password = h.hash(password);
            User newUser = new User(firstname, surname, username, password, "false", "0");
            newUser.createUser(newUser);
            sql.createCasualTables(newUser.getCasualBankID());
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
