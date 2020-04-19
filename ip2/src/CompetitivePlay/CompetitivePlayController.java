/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import HighScoreView.HighScoreView;
import ScoreHistory.ScoreHistory;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import ip2.Drawer;
import ip2.Question;
import ip2.Quiz;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class CompetitivePlayController implements Initializable {

    User currentUser;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton option1, option2, option3, option4, startButton, returnhome,  finishButton;

    @FXML
    private TextArea questionDisplay;

    private int qNo = 0;

    int score;
    Quiz q = new Quiz();

    @FXML
    Timer countdown = new Timer();

    @FXML
    private Label label1, label2, label3, scoreDisplay;

    @FXML
    private Button home,previousScoreButton, highscore;

    @FXML
    private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10;

    int questionno = 0;

    String username;
    Quiz quiz = new Quiz();
    ArrayList<Question> questions = new ArrayList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username = currentUser.getUsername();
                Drawer newdrawer = new Drawer();
                newdrawer.drawerPullout(drawer, currentUser, hamburger);
            }

        });
        startButton.setVisible(true);
        finishButton.setVisible(false);
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        highscore.setVisible(false);
        scoreDisplay.setVisible(false);

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
        questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            return;
        }

        drawer.setDisable(true);
        hamburger.setVisible(false);
        drawer.close();
        startButton.setVisible(false);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);
        nextQuestion();

    }

    @FXML
    private void changeCircleGreen() {
        switch (questionno) {
            case 0:
                circle1.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 1:
                circle2.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 2:
                circle3.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 3:
                circle4.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 4:
                circle5.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 5:
                circle6.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 6:
                circle7.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 7:
                circle8.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 8:
                circle9.setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 9:
                circle10.setFill(javafx.scene.paint.Color.GREEN);
                break;
            default:
                break;
        }

    }

    @FXML
    private void changeCircleRed() {
        switch (questionno) {
            case 0:
                circle1.setFill(javafx.scene.paint.Color.RED);
                break;
            case 1:
                circle2.setFill(javafx.scene.paint.Color.RED);
                break;
            case 2:
                circle3.setFill(javafx.scene.paint.Color.RED);
                break;
            case 3:
                circle4.setFill(javafx.scene.paint.Color.RED);
                break;
            case 4:
                circle5.setFill(javafx.scene.paint.Color.RED);
                break;
            case 5:
                circle6.setFill(javafx.scene.paint.Color.RED);
                break;
            case 6:
                circle7.setFill(javafx.scene.paint.Color.RED);
                break;
            case 7:
                circle8.setFill(javafx.scene.paint.Color.RED);
                break;
            case 8:
                circle9.setFill(javafx.scene.paint.Color.RED);
                break;
            case 9:
                circle10.setFill(javafx.scene.paint.Color.RED);
                break;
            default:
                break;
        }

    }

    @FXML
    private void answer(ActionEvent event) throws SQLException {

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
        if (qNo < 10) {
            Question q = questions.get(qNo);
            questionDisplay.setText(q.getUserQuestion());
            String[] answers = q.getAnswers(q);
            ArrayList num = quiz.nextQuestion();
            option1.setText(answers[(int) num.get(0)]);
            option2.setText(answers[(int) num.get(1)]);
            option3.setText(answers[(int) num.get(2)]);
            option4.setText(answers[(int) num.get(3)]);
            qNo++;
        } else {
            endQuiz();
        }
    }

    @FXML
    private void endQuiz() throws SQLException {
        countdown.cancel();
        home.setVisible(false);
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        finishButton.setVisible(true);
        finishButton.requestFocus();
        questionDisplay.clear();
        quiz.endCompQuiz(currentUser, score);
    }

    @FXML
    private void finish(ActionEvent event) {

        questionDisplay.setVisible(false);
        finishButton.setVisible(false);

        drawer.setDisable(false);
        drawer.close();
        hamburger.setVisible(true);
        highscore.setVisible(true);
        scoreDisplay.setVisible(true);

        label3.setVisible(true);
        label2.setVisible(true);
        label1.setVisible(true);
        previousScoreButton.setVisible(true);
        returnhome.setVisible(true);
        scoreDisplay.setText("" + score);

    }

    @FXML
    void viewHighScore(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) highscore.getScene().getWindow(), new HighScoreView(currentUser));
    }

    @FXML
    void viewPrevious(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) previousScoreButton.getScene().getWindow(), new ScoreHistory(currentUser));
    }

    @FXML
    private void returnHome(ActionEvent event) throws IOException, SQLException {
        SwitchWindow.switchWindow((Stage) returnhome.getScene().getWindow(), new UserHome(currentUser));
    }

    @FXML
    private void home(ActionEvent event) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you wish to return home? Your score will not be counted.", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            countdown.cancel();
            SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));
        }

    }

}
