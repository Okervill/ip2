/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginRegister;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Patrick
 */
public class RegisterUser extends Application{
    
        public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();        
        stage.centerOnScreen();
    }
    
    public RegisterUser(){
        
    }
}
