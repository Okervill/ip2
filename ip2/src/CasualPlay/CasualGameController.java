/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import SQL.SQLHandler;
import ip2.Question;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CasualGameController implements Initializable {

    User currentUser;

    String categorySelected = CasualGame.getUserSelection();

    ArrayList<Question> questions = new ArrayList<>();

    private ArrayList<Question> getQuestions(String catID) throws SQLException {

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

    private String fetchCatInfo(String tempcat) throws SQLException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> categoryInfo = sql.searchCategoriesTable(tempcat);

        String tempcategoryId = categoryInfo.get(0);
        

        return tempcategoryId;

    }

    @FXML
    private void answer(ActionEvent event) {
//        if (event.getSource().equals(option1)) {
//            if (option1.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
//                score++;
//            }
//            nextQuestion();
//        } else if (event.getSource().equals(option2)) {
//            if (option2.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
//                score++;
//            }
//            nextQuestion();
//        } else if (event.getSource().equals(option3)) {
//            if (option3.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
//                score++;
//            }
//            nextQuestion();
//        } else if (event.getSource().equals(option4)) {
//            if (option4.getText().equals(questions.get(qNo - 1).getCorrectAnswer())) {
//                score++;
//            }
//            nextQuestion();
//        }
    }
    
    @FXML
    private void start(ActionEvent event) throws SQLException {
//        startButton.setVisible(false);
//        ArrayList<Question> questions = getQuestions();
//        if (questions.isEmpty()) {
//            return;
//        }
//
//        option1.setVisible(true);
//        option2.setVisible(true);
//        option3.setVisible(true);
//        option4.setVisible(true);
//        questionDisplay.setVisible(true);
//
//        nextQuestion();
    }
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String catID  = fetchCatInfo(categorySelected);
            
            ArrayList<Question> allq = getQuestions(catID);

            System.out.println(allq);
            
            
            
            
            System.out.println(catID);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CasualGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
