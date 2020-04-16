/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import SQL.SQLHandler;
import UserHomePage.UserHome;
import UserHomePage.UserHomeController;
import UserHomePage.drawerController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import ip2.Category;
import ip2.Question;
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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patrick
 */
public class CasualPlaySelectionController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    User currentUser;
    @FXML
    private Button home;
    @FXML
    private Button playGame;
    String tempSelection;

    @FXML
    private TableView<Category> table;
    @FXML
    private TableColumn<Category, String> col_name;

    ObservableList<Category> data = FXCollections.observableArrayList();

    @FXML
    private TextField search;

   

    @FXML
    public void playGame(ActionEvent event) throws IOException, SQLException {

        try {

            TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            Category item = table.getItems().get(index);

   
            tempSelection = (String) item.getCategoryName();

            CasualGame.setUserSelection(tempSelection);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select Category");
            alert.setHeaderText("Please select a category to continue");
            alert.showAndWait();
            return;
        }
       int catID = Category.fetchCatInfo(tempSelection);
        SQLHandler sql = new SQLHandler();
        ArrayList<Question> allq = sql.getQnAFromCategory(catID, currentUser.getUserID());

        if (allq.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Questions");
            alert.setHeaderText("Sorry this category doesn't have any questions yet!");
            alert.showAndWait();
            return;
           

        }

        SwitchWindow.switchWindow((Stage) playGame.getScene().getWindow(), new CasualGame(currentUser));

    }

    @FXML
    public void homeButton(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) home.getScene().getWindow(), new UserHome(currentUser));

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserHomePage/pullout.fxml"));
                    VBox box = loader.load();
                    drawer.setSidePane(box);
                    drawerController controller = loader.getController();

                    controller.setData(currentUser);

                    HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
                    transition.setRate(-1);
                    hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        transition.setRate(transition.getRate() * -1);
                        transition.play();

                        if (drawer.isOpened()) {
                            drawer.close();
                            drawer.setDisable(true);
                        } else {
                            drawer.open();
                            drawer.setDisable(false);

                        }
                    }
                    );
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        try {

            SQLHandler sql = new SQLHandler();
            data=sql.showCategoriesTable();

            } catch (SQLException ex) {
            Logger.getLogger(CasualPlaySelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        col_name.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        table.setItems(data);

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
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        }
        

    public void setData(User user) {
        currentUser = user;

    }

}
