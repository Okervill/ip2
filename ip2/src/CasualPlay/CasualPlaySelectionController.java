/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import CategoryPage.DeleteCategoryController;
import SQL.SQLHandler;
import ip2.Category;
import ip2.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class CasualPlaySelectionController implements Initializable {

    User currentUser;
    
    @FXML
    private TableView<Category> table;
    @FXML
    private TableColumn<Category, String> col_name;
    
    ObservableList<Category> data = FXCollections.observableArrayList();

    @FXML
    public void playGame(ActionEvent event) throws IOException {
        System.out.println("This would start the game.");

    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/UserHomePage/UserHome.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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
      
        col_name.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        table.setItems(data);
        
              
              
    }

    public void setData(User user) {
        currentUser = user;

    }

}
