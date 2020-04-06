/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HighScoreTable;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import UserHomePage.UserHomeController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import ip2.LeaderBoardScore;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class HighScoreController implements Initializable {

    User currentUser;
    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private Button home;
    @FXML
    private TableView<LeaderBoardScore> highScoreTable;
    @FXML
    private TableColumn<LeaderBoardScore, String> name;
    @FXML
    private TableColumn<LeaderBoardScore, String> name1;
    ObservableList<LeaderBoardScore> data = FXCollections.observableArrayList();

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
                //System.out.println(currentUser);
                Connection conn = SQLHandler.getConn();
                String sql = "select Username,UserScore from Users where isAdmin = \"" + "false" + "\"";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    data.add(new LeaderBoardScore(rs.getString("Username"), rs.getString("UserScore")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(HighScoreController.class.getName()).log(Level.SEVERE, null, ex);
            }

            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            name1.setCellValueFactory(new PropertyValueFactory<>("score"));
            highScoreTable.setItems(data);

        });

        try {
            VBox box = FXMLLoader.load(getClass().getResource("pullout.fxml"));
            drawer.setSidePane(box);

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

    public void setData(User user) {
        currentUser = user;
    }

}
