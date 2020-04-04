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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author erino
 */
public class EditCategoryController implements Initializable {

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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/CategoryPage/ViewCategoryTable.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    public void editNewCategory(ActionEvent event) throws IOException, SQLException {
        String name = getCategoryName.getText();
        ArrayList<String> allCategories = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allCategories = sql.getAllCategories();
        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher match = pattern.matcher(name);
        boolean val = match.find();

        if (allCategories.contains(name)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category Error");
            alert.setHeaderText("This category name already exists, please choose another");
            alert.showAndWait();
            return;
        }
        if (name.isEmpty()) {

            addCategoryFailed();
            }
        if (val==true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category Error");
            alert.setHeaderText("Invalid category name, please choose another");
            alert.showAndWait();
            return;
        }
         else {

            Category newcategory = new Category(catid, name);
            newcategory.editCategory(newcategory);

            Parent root;
            root = FXMLLoader.load(getClass().getResource("/CategoryPage/ViewCategoryTable.fxml"));

            Scene scene = new Scene(root);
            Stage reg = new Stage(StageStyle.DECORATED);
            reg.setTitle("Home");
            reg.setScene(scene);

            reg.show();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

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
