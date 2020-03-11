/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompetitivePlay;

import AdminHomePage.AdminHomeController;
import ip2.User;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author patrick
 */
public class CompetitivePlay extends Application {

    User currentUser;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CompetitivePlay/CompetitivePlay.fxml"));
        Parent root = (Parent) loader.load();

        CompetitivePlayController controller = loader.getController();

        controller.setData(currentUser);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Competitive");

        stage.show();
        stage.centerOnScreen();

    }

    public CompetitivePlay(User user) {
        currentUser = user;
    }

}