/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import ip2.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class UserHomeController implements Initializable {

    User currentUser;

    @FXML
    private ListView<String> userDisplay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

            ObservableList<String> u = FXCollections.observableArrayList();
            u.add(currentUser.getUserID());
            u.add(currentUser.getFirstname());
            u.add(currentUser.getSurname());
            u.add(currentUser.getUsername());

            userDisplay.setItems(u);
        });
    }

    public void setData(User user) {
        currentUser = user;
    }

}
