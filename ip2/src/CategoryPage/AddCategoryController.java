/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CategoryPage;

import SQL.SQLHandler;
import ip2.Category;
import ip2.Shaker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    private TextField getCategoryName;
    @FXML
    private Button addNewCategoryButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

        });
    }

    @FXML
    private void addNewCategory(ActionEvent event) throws SQLException, IOException {
        String categoryName;
        categoryName = getCategoryName.getText();

        ArrayList<String> allCategories = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
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
        } else {

            Category newCategory = new Category(categoryName);
            newCategory.createCategory(newCategory);
            Parent root;
            root = FXMLLoader.load(getClass().getResource("CategoryPage.fxml"));

            Scene scene = new Scene(root);
            Stage reg = new Stage(StageStyle.DECORATED);
            reg.setTitle("Home");
            reg.setScene(scene);

            reg.show();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/CategoryPage/CategoryPage.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    private void addCategoryFailed() {
        Shaker shake = new Shaker(addNewCategoryButton);
        shake.shake();
        getCategoryName.requestFocus();
    }

}
