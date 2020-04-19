/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import CasualPlay.CasualGameSelector;
import CompetitivePlay.CompetitivePlay;
import HighScoreView.HighScoreView;
import ScoreHistory.ScoreHistory;
import com.jfoenix.controls.JFXButton;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 *
 * @author erino
 */
public class drawerController implements Initializable {

    User currentUser;
    @FXML
    private JFXButton competitive;

    @FXML
    private JFXButton casual, account, previousscore, highscore;

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
    public void highScore(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) highscore.getScene().getWindow(), new HighScoreView(currentUser));
    }

    @FXML
    public void previousScore(ActionEvent event) throws IOException {

        SwitchWindow.switchWindow((Stage) previousscore.getScene().getWindow(), new ScoreHistory(currentUser));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(User user) {
        this.currentUser = user;

    }

}
