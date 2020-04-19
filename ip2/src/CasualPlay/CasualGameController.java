/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import ip2.Category;
import ip2.Drawer;
import ip2.Question;
import ip2.Quiz;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    int currentQSize = 0;
    Quiz quiz = new Quiz();

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton option1, option2, option3, option4, finish;

    @FXML
    private TextArea questionDisplay;
    @FXML
    private Label scoreDisplay, label1, label2, label3, outOf, correct, qleft, labelquestions;

    @FXML
    private Button home;

    @FXML
    private HBox scorebox;
    @FXML
    private Button returnHome;

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
        if (qNo < questions.size()) {
            Question q = questions.get(qNo);
            questionDisplay.setText(q.getUserQuestion());
            String[] answers = q.getAnswers(q);
            ArrayList num = quiz.nextQuestion();

            option1.setText(answers[(int) num.get(0)]);
            option2.setText(answers[(int) num.get(1)]);
            option3.setText(answers[(int) num.get(2)]);
            option4.setText(answers[(int) num.get(3)]);
            qNo++;
            currentQSize = currentQSize - 1;
            qleft.setText("" + currentQSize);

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

        labelquestions.setVisible(false);
        qleft.setVisible(false);
        returnHome.setVisible(false);
        finish.setVisible(false);
        drawer.setDisable(false);
        hamburger.setVisible(true);
        scoreDisplay.setVisible(true);
        scorebox.setVisible(true);
        label3.setVisible(true);
        label2.setVisible(true);
        label1.setVisible(true);

        home.setVisible(true);
        scoreDisplay.setText("" + score);
        outOf.setText("" + questions.size());
        boolean end = quiz.endCasualQuiz(categorySelected, currentUser, answQuestions);
        if (end == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Finished Category");
            alert.setHeaderText("Congratulations! You have finished this category. It will now be reset for you to practice again.");
            alert.showAndWait();
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Drawer newdrawer = new Drawer();
                    newdrawer.drawerPullout(drawer, currentUser, hamburger);
                    scoreDisplay.setVisible(false);
                    scorebox.setVisible(false);
                    label3.setVisible(false);
                    label2.setVisible(false);
                    label1.setVisible(false);

                    home.setVisible(false);

                    //int catID = Category.fetchCatInfo(categorySelected);
                    questions = quiz.getQuestions(categorySelected, currentUser);
                    if (questions.isEmpty()) {
                        return;
                    }
                    correct.setText("" + score);
                    labelquestions.setVisible(true);
                    returnHome.setVisible(true);
                    finish.setVisible(true);
                    drawer.setDisable(true);
                    hamburger.setVisible(false);
                    option1.setVisible(true);
                    option2.setVisible(true);
                    option3.setVisible(true);
                    option4.setVisible(true);
                    questionDisplay.setVisible(true);
                    correct.setVisible(true);
                    currentQSize = questions.size();
                    currentQSize = currentQSize + 1;
                    qleft.setText("" + currentQSize);
                    nextQuestion();
                } catch (SQLException ex) {
                    Logger.getLogger(CasualGameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );
    }

    public void setData(User user) {
        currentUser = user;
    }

    @FXML
    public void finish(ActionEvent event) throws IOException, SQLException {
        endQuiz();

    }

    @FXML
    public void home(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));

    }

    @FXML
    private void returnHome(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to return home? Your score will not be counted.", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));
        }

    }

}
