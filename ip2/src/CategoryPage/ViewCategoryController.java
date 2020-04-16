/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CategoryPage;

import AdminHomePage.AdminHome;
import SQL.SQLHandler;
import com.jfoenix.controls.JFXButton;
import ip2.Category;
import ip2.Shaker;
import ip2.SwitchWindow;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author erino
 */
public class ViewCategoryController implements Initializable {

    User currentUser;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, String> name;

    ObservableList<Category> data = FXCollections.observableArrayList();
    @FXML
    private Button deleteButton, home;

    @FXML
    private JFXButton editCat;

    @FXML
    private JFXButton addCat;

    @FXML
    private TextField search;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            SQLHandler sql = new SQLHandler();
           data=sql.showCategoriesTable();

        name.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        categoryTable.setItems(data);

        FilteredList<Category> filtCat = new FilteredList<>(data, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filtCat.setPredicate((Predicate<? super Category>) category -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (category.getCategoryName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Category> sortedData = new SortedList<>(filtCat);
            sortedData.comparatorProperty().bind(categoryTable.comparatorProperty());
            categoryTable.setItems(sortedData);
        });
    }   catch (SQLException ex) {
            Logger.getLogger(ViewCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private String getTablePos() {
        TablePosition pos = (TablePosition) categoryTable.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        Category item = categoryTable.getItems().get(index);
        String categoryName = (String) name.getCellObservableValue(item).getValue();

        return categoryName;
    }

    @FXML
    private void editCategoryButton(ActionEvent event) throws IOException, SQLException {
        try {
            
            String catName = getTablePos();
            Category currentCategory = new Category(catName);
            currentCategory = Category.search(catName);

            SwitchWindow.switchWindow((Stage) editCat.getScene().getWindow(), new EditCategory(currentCategory));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit Error");
            alert.setHeaderText("Please select a category to edit");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void deleteCategory(ActionEvent event) throws SQLException, IOException {
        try {
            String catname = getTablePos();
            ArrayList<String> allCategories = new ArrayList<>();
            SQLHandler sql = new SQLHandler();
            allCategories = sql.getAllCategories();

            Category currentCategory = Category.search(catname);
            currentCategory.deleteCategory(currentCategory);
            SwitchWindow.switchWindow((Stage) deleteButton.getScene().getWindow(), new ViewCategory(currentUser));
           
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Please select a category to delete");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void addCategoryButton(ActionEvent event) throws SQLException, IOException {
        SwitchWindow.switchWindow((Stage) addCat.getScene().getWindow(), new AddCategory());

    }

   

    @FXML
    public void homeButton(ActionEvent event) throws IOException {
     

                   SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new AdminHome(currentUser));

    }

    private void deleteCategoryFailed() {
        Shaker shake = new Shaker(deleteButton);
        shake.shake();
        categoryTable.requestFocus();
    }

    public void setData(User user) {
        currentUser = user;
    }
}
