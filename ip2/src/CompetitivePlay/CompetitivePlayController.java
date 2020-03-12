/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import ip2.Question;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class CompetitivePlayController implements Initializable {

    User currentUser;
    @FXML
    private JFXButton option1;
    @FXML
    private JFXButton option3;
    @FXML
    private JFXButton option4;
    @FXML
    private JFXButton option2;
    @FXML
    private JFXButton startButton;
    @FXML
    private TextArea questionDisplay;

    @FXML
    private JFXButton returnhome;

    private int qNo = 0;
    ArrayList<Question> questions = new ArrayList<>();
    int score;

    @FXML
    Timer countdown = new Timer();

    @FXML
    private JFXButton casual;

    @FXML
    private JFXButton previousScoreButton;
    @FXML
    private JFXButton finishButton;

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

    @FXML
    private Label scoreDisplay;

    @FXML
    private JFXButton highscore;

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
                endQuiz();

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

    @FXML
    private int getRandom(int max) {
        int rnd = (int) (Math.random() * max);
        return rnd;
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

    @FXML
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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/UserHomePage/UserHome.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}

//30 seconds per quiz - not question
