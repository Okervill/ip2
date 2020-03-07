/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import SQL.SQLHandler;
import ip2.Question;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class ViewQuestionsController implements Initializable {

    @FXML
    private TableView<Question> table;
    @FXML
    private TableColumn<Question, String> col_quest;
    @FXML
    private TableColumn<Question, String> col_answer;
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> getQuestions = new ArrayList<>();
        ArrayList<Question> allQuestions = new ArrayList<>();

        try {
            getQuestions = sql.getAllQuestions();
        } catch (SQLException ex) {
            Logger.getLogger(ViewQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String q : getQuestions) {
            try {
                allQuestions.add(new Question(q));
            } catch (SQLException ex) {
                Logger.getLogger(ViewQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        allQuestions.forEach((q) -> {
            table.getItems().add(q);
        });

        col_quest.setCellValueFactory(new PropertyValueFactory<>("UserQuestion"));
        col_answer.setCellValueFactory(new PropertyValueFactory<>("CorrectAnswer"));

    }

    @FXML
    private void deleteQuestion(ActionEvent event) throws IOException, SQLException {

        TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        Question item = table.getItems().get(index);

        String quest = (String) col_quest.getCellObservableValue(item).getValue();

        ArrayList<String> allQuestions = new ArrayList<>();

        SQLHandler sql = new SQLHandler();
        allQuestions = sql.getAllQuestions();

        delete(quest);
        Parent root;
        root = FXMLLoader.load(getClass().getResource("ViewQuestions.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Questions");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    public void delete(String userquest) throws SQLException, IOException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> questionInfo = sql.searchQuestionTable(userquest);

        String QuestionID = questionInfo.get(0);
        String CategoryID = questionInfo.get(1);
        String quest = questionInfo.get(2);
        String answer = questionInfo.get(3);
        String wrongAns1 = questionInfo.get(4);
        String wrongAns2 = questionInfo.get(5);
        String wrongAns3 = questionInfo.get(6);

        Question currentQuestion = new Question(QuestionID, CategoryID, quest, answer, wrongAns1, wrongAns2, wrongAns3);
        currentQuestion.deleteQuestion(currentQuestion);

    }
    
     @FXML
    private void addQuestion(ActionEvent event) throws IOException, SQLException {
        
          Parent root;
        root = FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));

        Scene scene = new Scene(root);
        Stage add = new Stage(StageStyle.DECORATED);
        add.setTitle("Add Question");
        add.setScene(scene);

        add.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
   



}
