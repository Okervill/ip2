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
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Patrick
 */
public class UserHome extends Application {

    User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserHomePage/UserHome.fxml"));
        Parent root = (Parent) loader.load();

        UserHomeController controller = loader.getController();

        controller.setData(currentUser);
        controller.setStage(stage);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    public UserHome() {
    }

    public UserHome(User user) {
        currentUser = user;
    }
}
