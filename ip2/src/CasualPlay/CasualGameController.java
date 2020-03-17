/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import ip2.Question;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CasualGameController implements Initializable {

    User currentUser;

    String categorySelected = CasualGame.getUserSelection();

    ArrayList<Question> questions = new ArrayList<>();

    private int qNo = 0;

    int score = 0;

    @FXML
    private JFXButton startButton;
    @FXML
    private JFXButton option1;
    @FXML
    private JFXButton option3;
    @FXML
    private JFXButton option4;
    @FXML
    private JFXButton option2;
    @FXML
    private TextArea questionDisplay;
    @FXML
    private Label scoreDisplay;
    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton previousScoreButton;

    @FXML
    private Label label1;

    @FXML
    private Button home;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private HBox scorebox;

    private ArrayList<Question> getQuestions(int catID) throws SQLException {

        SQLHandler sql = new SQLHandler();
        ArrayList<Question> allq = sql.getQnAFromCategory(catID);

        if (allq.size() < 10) {
            System.out.println("Less than 10 questions found");
            return null;
        }

        for (int i = 0; i < 10; i++) {
            int x = getRandom(allq.size());
            if (questions.contains(allq.get(x))) {
                i = i - 1;
            } else {
                questions.add(allq.get(x));
            }
        }

        return questions;
    }

    private int getRandom(int max) {
        int rnd = (int) (Math.random() * max);
        return rnd;
    }

    private int fetchCatInfo(String tempcat) throws SQLException {
        SQLHandler sql = new SQLHandler();
        List categoryInfo = sql.searchCategoriesTable(tempcat);

        int tempcategoryId = (int) categoryInfo.get(0);

        return tempcategoryId;

    }

    @FXML
    private void answer(ActionEvent event) {
        if (event.getSource().equals(option1)) {
            if (option1.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
            }
            nextQuestion();
        } else if (event.getSource().equals(option2)) {
            if (option2.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
            }
            nextQuestion();
        } else if (event.getSource().equals(option3)) {
            if (option3.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
            }
            nextQuestion();
        } else if (event.getSource().equals(option4)) {
            if (option4.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
            }
            nextQuestion();
        }
    }

    private void nextQuestion() {
        Question q = questions.get(qNo);
        questionDisplay.setText(q.getUserQuestion());
        String[] answers = {q.getCorrectAnswer(), q.getWrongAnswer1(), q.getWrongAnswer2(), q.getWrongAnswer3()};
        ArrayList num = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int x = getRandom(4);
            if (num.contains(x)) {
                i = i - 1;
            } else {
                num.add(x);
            }
        }
        option1.setText(answers[(int) num.get(0)]);
        option2.setText(answers[(int) num.get(1)]);
        option3.setText(answers[(int) num.get(2)]);
        option4.setText(answers[(int) num.get(3)]);
        if (qNo < 9) {
            qNo++;
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {

        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        questionDisplay.setVisible(false);
        
        
       
        
        scoreDisplay.setVisible(true);
        scorebox.setVisible(true);
        label3.setVisible(true);
        label2.setVisible(true);
        label1.setVisible(true);
        previousScoreButton.setVisible(true);
        home.setVisible(true);
        scoreDisplay.setText("" + score);

       

     
        startButton.setVisible(false);
    }

    @FXML
    private void start(ActionEvent event) throws SQLException {

        int catID = fetchCatInfo(categorySelected);

        ArrayList<Question> questions = getQuestions(catID);
        if (questions.isEmpty()) {
            return;
        }

        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);
        questionDisplay.setVisible(true);

        System.out.println(questions);

        System.out.println(catID);

        nextQuestion();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        questionDisplay.setVisible(false);
        backButton.setVisible(false);
        scoreDisplay.setVisible(false);

        startButton.setVisible(true);

        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);

        scoreDisplay.setVisible(false);
        scorebox.setVisible(false);
        label3.setVisible(false);
        label2.setVisible(false);
        label1.setVisible(false);
        previousScoreButton.setVisible(false);
        home.setVisible(false);
    }

    @FXML
    public void quitQuiz(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/UserHomePage/UserHome.fxml"));

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

}
