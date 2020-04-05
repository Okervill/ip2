/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CategoryPage;

import QuestionPage.*;
import ip2.Category;
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
public class AddCategory extends Application {
    Category currentCategory;

    @Override
    public void start(Stage stage) throws Exception {
 stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CategoryPage/AddCategory.fxml"));
        Parent root = (Parent) loader.load();
        
        AddCategoryController controller = loader.getController();
        
    
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    
    
  
    
 
}
