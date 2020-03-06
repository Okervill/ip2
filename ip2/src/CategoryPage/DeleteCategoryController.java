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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static sun.net.www.http.HttpClient.New;

/**
 * FXML Controller class
 *
 * @author stani
 */
public class DeleteCategoryController implements Initializable {

    @FXML 
    private Button deleteButton;
    @FXML
    private TextField getCategoryName;
    @FXML
    private TableView<Category> categoryTable;
    @FXML 
    private TableColumn<Category, String> ID;
    @FXML
    private TableColumn<Category, String> name;
    ObservableList<Category> data = FXCollections.observableArrayList();
 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
            
            Connection conn = SQLHandler.getConn();
            ResultSet rs = conn.createStatement().executeQuery("Select * from Categories");
            while (rs.next()) {
            data.add(new Category(rs.getString("CategoryID"), rs.getString("CategoryName")));
         
        }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ID.setCellValueFactory(new PropertyValueFactory<>("CategoryID"));
        name.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        categoryTable.setItems(data);
        
              
              
    }
    @FXML
    private void deleteCategory(ActionEvent event) throws SQLException, IOException {
        String categoryName;
        categoryName = getCategoryName.getText();
        

        ArrayList<String> allCategories = new ArrayList<>();
        SQLHandler sql = new SQLHandler();
        allCategories = sql.getAllCategories();

        if (categoryName.isEmpty()) {
            deleteCategoryFailed();
        } else {
         
            delete(categoryName);
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
     public void delete(String name) throws SQLException, IOException{
        SQLHandler sql = new SQLHandler();
        ArrayList<String> categoryInfo = sql.searchCategoriesTable(name);

        String categoryId = categoryInfo.get(0);
        String categoryName=categoryInfo.get(1);
        Category currentCategory = new Category(categoryId,categoryName);
        currentCategory.deleteCategory(currentCategory);
   
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
    private void deleteCategoryFailed() {
        Shaker shake = new Shaker(deleteButton);
        shake.shake();
        getCategoryName.requestFocus();
    }

}
