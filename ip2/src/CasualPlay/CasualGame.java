package CasualPlay;

import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CasualGame extends Application {

    User currentUser;

    private String userSelection;

    public CasualGame() {
    }

    public String getUserSelection() {
        return userSelection;
    }

    public void setUserSelection(String userSelection) {
        this.userSelection = userSelection;
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CasualGame/CasualGame.fxml"));
        Parent root = (Parent) loader.load();

        CasualGameController controller = loader.getController();

        controller.setData(currentUser, userSelection);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public CasualGame(User user) {
        currentUser = user;
    }

    public CasualGame(User currentUser, String userSelection) {
        this.currentUser = currentUser;
        this.userSelection = userSelection;
    }

    public CasualGame(String userSelect) {
        userSelect = userSelection;
    }

}
