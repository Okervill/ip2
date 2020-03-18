/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import ip2.Question;
import ip2.Shaker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author erino
 */
public class EditController implements Initializable {

    Question currentQuestion;

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
    private JFXComboBox<String> categoryCombo;

    @FXML
    private JFXButton addQuest;
    int tempcat;
    int questID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {

            correctAns.setText(currentQuestion.getCorrectAnswer());
            questionArea.setText(currentQuestion.getUserQuestion());
            wrongAns1.setText(currentQuestion.getWrongAnswer1());
            wrongAns2.setText(currentQuestion.getWrongAnswer2());
            wrongAns3.setText(currentQuestion.getWrongAnswer3());
            tempcat = currentQuestion.getCategoryId();
            questID = currentQuestion.getQuestionId();

            questionArea.requestFocus();
            questionArea.end();

            try {
                String categoryName = fetchCatName(tempcat);

                categoryCombo.getSelectionModel().select(categoryName);

            } catch (SQLException ex) {
                Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

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

    private String fetchCatName(int tempcat) throws SQLException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> categoryInfo = sql.searchIDCategoriesTable(tempcat);

        String categoryName = categoryInfo.get(0);

        return categoryName;

    }

    private int fetchCatId(String catId) throws SQLException {

        SQLHandler sql = new SQLHandler();
        List categoryInfo = sql.searchCategoriesTable(catId);

        int tempcategoryId = (int) categoryInfo.get(0);

        return tempcategoryId;

    }

    public void setData(Question question) {
        currentQuestion = question;

    }

    @FXML
    public void addQuestion(ActionEvent event) throws IOException, SQLException {
        String answer = correctAns.getText();
        String question = questionArea.getText();
        String wrong1 = wrongAns1.getText();
        String wrong2 = wrongAns2.getText();
        String wrong3 = wrongAns3.getText();

        String tempcat = categoryCombo.getSelectionModel().getSelectedItem();

        int catId = fetchCatId(tempcat);

        if (answer.isEmpty() || question.isEmpty() || wrong1.isEmpty() || wrong2.isEmpty() || wrong3.isEmpty() || categoryCombo.getSelectionModel().isEmpty()) {

            addQuestionFailed();

        } else {

            Question newQuestion = new Question(questID, catId, question, answer, wrong1, wrong2, wrong3);
            newQuestion.editQuestion(newQuestion);

            Parent root;
            root = FXMLLoader.load(getClass().getResource("ViewQuestions.fxml"));

            Scene scene = new Scene(root);
            Stage reg = new Stage(StageStyle.DECORATED);
            reg.setTitle("Home");
            reg.setScene(scene);

            reg.show();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

        }

    }

    private void addQuestionFailed() {
        Shaker shake = new Shaker(addQuest);
        shake.shake();
        questionArea.requestFocus();
    }

    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/QuestionPage/ViewQuestions.fxml"));

        Scene scene = new Scene(root);
        Stage view = new Stage(StageStyle.DECORATED);
        view.setTitle("View");
        view.setScene(scene);

        view.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

}
