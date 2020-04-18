/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import AdminHomePage.AdminHome;
import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import ip2.Question;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author erino
 */
public class ViewQuestionsController implements Initializable {

    User currentUser;
    @FXML
    private TableView<Question> table;
    @FXML
    private TableColumn<Question, String> col_quest;
    @FXML
    private TableColumn<Question, String> col_answer;
    ObservableList<Question> data = FXCollections.observableArrayList();
    String quest;
    @FXML
    private JFXButton editQuest, addQuest, deleteQuest;

    @FXML
    private TextField search;

    @FXML
    private Button home;
    private static final SQLHandler sql = new SQLHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        try {
            data = sql.showQuestionsTable();
        } catch (SQLException ex) {
            Logger.getLogger(ViewQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
          table.setItems(data);

        col_quest.setCellValueFactory(new PropertyValueFactory<>("UserQuestion"));
        col_answer.setCellValueFactory(new PropertyValueFactory<>("CorrectAnswer"));

     
        FilteredList<Question> filtQuest = new FilteredList<>(data, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filtQuest.setPredicate((Predicate<? super Question>) question -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (question.getUserQuestion().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Question> sortedData = new SortedList<>(filtQuest);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
    }

    @FXML
    private String getTablePos() {
        TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        Question item = table.getItems().get(index);

        quest = (String) col_quest.getCellObservableValue(item).getValue();

        return quest;
    }

    @FXML
    private void deleteQuestion(ActionEvent event) throws IOException, SQLException {
        try {
            Question currentQuestion = Question.search(getTablePos());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove the question '' " +getTablePos() + "''?" , ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
             
          currentQuestion.deleteQuestion(currentQuestion);
      
            SwitchWindow.switchWindow((Stage) deleteQuest.getScene().getWindow(), new QuestionPage(currentUser));
           
        }
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Please select a question to delete");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void editQuestion(ActionEvent event) throws IOException, SQLException {
        try {
            Question currentQuestion = Question.search(getTablePos());
            SwitchWindow.switchWindow((Stage) editQuest.getScene().getWindow(), new EditPage(currentQuestion));
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit");
            alert.setHeaderText("Please select a question to edit");
            alert.showAndWait();
            return;
        }
    }

 

    @FXML
    private void addQuestion(ActionEvent event) throws IOException, SQLException {

        SwitchWindow.switchWindow((Stage) addQuest.getScene().getWindow(), new AddQuestion());
    }

    @FXML
    private void homeButton(ActionEvent event) throws IOException, SQLException {

        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new AdminHome(currentUser));
    }

    public void setData(User user) {
        currentUser = user;
    }
}
