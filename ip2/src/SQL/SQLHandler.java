/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import ip2.Category;
import ip2.HighScore;
import ip2.Question;
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
    // ADD NEW DATA TO LOGIN TABLE //
    //-----------------------------//
    public void createUser(int userid, int CompetitiveBankID, int CasualBankID, int CategoriesAnsweredID, String firstname, String surname, String username, String password, String admin, String userscore) throws SQLException {

        String sql = "INSERT INTO Users (UserID, CompetitiveBankID, CasualBankID, CategoriesAnsweredID, FirstName, Surname, Username, Password, isAdmin, UserScore) VALUES(?,?,?,?,?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

        query.setInt(1, userid);
        query.setInt(2, CompetitiveBankID);
        query.setInt(3, CasualBankID);
        query.setInt(4, CategoriesAnsweredID);
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
    public void createQuestion(int QuestionId, int CategoryId, String question, String answer, String wrongAns1, String wrongAns2, String wrongAns3) throws SQLException {

        String sql = "INSERT INTO Questions (QuestionId, CategoryId, Question, Answer, wrongAns1, wrongAns2, wrongAns3) VALUES(?,?,?,?,?,?,?)";
        query = conn.prepareStatement(sql);

        query.setInt(1, QuestionId);
        query.setInt(2, CategoryId);
        query.setString(3, question);
        query.setString(4, answer);
        query.setString(5, wrongAns1);
        query.setString(6, wrongAns2);
        query.setString(7, wrongAns3);

        query.executeUpdate();
        query.close();
    }

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

    public Stack getAllQuestionIDs() throws SQLException {

        Stack stack = new Stack();
        String sql = "SELECT QuestionID FROM Questions";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            stack.add(rs.getInt("QuestionID"));
        }

        query.close();
        return stack;
    }

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
        String sql = "SELECT * FROM Questions WHERE CategoryID='" + categoryID + "'";

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

    public void createCategory(int categoryId, String categoryName) throws SQLException {
        String sql = "INSERT INTO Categories (CategoryID, CategoryName) VALUES(?,?)";
        query = conn.prepareStatement(sql);
        query.setInt(1, categoryId);
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

    public Stack getAllCategoryIDs() throws SQLException {

        Stack stack = new Stack();
        String sql = "SELECT CategoryID FROM Categories";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            stack.add(rs.getString("CategoryID"));
        }

        query.close();
        return stack;
    }

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

    public void deleteQuestion(String question) throws SQLException {
        String sql = " DELETE FROM Questions WHERE question=?";
        query = conn.prepareStatement(sql);
        query.setString(1, question);
        query.executeUpdate();
        query.close();
    }

    //------------------------------------//
    // GET ALL USER HIGHSCORE FROM USER TABLE //
    //------------------------------------//
    public ArrayList getAllHighscore() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "Select Quiz 1 from CompetitiveBank";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            //output.add(new HighScore(rs.getString("CompetitiveBankId"),rs.getString("Quiz 1")));
            output.add(rs.getString("CompetitiveBankId"));
            output.add(rs.getString("Quiz 1"));
        }

        query.close();
        return output;

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
        String sql = "Select * from CompetitiveBank where CompetitiveBankID = \"" + cbankid + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            output = Integer.valueOf(rs.getString("Quiz 1"));
        }

        query.close();
        return output;
    }

    public int getCompQuizNo(int id) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM comp_" + id;
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            count = rs.getInt(1);
        }
        if (count == 0) {
            createCompQuiz(id);
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

    public void createCompQuiz(int id) throws SQLException {
        String sql = "INSERT INTO CompetitiveBank (CompetitiveBankID, 'Quiz 1') VALUES(?,?)";
        query = conn.prepareStatement(sql);

        query.setString(1, String.valueOf(id));
        query.setString(2, "0");

        query.executeUpdate();
        query.close();
    }

    public void updateTotalCompScore(int id, int score) throws SQLException {

        int currentTotal;
        SQLHandler sql = new SQLHandler();
        currentTotal = sql.getUserScores(String.valueOf(id));

        String sqlstring = "UPDATE CompetitiveBank SET 'Quiz 1' = ? WHERE CompetitiveBankID = \"" + id + "\"";

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
    
}
