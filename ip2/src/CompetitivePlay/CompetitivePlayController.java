/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXButton;
import ip2.Question;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class CompetitivePlayController implements Initializable {

    User currentUser;
    @FXML
    private JFXButton option1, option2, option3, option4, startButton, returnhome, previousScoreButton, finishButton, highscore;

    @FXML
    private TextArea questionDisplay;

    private int qNo = 0;
    ArrayList<Question> questions = new ArrayList<>();
    int score;

    @FXML
    Timer countdown = new Timer();

    @FXML
    private Label label1, label2, label3, scoreDisplay;

    @FXML
    private Label label1;

    @FXML
    private Button home;

    @FXML
    private HBox scorebox;


    @FXML
    private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10;

    int questionno = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        class CountdownTimer extends TimerTask {

            public void run() {
                try {
                    endQuiz();
                } catch (SQLException ex) {
                    Logger.getLogger(CompetitivePlayController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        countdown.schedule(new CountdownTimer(), 30000, 60000);

        startButton.setVisible(true);
        finishButton.setVisible(false);
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);

        highscore.setVisible(false);
        scoreDisplay.setVisible(false);
        scorebox.setVisible(false);
        label3.setVisible(false);
        label2.setVisible(false);
        label1.setVisible(false);
        previousScoreButton.setVisible(false);
        returnhome.setVisible(false);
    }

    @FXML
    public void setData(User user) {
        currentUser = user;
    }

    @FXML
    private void start(ActionEvent event) throws SQLException {

        ArrayList<Question> questions = getQuestions();
        if (questions.isEmpty()) {
            return;
        }
        startButton.setVisible(false);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);
        nextQuestion();

    }

    @FXML
    private ArrayList<Question> getQuestions() throws SQLException {

        SQLHandler sql = new SQLHandler();
        ArrayList<Question> allq = sql.getAllQandA();

        if (allq.size() < 10) {
            System.out.println("Less than 10 questions found");
            return null;
        }

        for (int i = 0; i < 11; i++) {
            int x = getRandom(allq.size());
            if (questions.contains(allq.get(x))) {
                i = i - 1;
            } else {
                questions.add(allq.get(x));
            }
        }

        return questions;
    }

    @FXML
    private int getRandom(int max) {
        int rnd = (int) (Math.random() * max);
        return rnd;
    }

    @FXML
    private void changeCircleGreen() {
        if (questionno == 0) {
            circle1.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 1) {
            circle2.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 2) {
            circle3.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 3) {
            circle4.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 4) {
            circle5.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 5) {
            circle6.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 6) {
            circle7.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 7) {
            circle8.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 8) {
            circle9.setFill(javafx.scene.paint.Color.GREEN);
        } else if (questionno == 9) {
            circle10.setFill(javafx.scene.paint.Color.GREEN);
        }

    }

    @FXML
    private void changeCircleRed() {
        if (questionno == 0) {
            circle1.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 1) {
            circle2.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 2) {
            circle3.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 3) {
            circle4.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 4) {
            circle5.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 5) {
            circle6.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 6) {
            circle7.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 7) {
            circle8.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 8) {
            circle9.setFill(javafx.scene.paint.Color.RED);
        } else if (questionno == 9) {
            circle10.setFill(javafx.scene.paint.Color.RED);
        }

    }

    @FXML
    private void answer(ActionEvent event) {

        if (event.getSource().equals(option1)) {
            if (option1.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                changeCircleGreen();
                score++;
            } else {
                changeCircleRed();
            }
            nextQuestion();

        } else if (event.getSource().equals(option2)) {
            if (option2.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                changeCircleGreen();
            } else {
                changeCircleRed();
            }
            nextQuestion();
        } else if (event.getSource().equals(option3)) {
            if (option3.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                changeCircleGreen();
            } else {
                changeCircleRed();
            }
            nextQuestion();
        } else if (event.getSource().equals(option4)) {
            if (option4.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
                score++;
                changeCircleGreen();
            } else {
                changeCircleRed();
            }

            nextQuestion();
        }
        questionno++;
    }

    @FXML
    private void nextQuestion() throws SQLException {
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
        if (qNo < 10) {
            qNo++;
        } else {
            endQuiz();
        }
    }

    @FXML
    private void endQuiz() {
        System.out.println(score);
      
        countdown.cancel();

        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);

        finishButton.setVisible(true);
        finishButton.requestFocus();

        questionDisplay.clear();

        SQLHandler sql = new SQLHandler();

        //Create comp table if it doesnt exist
        sql.createCompTables(Integer.valueOf(currentUser.getCompetitiveBankID()));

        int quizNo = sql.getCompQuizNo(Integer.valueOf(currentUser.getCompetitiveBankID()));

        sql.addCompScore(Integer.valueOf(currentUser.getCompetitiveBankID()), ++quizNo, score);
        sql.updateTotalCompScore(Integer.valueOf(currentUser.getCompetitiveBankID()), score);

    }

    @FXML
    private void finish(ActionEvent event) {
        questionDisplay.setVisible(false);
        finishButton.setVisible(false);
        home.setVisible(false);

        highscore.setVisible(true);
        scoreDisplay.setVisible(true);
        scorebox.setVisible(true);
        label3.setVisible(true);
        label2.setVisible(true);
        label1.setVisible(true);
        previousScoreButton.setVisible(true);
        returnhome.setVisible(true);
        scoreDisplay.setText("" + score);
    

    }

    @FXML
    private void returnHome(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) returnhome.getScene().getWindow(), new UserHome(currentUser));
    }

}
