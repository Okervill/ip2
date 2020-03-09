/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import SQL.SQLHandler;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import ip2.Question;
import ip2.Shaker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author erino
 */
public class AddQuestionController implements Initializable {

    @FXML
    private JFXTextField correctAns;

    @FXML
    private TextArea questionArea;

    @FXML
    private JFXTextField wrongAns1;

    @FXML
    private JFXTextField wrongAns2;

    @FXML
    private JFXTextField wrongAns3;

    @FXML
    private Button addQuest;

    @FXML
    private JFXComboBox<String> categoryCombo;

    @FXML
    private void addQuestion(ActionEvent event) throws SQLException, IOException {
        String answer = correctAns.getText();
        String question = questionArea.getText();
        String wrong1 = wrongAns1.getText();
        String wrong2 = wrongAns2.getText();
        String wrong3 = wrongAns3.getText();

       /* ArrayList<String> allQuestions = new ArrayList<>();
        SQLHandler sql2 = new SQLHandler();
        allQuestions = sql2.getAllQuestions();
*/
        if (answer.isEmpty() || question.isEmpty() || wrong1.isEmpty() || wrong2.isEmpty() || wrong3.isEmpty() || categoryCombo.getSelectionModel().isEmpty()) {

            addQuestionFailed();

        } else {
            String tempcat = categoryCombo.getSelectionModel().getSelectedItem();

            String categoryId = fetchCatInfo(tempcat);

            Question newQuestion = new Question(categoryId, question, answer, wrong1, wrong2, wrong3);
            newQuestion.createQuestion(newQuestion);
            Parent root;
            root = FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));

            Scene scene = new Scene(root);
            Stage reg = new Stage(StageStyle.DECORATED);
            reg.setTitle("Home");
            reg.setScene(scene);

            reg.show();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

        }
    }
    
    @FXML
    private void cancelButton(ActionEvent event) throws SQLException, IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/QuestionPage/ViewQuestions.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    private String fetchCatInfo(String tempcat) throws SQLException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> categoryInfo = sql.searchCategoriesTable(tempcat);

        String tempcategoryId = categoryInfo.get(0);
        

        return tempcategoryId;

    }

    private void addQuestionFailed() {
        Shaker shake = new Shaker(addQuest);
        shake.shake();
        questionArea.requestFocus();
    }
    
     @FXML
    public void finishButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/QuestionPage/ViewQuestions.fxml"));

        Scene scene = new Scene(root);
        Stage view = new Stage(StageStyle.DECORATED);
        view.setTitle("View");
        view.setScene(scene);

        view.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> allCategories = new ArrayList<>();
        SQLHandler sql1 = new SQLHandler();
        try {
            allCategories = sql1.getAllCategories();
        } catch (SQLException ex) {
            Logger.getLogger(AddQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> data = FXCollections.observableArrayList(allCategories);

        categoryCombo.setItems(data);
    }

}
