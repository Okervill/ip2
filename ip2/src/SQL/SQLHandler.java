/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import ip2.Category;
import ip2.HighScore;
import ip2.LeaderBoardScore;
import ip2.Question;
import ip2.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
    // ADD NEW DATA TO USERS TABLE //
    //-----------------------------//
    public void createUser(String firstname, String surname, String username, String password, String admin, String userscore) throws SQLException {

        String sql = "INSERT INTO Users ( FirstName, Surname, Username, Password, isAdmin, UserScore) VALUES(?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

;
        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, username);
        query.setString(4, password);
        query.setString(5, admin);
        query.setString(6, userscore);

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
    public void createQuestion( int CategoryId, String question, String answer, String wrongAns1, String wrongAns2, String wrongAns3) throws SQLException {

        String sql = "INSERT INTO Questions (CategoryId, Question, Answer, wrongAns1, wrongAns2, wrongAns3) VALUES(?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

        query.setInt(1, CategoryId);
        query.setString(2, question);
        query.setString(3, answer);
        query.setString(4, wrongAns1);
        query.setString(5, wrongAns2);
        query.setString(6, wrongAns3);

        query.executeUpdate();
        query.close();
    }

    //-----------------------------//
    // EDIT DATA IN QUESTION TABLE //
    //-----------------------------//
    public void editQuestion(int QuestionId, int CategoryId, String question, String answer, String wrongAns1, String wrongAns2, String wrongAns3) throws SQLException {

        String sql = "UPDATE Questions SET CategoryId = ? , Question = ?, Answer = ?, wrongAns1 = ?, wrongAns2 = ?, wrongAns3 = ? WHERE QuestionId = \"" + QuestionId + "\"";

        query = conn.prepareStatement(sql);

        query.setInt(1, CategoryId);
        query.setString(2, question);
        query.setString(3, answer);
        query.setString(4, wrongAns1);
        query.setString(5, wrongAns2);
        query.setString(6, wrongAns3);

        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL QUESTIONS FROM QUESTION TABLE //
    //------------------------------------//
    public ArrayList<Question> getAllQandA() throws SQLException {
        ArrayList<Question> output = new ArrayList<>();
        String sql = "SELECT * FROM Questions";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            int QuestionID = rs.getInt("QuestionID");
            int CategoryID = rs.getInt("CategoryID");
            String question = rs.getString("Question");
            String answer = rs.getString("Answer");
            String wrongans1 = rs.getString("wrongAns1");
            String wrongans2 = rs.getString("wrongAns2");
            String wrongans3 = rs.getString("wrongAns3");

            output.add(new Question(QuestionID, CategoryID, question, answer, wrongans1, wrongans2, wrongans3));

        }

        query.close();
        return output;
    }

    //------------------------------------//
    // GET ALL QUESTIONS FROM SPECIFIC CATEGORY //
    //------------------------------------//
    public ArrayList<Question> getQnAFromCategory(int categoryID, int id) throws SQLException {
        ArrayList<Question> output = new ArrayList<>();
        String sql = "SELECT * FROM Questions WHERE CategoryID='" + categoryID + "' AND QuestionID NOT IN (SELECT QuestionID from casual_" + id + ")";

//        = \"" + searchQuery + "\""
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            int QuestionID = rs.getInt("QuestionID");
            int CategoryID = rs.getInt("CategoryID");
            String question = rs.getString("Question");
            String answer = rs.getString("Answer");
            String wrongans1 = rs.getString("wrongAns1");
            String wrongans2 = rs.getString("wrongAns2");
            String wrongans3 = rs.getString("wrongAns3");

            output.add(new Question(QuestionID, CategoryID, question, answer, wrongans1, wrongans2, wrongans3));

        }

        query.close();
        return output;
    }
    //------------------------------------//
    // GET ALL QUESTIONS FROM QUESTION TABLE //
    //------------------------------------//
    public ObservableList showQuestionsTable() throws SQLException {

        ObservableList<Question> output = FXCollections.observableArrayList();
        output.clear();

        String sql = "SELECT * FROM Questions";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            int QuestionID = rs.getInt("QuestionID");
            int CategoryID = rs.getInt("CategoryID");
            String question = rs.getString("Question");
            String answer = rs.getString("Answer");
            String wrongans1 = rs.getString("wrongAns1");
            String wrongans2 = rs.getString("wrongAns2");
            String wrongans3 = rs.getString("wrongAns3");

            output.add(new Question(QuestionID, CategoryID, question, answer, wrongans1, wrongans2, wrongans3));

        }

        query.close();
        return output;
    }
    //------------------------------------//
    // GET ALL CATEGORIES FROM CATEGORIES TABLE //
    //------------------------------------//
    public ObservableList showCategoriesTable() throws SQLException {

        ObservableList<Category> output = FXCollections.observableArrayList();
        output.clear();

        String sql = "SELECT * FROM Categories";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            int CategoryID = rs.getInt("CategoryID");
            String CategoryName = rs.getString("CategoryName");
            Category currentCategory = new Category(CategoryID, CategoryName);
            output.add(currentCategory);

        }

        query.close();
        return output;
    }
    
    //------------------------------------//
    // GET A SPECIFIC QUESTION FROM QUESTION TABLE //
    //------------------------------------//
    public List searchQuestionTable(String searchQuery) throws SQLException {

        List output = new ArrayList<>();
        String sql = "SELECT * FROM Questions WHERE question = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getInt("QuestionId")));
            output.add((rs.getInt("CategoryId")));
            output.add((rs.getString("Question")));
            output.add((rs.getString("Answer")));
            output.add((rs.getString("wrongAns1")));
            output.add((rs.getString("wrongAns2")));
            output.add((rs.getString("wrongAns3")));

        }
        return output;
    }

    //------------------------------------//
    // GET A SPECIFIC USER FROM USERS TABLE //
    //------------------------------------//
    public ArrayList searchUsersTable(String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Username = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getString("UserID")));
            output.add((rs.getString("Firstname")));
            output.add((rs.getString("Surname")));
            output.add((rs.getString("Username")));
            output.add((rs.getString("Password")));
            output.add((rs.getString("isAdmin")));
            output.add((rs.getString("UserScore")));
        }
        return output;
    }

    public void createCategory( String categoryName) throws SQLException {
        String sql = "INSERT INTO Categories ( CategoryName) VALUES(?)";
        query = conn.prepareStatement(sql);
        query.setString(1, categoryName);
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

   
    //------------------------------------//
    // GET A CATEGORY WITH SPECIFIC NAME//
    //------------------------------------//
    public List searchCategoriesTable(String searchQuery) throws SQLException {

        List output = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM Categories WHERE CategoryName = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output.add((rs.getInt("CategoryID")));
            output.add((rs.getString("CategoryName")));

        }
        return output;
    }
    //------------------------------------//
    // GET A CATEGORY WITH A SPECIFIC ID//
    //------------------------------------//
    public ArrayList searchIDCategoriesTable(int searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT CategoryName FROM Categories WHERE CategoryID = \"" + searchQuery + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {

            output.add((rs.getString("CategoryName")));

        }
        return output;
    }

    //------------------------------------//
    // DELETE A CATEOGORY FROM CATEGORIES TABLE AND THE RELATED QUESTIONS //
    //------------------------------------//
    public void deleteCategory(int categoryId) throws SQLException {
        String sql1 = " DELETE FROM Categories WHERE CategoryID=?";
        query = conn.prepareStatement(sql1);
        query.setInt(1, categoryId);
        query.executeUpdate();
        query.close();
        String sql2 = "DELETE FROM Questions WHERE CategoryID=?";
        query = conn.prepareStatement(sql2);
        query.setInt(1, categoryId);
        query.executeUpdate();
        query.close();

    }

    public void editCategory(int CategoryId, String CategoryName) throws SQLException {

        String sql = "UPDATE Categories SET CategoryID = ? , CategoryName = ?  WHERE CategoryID = \"" + CategoryId + "\"";

        query = conn.prepareStatement(sql);

        query.setInt(1, CategoryId);
        query.setString(2, CategoryName);

        query.executeUpdate();
        query.close();
    }

    public void deleteQuestion(int id) throws SQLException {
        String sql = " DELETE FROM Questions WHERE questionID=?";
        query = conn.prepareStatement(sql);
        query.setInt(1, id);
        query.executeUpdate();
        query.close();
    }


    public void createCompTables(int id) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS comp_" + id + " (\n"
                + "    quizNo integer PRIMARY KEY,\n"
                + "    score integer NOT NULL\n"
                + ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

   
    public void createCasualTables(int id) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS casual_" + id + "(\n"
                + " QuestionID integer,\n"
                + " CategoryID integer,\n"
                + " PRIMARY KEY (QuestionID),\n"
                + " FOREIGN KEY (CategoryID) REFERENCES Questions(CategoryID),\n"
                + " FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID)"
                + ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);

    }

    public int getUserScores(String cbankid) throws SQLException {
        int output = 0;
        String sql = "Select UserScore from Users where UserID = \"" + cbankid + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output = Integer.valueOf(rs.getString("UserScore"));
        }

        query.close();
        return output;
    }
    
    public ObservableList<LeaderBoardScore> getHighScores() throws SQLException{
        ObservableList<LeaderBoardScore> data = FXCollections.observableArrayList();
        String sql = "select Username,UserScore from Users where isAdmin = \"" + "false" + "\"";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    data.add(new LeaderBoardScore(rs.getString("Username"), rs.getInt("UserScore")));
                }
                return data;
    }
    
    public int getCompQuizNo(int id) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM comp_" + id;
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public void addCompScore(int id, int quizNo, int score) throws SQLException {
        String sql = "INSERT INTO comp_" + id + " (quizNo, Score) VALUES(?,?)";
        query = conn.prepareStatement(sql);

        query.setInt(1, quizNo);
        query.setInt(2, score);

        query.executeUpdate();
        query.close();
    }

    public void updateTotalCompScore(int id, int score) throws SQLException {

        int currentTotal;
        SQLHandler sql = new SQLHandler();
        currentTotal = sql.getUserScores(String.valueOf(id));

        String sqlstring = "UPDATE Users SET 'UserScore' = ? WHERE UserID = \"" + id + "\"";

        query = conn.prepareStatement(sqlstring);

        query.setInt(1, currentTotal + score);

        query.executeUpdate();
        query.close();
    }

    public void addAnsweredQuestions(int id, int questID, int catID) throws SQLException {
        String sql = "INSERT INTO casual_" + id + " (QuestionID, CategoryID) VALUES(?,?)";
        query = conn.prepareStatement(sql);

        query.setInt(1, questID);
        query.setInt(2, catID);

        query.executeUpdate();
        query.close();
    }

    public void deleteAnQuestions(int catID, int id) throws SQLException {
        String sql = "DELETE from casual_" + id + " WHERE CategoryID='" + catID + "'";
        query = conn.prepareStatement(sql);

        query.executeUpdate();
        query.close();
    }
    
    public void deleteAccount(int userId) throws SQLException{
        String sql1 = "DROP TABLE comp_" + userId + "";
        String sql2 = "DROP TABLE casual_" + userId + "";
        String sql3 = " DELETE FROM Users WHERE UserID='"+userId+"'";
       Statement stmt = conn.createStatement();
        stmt.execute(sql1);
        stmt.execute(sql2);
        stmt.execute(sql3);
        
    }
    public void editUsername(int UserId, String username) throws SQLException {

        String sql = "UPDATE Users SET Username = ?  WHERE UserID = \"" + UserId + "\"";

        query = conn.prepareStatement(sql);

        query.setString(1, username);
        query.executeUpdate();
        query.close();
    }

    public void editPassword(int userID, String password) throws SQLException {
        String sql = "UPDATE Users SET Password = ?  WHERE UserID = \"" + userID + "\"";

        query = conn.prepareStatement(sql);

        query.setString(1, password);
        query.executeUpdate();
        query.close();

    }
    public ObservableList<HighScore> getPreviousResult(User currentUser) throws SQLException{
        ObservableList<HighScore> data = FXCollections.observableArrayList();
        String sql = "select quizNo,score from comp_" + currentUser.getUserID() + "";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    data.add(new HighScore(rs.getInt("quizNo"), rs.getString("score")));
                }
                return data;
    }
}
