/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import UserHomePage.UserHomeController;
import UserHomePage.drawerController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

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
    private Button home;
    
    @FXML
    private Line line;

   

    @FXML
    private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10;

    int questionno = 0;

    String username;

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
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserHomePage/pullout.fxml"));
                    VBox box = loader.load();
                    drawer.setSidePane(box);
                    drawerController controller = loader.getController();

                    controller.setData(currentUser);

                    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
                    transition.setRate(-1);
                    hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        transition.setRate(transition.getRate() * -1);
                        transition.play();

                        if (drawer.isOpened()) {
                            drawer.close();
                            drawer.setDisable(true);
                        } else {
                            drawer.open();
                            drawer.setDisable(false);

                        }
                    }
                    );
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        line.setVisible(false);
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
        ArrayList<Question> questions = getQuestions();
        if (questions.isEmpty()) {
            return;
        }
        drawer.setDisable(true);
        drawer.close();
        hamburger.setVisible(false);
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
        Question q = questions.get(qNo);
        System.out.println(qNo);
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
    private void endQuiz() throws SQLException {
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
        sql.createCompTables(currentUser.getUserID());

        int quizNo = sql.getCompQuizNo(currentUser.getUserID());

        sql.addCompScore(currentUser.getUserID(), ++quizNo, score);
        sql.updateTotalCompScore(currentUser.getUserID(), score);
       

    }

    @FXML
    private void finish(ActionEvent event) {
        questionDisplay.setVisible(false);
        finishButton.setVisible(false);
        home.setVisible(false);

        highscore.setVisible(true);
        scoreDisplay.setVisible(true);
        line.setVisible(true);
        label3.setVisible(true);
        label2.setVisible(true);
        label1.setVisible(true);
        previousScoreButton.setVisible(true);
        returnhome.setVisible(true);
        scoreDisplay.setText("" + score);

    }

    @FXML
    private void returnHome(ActionEvent event) throws IOException, SQLException {
         User currentUser = new User(username);
        SwitchWindow.switchWindow((Stage) returnhome.getScene().getWindow(), new UserHome(this.currentUser));
    }

    @FXML
    private void home(ActionEvent event) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure u wish to return home? Your score will not be counted.", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            countdown.cancel();
            SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));
        }

    }

}
