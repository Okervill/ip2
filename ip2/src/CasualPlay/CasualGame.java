package CasualPlay;

import UserHomePage.*;
import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CasualGame extends Application {

    User currentUser;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CasualGame/CasualGame.fxml"));
        Parent root = (Parent) loader.load();

        UserHomeController controller = loader.getController();

        controller.setData(currentUser);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }
    
    public CasualGame() {
    }
    
    public CasualGame(User user){
        currentUser = user;
    }
}
