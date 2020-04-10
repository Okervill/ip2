/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHomePage;

import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author stani
 */
public class ChangeUsername extends Application{

    User currentUser;
    

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserHomePage/ChangeUsername.fxml"));
        Parent root = (Parent) loader.load();
        
        ChangeUsernameController controller = loader.getController();
        
        controller.setData(currentUser);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Change Username");

        stage.show();
        stage.centerOnScreen();

    }

    public ChangeUsername(User user){
       currentUser = user;
    }

}
