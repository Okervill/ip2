/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import AdminHomePage.AdminHome;
import CasualPlay.CasualPlaySelection;
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
import javafx.scene.layout.VBox;
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
    private JFXButton casual;

    @FXML
    public void competitiveLoad(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) competitive.getScene().getWindow(), new CompetitivePlay(currentUser));
    }
    
     @FXML
    public void casualLoad(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) casual.getScene().getWindow(), new CasualPlaySelection(currentUser));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

 

}
