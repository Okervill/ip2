/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HighscorePage;

import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Patrick
 */
public class Highscore extends Application{
    
    User currentUser;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HighscorePage/UserHome.fxml"));
        Parent root = (Parent) loader.load();
        
        HighscoreController controller = loader.getController();
        
        controller.setData(currentUser);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    public Highscore() {
    }
    
    public Highscore(User user){
        currentUser = user;
    }
}
