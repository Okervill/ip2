package CasualPlay;

import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CasualGame extends Application {

    User currentUser;

    private static String userSelection;

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CasualPlay/CasualQuiz.fxml"));
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

   
    public CasualGame(User user){
        currentUser = user;
    }
}
