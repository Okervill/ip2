/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * FXML Controller class
 *
 * @author patrick
 */
public class CompetitivePlayController implements Initializable {

    User currentUser;
    // @FXML
    // private Button startButton;
    @FXML
    private Button option1;
    @FXML
    private Button option3;
    @FXML
    private Button option4;
    @FXML
    private Button option2;
    @FXML
    private TextArea questionDisplay;

    private int qNo = 0;
    ArrayList<Question> questions = new ArrayList<>();
    int score = 0;
    // @FXML
    //private TextArea scoreDisplay;
    // @FXML
    //private Button backButton;
    //@FXML
    //private Button quitButton;

    Timer countdown = new Timer();

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

    }

    public void setData(User user) {
        currentUser = user;
    }

    @FXML
    private void start(ActionEvent event) throws SQLException {

        ArrayList<Question> questions = getQuestions();
        if (questions.isEmpty()) {
            return;
        }

        nextQuestion();

    }

    /*
    @FXML
    private void homeButton(ActionEvent event) throws IOException, SQLException {

        Parent root;
        root = FXMLLoader.load(getClass().getResource("/UserHomePage/UserHome.fxml"));

        Scene scene = new Scene(root);
        Stage add = new Stage(StageStyle.DECORATED);
        add.setTitle("Admin");
        add.setScene(scene);

        add.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
     */
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
        countdown.cancel();
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        questionDisplay.setVisible(false);

        // scoreDisplay.setVisible(true);
        //scoreDisplay.setText(String.valueOf(score));
        // backButton.setVisible(true);
    }

}

//30 seconds per quiz - not question
