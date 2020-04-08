/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import CasualPlay.CasualGameSelector;
import CompetitivePlay.CompetitivePlay;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author erino
 */
public class drawerController implements Initializable {

     User currentUser;
    @FXML
    private JFXButton competitive;

    @FXML
    private JFXButton casual, account;

    @FXML
    public void competitiveLoad(ActionEvent event) throws IOException {
        
        SwitchWindow.switchWindow((Stage) competitive.getScene().getWindow(), new CompetitivePlay(currentUser));
        
            
        
    }
    @FXML
    public void accountLoad(ActionEvent event) throws IOException {
     
       SwitchWindow.switchWindow((Stage) account.getScene().getWindow(), new Account(currentUser));
    }
     
    
     @FXML
    public void casualLoad(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) casual.getScene().getWindow(), new CasualGameSelector(currentUser));
    }
    
    @FXML
    public void previousScore(ActionEvent event) throws IOException{
      Parent root;
        root = FXMLLoader.load(getClass().getResource("/PreviousScore/PreviousScore.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Casual Play Selection");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
     public void setData(User user) {
        this.currentUser = user;
       

    }

 

}
