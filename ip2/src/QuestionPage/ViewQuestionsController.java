/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import SQL.SQLHandler;
import ip2.Question;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class ViewQuestionsController implements Initializable {

    @FXML
    private TableView<Question> table;
    @FXML
    private TableColumn<String, String> col_quest;
    @FXML
    private TableColumn<String, String> col_answer;

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

}
