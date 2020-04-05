/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CategoryPage;

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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author erino
 */
public class EditCategoryController implements Initializable {

    User currentUser;
    int catid;
    Category currentCategory;
    @FXML
    private JFXTextField getCategoryName;

    @FXML
    private JFXButton addNewCategoryButton;

    @FXML
    private JFXButton cancel;

    @FXML
    public void editnewCategory(ActionEvent event) throws IOException {

    }

    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) cancel.getScene().getWindow(), new ViewCategory(currentUser));
    }

    @FXML
    public void editNewCategory(ActionEvent event) throws IOException, SQLException {
        String name = getCategoryName.getText();
        ArrayList<String> allCategories = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allCategories = sql.getAllCategories();

        if (name.isEmpty()) {

            addCategoryFailed();

        } else {

            Category newcategory = new Category(catid, name);
            newcategory.editCategory(newcategory);

               SwitchWindow.switchWindow((Stage) addNewCategoryButton.getScene().getWindow(), new ViewCategory(currentUser));

        }

    }

    private void addCategoryFailed() {
        Shaker shake = new Shaker(addNewCategoryButton);
        shake.shake();
        getCategoryName.requestFocus();
    }

    public void setData(Category category) {
        currentCategory = category;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {

            getCategoryName.setText(currentCategory.getCategoryName());
            catid = currentCategory.getCategoryId();
        });
    }

}
