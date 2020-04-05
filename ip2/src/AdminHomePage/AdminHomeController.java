/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminHomePage;

import CategoryPage.ViewCategory;
import LoginRegister.Login;
import QuestionPage.QuestionPage;
import com.jfoenix.controls.JFXButton;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class AdminHomeController implements Initializable {

    User currentUser;
    @FXML
    private JFXButton catButton;

    @FXML
    private JFXButton questButton;

    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

        });
    }

    @FXML
    public void logoutButton(ActionEvent event) throws IOException {
      SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new Login());

    }

    @FXML
    public void categoryPage(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) catButton.getScene().getWindow(), new ViewCategory(currentUser));

    }

    @FXML
    public void questionPage(ActionEvent event) throws IOException {
       SwitchWindow.switchWindow((Stage) catButton.getScene().getWindow(), new QuestionPage(currentUser));
    }

    public void setData(User user) {
        currentUser = user;
    }

}
