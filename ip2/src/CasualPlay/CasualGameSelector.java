package CasualPlay;

import SQL.SQLHandler;
import ip2.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CasualGameSelector extends Application {

    User currentUser;

    
    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/Resources/quiz.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CasualPlay/CasualPlaySelect.fxml"));
        Parent root = (Parent) loader.load();
        
        CasualPlaySelectionController controller = loader.getController();

        controller.setData(currentUser);
        SQLHandler sql=new SQLHandler();
        sql.createCasualTables(currentUser.getUserID());
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        stage.show();
        stage.centerOnScreen();

    }

    public CasualGameSelector(User user){
        currentUser = user;
    }


}
