/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXButton;
import ip2.Question;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
    ArrayList<Question> answQuestions = new ArrayList<>();
    private int qNo = 0;

    int score = 0;

 

    @FXML
    private JFXButton option1, option2, option3, option4;

    @FXML
    private TextArea questionDisplay;
    @FXML
    private Label scoreDisplay, label1, label2, label3, outOf, correct;

    @FXML
    private JFXButton previousScoreButton;

    @FXML
    private Button home;

    @FXML
    private HBox scorebox;
    int qsize;

    
    private ArrayList<Question> getQuestions(int catID, int userId) throws SQLException {

        SQLHandler sql = new SQLHandler();
        
        ArrayList<Question> allq = sql.getQnAFromCategory(catID, currentUser.getCasualBankID());

        if (allq.size() < 1) {
            System.out.println("No questions found");
            return null;
        }
        qsize = allq.size();

        for (int i = 0; i < allq.size(); i++) {
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
    private void answer(ActionEvent event) throws SQLException {
        if (event.getSource().equals(option1)) {
            if (option1.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                answQuestions.add(questions.get(qNo - 1));
                correct.setText("" + score);
            }
            nextQuestion();
        } else if (event.getSource().equals(option2)) {
            if (option2.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                answQuestions.add(questions.get(qNo - 1));
                correct.setText("" + score);
            }
            nextQuestion();
        } else if (event.getSource().equals(option3)) {
            if (option3.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                answQuestions.add(questions.get(qNo - 1));
               correct.setText("" + score);
            }
            nextQuestion();
        } else if (event.getSource().equals(option4)) {
            if (option4.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                answQuestions.add(questions.get(qNo - 1));
               correct.setText("" + score);
            }
            nextQuestion();
        }
    }

    private void nextQuestion() throws SQLException {
        if (qNo < qsize) {
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
            qNo++;

        } else {
            endQuiz();
        }
    }

    private void endQuiz() throws SQLException {

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
        outOf.setText("" + qsize);
        
        SQLHandler sql = new SQLHandler();
        
        sql.createCasualTables(currentUser.getCasualBankID());
        for (Question q:answQuestions){
            sql.addAnsweredQuestions(currentUser.getCasualBankID(), q.getQuestionId(), q.getCategoryId());
        
    }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Platform.runLater(new Runnable() {
        @Override
            public void run() {
        try {
            scoreDisplay.setVisible(false);
            scorebox.setVisible(false);
            label3.setVisible(false);
            label2.setVisible(false);
            label1.setVisible(false);
            previousScoreButton.setVisible(false);
            home.setVisible(false);

            int catID = fetchCatInfo(categorySelected);

            questions = getQuestions(catID, currentUser.getCasualBankID());
            if (questions.isEmpty()) {
                return;
            }

            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            option4.setVisible(true);
            questionDisplay.setVisible(true);
            correct.setVisible(true);
            

            nextQuestion();
        } catch (SQLException ex) {
            Logger.getLogger(CasualGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            });
    }

    public void setData(User user) {
        currentUser = user;
    }

    @FXML
    public void home(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));

    }

}
