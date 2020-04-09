/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author erino
 */
public class AccountController implements Initializable {

    User currentUser;
    @FXML
    private Button home;
    @FXML
    private JFXHamburger hamburger;
   

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Label firstName, rank;

    @FXML
    private Label secName;

    @FXML
    private Label userName;

    @FXML
    private Label highScore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Platform.runLater(new Runnable() {

            @Override
            public void run() {
              
                
                firstName.setText(currentUser.getFirstname());
                secName.setText(currentUser.getSurname());
                userName.setText(currentUser.getUsername());
                highScore.setText(currentUser.getUserScore());
        
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserHomePage/pullout.fxml"));
                    VBox box = loader.load();
                    drawer.setSidePane(box);
                    drawerController controller = loader.getController();

                    controller.setData(currentUser);

                    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
                    transition.setRate(-1);
                    hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        transition.setRate(transition.getRate() * -1);
                        transition.play();

                        if (drawer.isOpened()) {
                            drawer.close();
                            drawer.setDisable(true);
                        } else {
                            drawer.open();
                            drawer.setDisable(false);

                        }
                    }
                    );
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void home(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));
    }

    public void setData(User user) {
        currentUser = user;

    }

}
