/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.io.IOException;
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
public class Ip2 extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException, IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/LoginRegister/Login.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
        
        SQLHandler sql = new SQLHandler();
        System.out.print(sql.getAllUsers());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
