/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author erino
 */
public class AddQuestion extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionPage/AddQuestion.fxml"));
        Parent root = (Parent) loader.load();

       // AddQuestionController controller = loader.getController();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Add Question");

        stage.show();
        stage.centerOnScreen();

    }

}
