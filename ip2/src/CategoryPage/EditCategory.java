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
public class EditCategory extends Application {
    Category currentCategory;

    @Override
    public void start(Stage stage) throws Exception {
 stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CategoryPage/EditCategory.fxml"));
        Parent root = (Parent) loader.load();
        
        EditCategoryController controller = loader.getController();
        
        controller.setData(currentCategory);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    
    
  
    
    public EditCategory(Category category){
        currentCategory = category;
    }
}
