/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import CategoryPage.ViewCategory;
import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import ip2.Category;
import ip2.Hash;
import ip2.Shaker;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stani
 */
public class ChangePasswordController implements Initializable {
    User currentUser;
    int userId;
    @FXML
    private PasswordField getOldPassword;
    @FXML
    private PasswordField getNewPassword; 
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton updatePassword;
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField getUsername;
    
     
    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) cancel.getScene().getWindow(), new Account(currentUser));
    }
    
    @FXML
    public void updatePasswordButton(ActionEvent event) throws IOException, SQLException {
        String oldPass = getOldPassword.getText();
        String newPass = getNewPassword.getText();
        ArrayList<String> allUsers = new ArrayList<>();
        Hash h = new Hash();
        SQLHandler sql = new SQLHandler();
        if (!h.verifyHash(oldPass, currentUser.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Old password is incorrect, please try again");
            alert.showAndWait();
            return;
         }

        
        if (oldPass.isEmpty()||newPass.isEmpty()) {

            changePasswordFailed();
            }
       if (User.match(newPass) == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Password cannot contain special characters, please choose another");
            alert.showAndWait();
        }
        if (newPass.length() < 8 || newPass.length() > 32) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Password must be between 8-32 characters");
            alert.showAndWait();
        }
         else {
            newPass = h.hash(newPass);
            currentUser.setPassword(newPass);
            currentUser.editPassword(currentUser);

               SwitchWindow.switchWindow((Stage) updatePassword.getScene().getWindow(), new Account(currentUser));

        }
    }
        
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }
    private void changePasswordFailed() {
        Shaker shake = new Shaker(updatePassword);
        shake.shake();
        getOldPassword.requestFocus();
}
    public void setData(User currentUser) {
        this.currentUser = currentUser;
    }
     
}
