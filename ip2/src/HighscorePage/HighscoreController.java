/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HighscorePage;

import CategoryPage.DeleteCategoryController;
import SQL.SQLHandler;
import ip2.Category;
import ip2.HighScore;
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
import javafx.scene.control.Label;
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
public class HighscoreController implements Initializable {

    User currentUser;
    @FXML
    private Label userLabel;
    @FXML
    private TableView<HighScore> highScoreTable;
   @FXML
    private TableColumn name;
   @FXML
    private TableColumn<HighScore,String > name1;
    ObservableList<HighScore> data = FXCollections.observableArrayList();
   
    

    @FXML
    public void logutButton(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/LoginRegister/Login.fxml"));

        Scene scene = new Scene(root);
        Stage reg = new Stage(StageStyle.DECORATED);
        reg.setTitle("Home");
        reg.setScene(scene);

        reg.show();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            Connection conn = SQLHandler.getConn();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM CompetitiveBank "); //where competitiveBankId = \""+currentUser.getCompetitiveBankID()+"\"");
            while (rs.next()) {
            data.add(new HighScore(rs.getString("CompetitiveBankId"), rs.getString("Quiz 1")));
         System.out.println(data);
        }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        System.out.println();
        
        
         name.setCellValueFactory(new PropertyValueFactory<>("competitiveBankId"));
         name1.setCellValueFactory(new PropertyValueFactory<>("quiz"));
        highScoreTable.setItems(data);
        
              
    }

    public void setData(User user) {
        currentUser = user;
    }

}
