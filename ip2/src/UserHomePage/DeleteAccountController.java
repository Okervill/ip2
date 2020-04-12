/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import LoginRegister.Login;
import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import ip2.Hash;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stani
 */
public class DeleteAccountController implements Initializable {
    User currentUser;
    int userId;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton deleteAccount;
    @FXML
    private PasswordField getPassword; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) cancel.getScene().getWindow(), new Account(currentUser));
    }
     @FXML
    private void deleteAccountButton(ActionEvent event) throws SQLException, IOException {
            String pass = getPassword.getText();
            Hash h = new Hash();
            SQLHandler sql = new SQLHandler();
            int userId=currentUser.getUserID();
            if (!h.verifyHash(pass, currentUser.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Password is incorrect, please try again");
            alert.showAndWait();
            return;
         }
            if (pass.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Error");
            alert.setHeaderText("Please enter a password to confirm");
            alert.showAndWait();
            return;
            }
            else {
                sql.deleteAccount(userId);
                SwitchWindow.switchWindow((Stage) deleteAccount.getScene().getWindow(), new Login());
                
            }
}
    public void setData(User currentUser) {
        this.currentUser = currentUser;
    }
    
}
