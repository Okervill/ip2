package CasualPlay;

import SQL.SQLHandler;
import ip2.Question;
import ip2.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CasualGame extends Application {

    User currentUser;

    private static String userSelection;

   

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CasualGame/CasualGame.fxml"));
        Parent root = (Parent) loader.load();

        CasualGameController controller = loader.getController();

        controller.setData(currentUser);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    public static String getUserSelection() {
        return userSelection;
    }

    public static void setUserSelection(String userSelection) {
        CasualGame.userSelection = userSelection;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
