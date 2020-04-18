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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView imageRank;

    int rankScore = 0;
    @FXML
    private Button deleteAccount;
    @FXML 
    private Button changeUsername;
     @FXML 
    private Button changePassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                rankScore = Integer.parseInt(currentUser.getUserScore());

                if (rankScore < 50) {
                    rank.setText("Newbie");
                    File file = new File("src/Resources/new.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);
                }

                if (rankScore >= 50 && rankScore < 100) {
                    rank.setText("Bronze");
                    File file = new File("src/Resources/bronze.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);
                }

                if (rankScore >= 100 && rankScore < 150) {
                    rank.setText("Silver");
                    File file = new File("src/Resources/silver.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);
                }

                if (rankScore >= 150 && rankScore < 500) {
                    rank.setText("Gold");
                    File file = new File("src/Resources/gold.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);

                }

                if (rankScore >= 500 && rankScore < 1000) {
                    rank.setText("Diamond");
                    File file = new File("src/Resources/diamond.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);
                }

                if (rankScore >= 1000) {
                    rank.setText("Legend");
                    File file = new File("src/Resources/phoenix.png");
                    Image image = new Image(file.toURI().toString());
                    imageRank.setImage(image);
                }
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
    @FXML
    private void deleteAccount(ActionEvent event) throws SQLException, IOException {

            SwitchWindow.switchWindow((Stage) deleteAccount.getScene().getWindow(), new DeleteAccount(currentUser));
        }

    
    @FXML
    private void changeUsername(ActionEvent event){
        SwitchWindow.switchWindow((Stage) changeUsername.getScene().getWindow(), new ChangeUsername(currentUser));
    }
    @FXML
    private void changePassword(ActionEvent event){
        SwitchWindow.switchWindow((Stage) changePassword.getScene().getWindow(), new ChangePassword(currentUser));
    }
    public void setData(User user) {
        currentUser = user;

    }

}
