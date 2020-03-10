/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import com.jfoenix.controls.JFXButton;
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

    @FXML
    private JFXButton competitive;

  
    @FXML
    private JFXButton casual;

    @FXML
    private void loadCompetitive(ActionEvent event) throws IOException {
       
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        casual.requestFocus();
    }

}
