/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import HighscorePage.HighscorePage;
import LoginRegister.RegisterUser;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class UserHomeController implements Initializable {

    User currentUser;
    
   
    
    @FXML
    private Label userLabel;
    @FXML
    private Button highScoreButton;

    @FXML
    public void highscoreButton(ActionEvent event) throws IOException {     
        //System.out.println(currentUser);

         SwitchWindow.switchWindow((Stage) highScoreButton.getScene().getWindow(), new HighscorePage(currentUser));

    }
    
    @FXML
    public void casualPlay(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/CasualPlay/CasualPlaySelection.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Casual Play Selection");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }
    
    @FXML
    public void logutButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/LoginRegister/Login.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userLabel.setText("Welcome to the quiz!");
        
    }

    public void setData(User user) {
        currentUser = user;

    }

}
