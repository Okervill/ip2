/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import ip2.Category;
import ip2.Question;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLHandler {

    Connection conn = SQLHandler.getConn();
    PreparedStatement query;

    public SQLHandler() {

    }

    //----------------------//
    // CONNECT TO SQLITE DB //
    //----------------------//
    public static Connection getConn() {

        String url = "jdbc:sqlite:src/SQL/ip2.db";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
            return null;
        }
        return conn;
    }

    //-----------------------------//
    // ADD NEW DATA TO LOGIN TABLE //
    //-----------------------------//
    public void createUser(String userid, String CompetitiveBankID, String CasualBankID, String CategoriesAnsweredID, String firstname, String surname, String username, String password, String admin, String userscore) throws SQLException {

        String sql = "INSERT INTO Users (UserID, CompetitiveBankID, CasualBankID, CategoriesAnsweredID, FirstName, Surname, Username, Password, isAdmin, UserScore) VALUES(?,?,?,?,?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

        query.setString(1, userid);
        query.setString(2, CompetitiveBankID);
        query.setString(3, CasualBankID);
        query.setString(4, CategoriesAnsweredID);
        query.setString(5, firstname);
        query.setString(6, surname);
        query.setString(7, username);
        query.setString(8, password);
        query.setString(9, admin);
        query.setString(10, userscore);

        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL USERNAMES FROM USERS TABLE //
    //------------------------------------//
    public ArrayList getAllUsers() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT username FROM Users";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("Username"));
        }

        query.close();
        return output;
    }

    //-----------------------------//
    // ADD NEW DATA TO QUESTION TABLE //
    //-----------------------------//
    public void createQuestion(String QuestionId, String CategoryId, String question, String answer, String wrongAns1, String wrongAns2, String wrongAns3) throws SQLException {

        String sql = "INSERT INTO Question (QuestionId, CategoryId, Question, Answer, wrongAns1, wrongAns2, wrongAns3) VALUES(?,?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

        query.setString(1, QuestionId);
        query.setString(2, CategoryId);
        query.setString(3, question);
        query.setString(4, answer);
        query.setString(5, wrongAns1);
        query.setString(6, wrongAns2);
        query.setString(7, wrongAns3);

        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL QUESTIONS FROM QUESTION TABLE //
    //------------------------------------//
    public ArrayList getAllQuestions() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT question FROM Questions";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("Question"));
        }

        query.close();
        return output;
    }

    public ObservableList showQuestionsTable() throws SQLException {

        ObservableList<Question> output = FXCollections.observableArrayList();
        output.clear();
     
        String sql = "SELECT * FROM Questions";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            String QuestionID = rs.getString("QuestionID");
            String CategoryID = rs.getString("CategoryID");
            String question = rs.getString("Question");
            String answer = rs.getString("Answer");
            String wrongans1 = rs.getString("wrongAns1");
            String wrongans2 = rs.getString("wrongAns2");
            String wrongans3 = rs.getString("wrongAns3");
          
            //output.add(new Question(QuestionID, CategoryID, question, answer, wrongans1,wrongans2, wrongans3));
         
        }

        query.close();
        return output;
    }

    public ObservableList showCategoriesTable() throws SQLException {

        ObservableList<Category> output = FXCollections.observableArrayList();
        output.clear();
     
        String sql = "SELECT * FROM Categories";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            String CategoryID = rs.getString("CategoryID");
            String CategoryName = rs.getString("CategoryName");
            Category currentCategory=new Category(CategoryID, CategoryName);
            output.add(currentCategory);
         
        }

        query.close();
        return output;
    }

    public ArrayList searchQuestionTable(String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT QuestionId, CategoryId, Question, Answer, wrongAns1, wrongAns2, wrongAns3 FROM Questions WHERE question = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getString("QuestionId")));
            output.add((rs.getString("CategoryId")));
            output.add((rs.getString("Answer")));
            output.add((rs.getString("wrongAns1")));
            output.add((rs.getString("wrongAns2")));
            output.add((rs.getString("wrongAns3")));

        }
        return output;
    }

    public ArrayList searchUsersTable(String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT UserID, CompetitiveBankID, CasualBankID, CategoriesAnsweredID, FirstName, Surname, Username, Password, isAdmin, UserScore FROM Users WHERE Username = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getString("UserID")));
            output.add((rs.getString("CompetitiveBankID")));
            output.add((rs.getString("CasualBankID")));
            output.add((rs.getString("CategoriesAnsweredID")));
            output.add((rs.getString("Firstname")));
            output.add((rs.getString("Surname")));
            output.add((rs.getString("Username")));
            output.add((rs.getString("Password")));
            output.add((rs.getString("isAdmin")));
            output.add((rs.getString("UserScore")));
        }
        return output;
    }

    public void createCategory(String categoryId, String categoryName) throws SQLException {
        String sql = "INSERT INTO Categories (CategoryID, CategoryName) VALUES(?,?)";
        query = conn.prepareStatement(sql);
        query.setString(1, categoryId);
        query.setString(2, categoryName);
        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL CATEGORY NAMES FROM CATEGORIES TABLE //
    //------------------------------------//
    public ArrayList getAllCategories() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT CategoryName FROM Categories";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("CategoryName"));
        }

        query.close();
        return output;
    }

    public ArrayList searchCategoriesTable(String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Categories WHERE CategoryName = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getString("CategoryID")));
            output.add((rs.getString("CategoryName")));

        }
        return output;
    }

    public void deleteCategory(String categoryId) throws SQLException {
        String sql1 = " DELETE FROM Categories WHERE CategoryID=?";
        query = conn.prepareStatement(sql1);
        query.setString(1, categoryId);
        query.executeUpdate();
        query.close();
        String sql2="DELETE FROM Questions WHERE CategoryID=?";
        query = conn.prepareStatement(sql2);
        query.setString(1, categoryId);
        query.executeUpdate();
        query.close();
        
    }

}
