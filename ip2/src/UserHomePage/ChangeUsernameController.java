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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stani
 */
public class ChangeUsernameController implements Initializable {
    User currentUser;
    int userId;
    @FXML
    private JFXButton updateUsername;
     
    @FXML
    private JFXButton cancel;
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
    public void updateUsernameButton(ActionEvent event) throws IOException, SQLException {
        String name = getUsername.getText();
        ArrayList<String> allUsers = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allUsers = sql.getAllUsers();
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher match = pattern.matcher(name);
        boolean val = match.find();

        if (allUsers.contains(name)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("This username already exists, please choose another");
            alert.showAndWait();
            return;
        }
        if (name.isEmpty()) {

            addUsernameFailed();
            }
        if (val==true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Error");
            alert.setHeaderText("Invalid username, please choose another");
            alert.showAndWait();
            return;
        }
         else {

            currentUser.setUsername(name);
            currentUser.editUsername(currentUser);

               SwitchWindow.switchWindow((Stage) updateUsername.getScene().getWindow(), new Account(currentUser));

        }
    }
        
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            getUsername.setText(currentUser.getUsername());
        });
    }
    private void addUsernameFailed() {
        Shaker shake = new Shaker(updateUsername);
        shake.shake();
        getUsername.requestFocus();
}
    public void setData(User currentUser) {
        this.currentUser = currentUser;
    }
     
}
