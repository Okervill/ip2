/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CategoryPage;

import AdminHomePage.AdminHome;
import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import ip2.Category;
import ip2.Shaker;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author stani
 */
public class AddCategoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField getCategoryName;
    @FXML
    private JFXButton addNewCategoryButton, cancel;

    public static SQLHandler sql = new SQLHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

        });
    }

    @FXML
    private void addNewCategory(ActionEvent event) throws SQLException, IOException {
        String categoryName=getCategoryName.getText();
        ArrayList<String> allCategories = new ArrayList<>();
        allCategories = sql.getAllCategories();
 
        
        if (allCategories.contains(categoryName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category Error");
            alert.setHeaderText("This category already exists, please choose another");
            alert.showAndWait();
            return;
        }

        if (categoryName.isEmpty()) {
            addCategoryFailed();
        } 
        if (Category.match(categoryName)==true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category Error");
            alert.setHeaderText("Invalid category name, please choose another");
            alert.showAndWait();
            return;
        }
        else {
            Category.createCategory(categoryName);
            SwitchWindow.switchWindow((Stage) addNewCategoryButton.getScene().getWindow(), new ViewCategory(currentUser));
            
           

        }
    }
User currentUser;
    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) cancel.getScene().getWindow(), new ViewCategory(currentUser));

    }

    private void addCategoryFailed() {
        Shaker shake = new Shaker(addNewCategoryButton);
        shake.shake();
        getCategoryName.requestFocus();
    }
    
       public void AddCategory(User user) {
        currentUser = user;
    }

}
