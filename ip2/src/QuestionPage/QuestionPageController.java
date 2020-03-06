/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import CategoryPage.DeleteCategoryController;
import SQL.SQLHandler;
import ip2.Category;
import ip2.Question;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class QuestionPageController implements Initializable {

    User currentUser;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private TableView<Question> table;

    @FXML
    private TableColumn<Question, String> col_quest;

    @FXML
    private TableColumn<Question, String> col_answer;

    // ObservableList<Question> oblist = FXCollections.observableArrayList();
    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminHomePage/AdminHome.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    public void setData(User user) {
        currentUser = user;
    }

    private void initialiseQuestCol() {
        col_quest.setCellValueFactory(new PropertyValueFactory<>("question"));
        col_answer.setCellValueFactory(new PropertyValueFactory<>("answer"));

    }

    private void loadQuestTable() {
        ObservableList<Question> oblist = FXCollections.observableArrayList();
        

        try {

            Connection conn = SQLHandler.getConn();
            ResultSet rs = conn.createStatement().executeQuery("Select * from Questions");
            while (rs.next()) {
                oblist.add(new Question(rs.getString("QuestionID"), rs.getString("CategoryID"), rs.getString("question"), rs.getString("answer"), rs.getString("wrongAns1"), rs.getString("wrongAns2"), rs.getString("wrongAns3")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_quest.setCellValueFactory(new PropertyValueFactory<>("question"));
        col_answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        table.setItems(oblist);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

        });

        initialiseQuestCol();
        loadQuestTable();

    }

}
