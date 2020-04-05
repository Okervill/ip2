/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionPage;

import ip2.Question;
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
public class EditPage extends Application {
    Question currentQuestion;

    @Override
    public void start(Stage stage) throws Exception {
stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionPage/EditQuestion.fxml"));
        Parent root = (Parent) loader.load();
        
        EditController controller = loader.getController();
        
        controller.setData(currentQuestion);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Edit Question");

        stage.show();
        stage.centerOnScreen();

    }

    
    
  
    
    public EditPage(Question question){
        currentQuestion = question;
    }
}
