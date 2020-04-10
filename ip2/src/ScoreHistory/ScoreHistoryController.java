/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScoreHistory;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import UserHomePage.UserHomeController;
import UserHomePage.drawerController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import ip2.HighScore;
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
import javafx.scene.control.TableColumn.SortType;
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
public class ScoreHistoryController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    User currentUser;
    @FXML
    private Button home;
    @FXML
    private TableView<HighScore> highScoreTable1;
    @FXML
    private TableColumn<HighScore, Integer> n;
    @FXML
    private TableColumn<HighScore, String> n1;
    ObservableList<HighScore> data = FXCollections.observableArrayList();

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
                //  System.out.println(currentUser.getCompetitiveBankID());
                Connection conn = SQLHandler.getConn();
                String sql = "select quizNo,score from comp_" + currentUser.getUserID() + "";
                System.out.println(sql);
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    data.add(new HighScore(rs.getInt("quizNo"), rs.getString("score")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScoreHistoryController.class.getName()).log(Level.SEVERE, null, ex);
            }

            n.setCellValueFactory(new PropertyValueFactory<>("quizNo"));
            n1.setCellValueFactory(new PropertyValueFactory<>("score"));
          
           highScoreTable1.setItems(data);
            n.setSortType(SortType.DESCENDING);
            highScoreTable1.getSortOrder().add(n);

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

        });

    }

    public void setData(User user) {
        currentUser = user;
    }

}
