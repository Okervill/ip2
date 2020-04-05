/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author patrick
 */
public class Ip2 extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException, IOException {
        primaryStage.getIcons().add(new Image("/Resources/quiz.png"));
        Parent root = FXMLLoader.load(getClass().getResource("/LoginRegister/Login.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
