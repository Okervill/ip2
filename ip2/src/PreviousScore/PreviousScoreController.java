/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreviousScore;

import SQL.SQLHandler;
import ip2.HighScore;
import ip2.LeaderBoardScore;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class PreviousScoreController implements Initializable {

    User currentUser;
    @FXML
    private Label userLabel;
    @FXML
    private TableView<LeaderBoardScore> highScoreTable;
    @FXML
    private TableColumn<LeaderBoardScore, String> name;
    @FXML
    private TableColumn<LeaderBoardScore, String> name1;
    ObservableList<LeaderBoardScore> data = FXCollections.observableArrayList();

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/UserHomePage/UserHome.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

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
                    data.add(new LeaderBoardScore(rs.getString("Username"),rs.getString("UserScore")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PreviousScoreController.class.getName()).log(Level.SEVERE, null, ex);
            }

         name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name1.setCellValueFactory(new PropertyValueFactory<>("score"));
        highScoreTable.setItems(data);

      });
    }

    public void setData(User user) {
        currentUser = user;
    }

}
