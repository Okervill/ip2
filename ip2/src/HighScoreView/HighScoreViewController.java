/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HighScoreView;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import ip2.Drawer;
import ip2.LeaderBoardScore;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class HighScoreViewController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    User currentUser;
    @FXML
    private Button home;
    @FXML
    private TableView<LeaderBoardScore> highScoreTable;
    @FXML
    private TableColumn<LeaderBoardScore, String> name;
    @FXML
    private TableColumn<LeaderBoardScore, Integer> name1;
    ObservableList<LeaderBoardScore> data = FXCollections.observableArrayList();
    SQLHandler sql = new SQLHandler();
    @FXML
    public void homeButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            try {
                data=sql.getHighScores();
                } catch (SQLException ex) {
                Logger.getLogger(HighScoreViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            name.setCellValueFactory(new PropertyValueFactory<>("nasyme"));
            name1.setCellValueFactory(new PropertyValueFactory<>("score"));
            highScoreTable.setItems(data);
            name1.setSortType(TableColumn.SortType.DESCENDING);
            highScoreTable.getSortOrder().add(name1);
            Drawer newdrawer = new Drawer();
            newdrawer.drawerPullout(drawer, currentUser, hamburger);
        });

    }

    public void setData(User user) {
        currentUser = user;
    }

}
