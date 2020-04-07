/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualPlay;

import CompetitivePlay.CompetitivePlay;
import SQL.SQLHandler;
import UserHomePage.UserHome;
import UserHomePage.UserHomeController;
import com.jfoenix.controls.JFXButton;
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
    private int fetchCatId(String catId) throws SQLException {

        SQLHandler sql = new SQLHandler();
        List categoryInfo = sql.searchCategoriesTable(catId);

        int tempcategoryId = (int) categoryInfo.get(0);

        return tempcategoryId;

    }

    @FXML
    public void playGame(ActionEvent event) throws IOException, SQLException {

        try {

            TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            Category item = table.getItems().get(index);

            System.out.println("You have selected " + item.getCategoryName());

            tempSelection = (String) item.getCategoryName();

            CasualGame.setUserSelection(tempSelection);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select Category");
            alert.setHeaderText("Please select a category to continue");
            alert.showAndWait();
            return;
        }
        int catID = fetchCatId(tempSelection);
        SQLHandler sql = new SQLHandler();
        ArrayList<Question> allq = sql.getQnAFromCategory(catID);

        if (allq.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No questions");
            alert.setHeaderText("Sorry, there has been no questions found in this category!");
            alert.showAndWait();
            return;

        } else {

            SwitchWindow.switchWindow((Stage) playGame.getScene().getWindow(), new CasualGame(currentUser));
        }

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
       
        try {
             drawer.setDisable(true);
            VBox box = FXMLLoader.load(getClass().getResource("/UserHomePage/pullout.fxml"));
            drawer.setSidePane(box);

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
        try {

            Connection conn = SQLHandler.getConn();
            ResultSet rs = conn.createStatement().executeQuery("Select * from Categories");
            while (rs.next()) {
                data.add(new Category(rs.getInt("CategoryID"), rs.getString("CategoryName")));

            }
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
