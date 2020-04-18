/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Patrick
 */
public class User {

    private final int userid;
    private final String firstname;
    private final String surname;
    private String username;
    private String password;
    private final String admin;
    private String userscore;
    private final SQLHandler sql = new SQLHandler();

    public User(String first, String sur, String user, String pass, String isAdmin, String usrscore) throws SQLException {
        userid=0;
        firstname = first;
        surname = sur;
        username = user;
        password = pass;
        admin = isAdmin;
        userscore = usrscore;
    }

   
    
    public User(String user) throws SQLException {
        ArrayList<String> userInfo = sql.searchUsersTable(user);
        userid = parseInt(userInfo.get(0));
        firstname = userInfo.get(1);
        surname = userInfo.get(2);
        username = userInfo.get(3);
        password = userInfo.get(4);
        admin = userInfo.get(5);
        userscore = userInfo.get(6);
    }

    public void createUser(User user) throws SQLException {
        
        sql.createUser(user.getFirstname(), user.getSurname(), user.getUsername(), user.getPassword(), user.getUserType(), user.getUserScore());
    }

    public int getUserID() {
        return this.userid;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.admin;
    }

    public String getUserScore() {
        return this.userscore;
    }
    
    public void setUserScore(int score){
        this.userscore = String.valueOf(score);
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public void editUsername(User user) throws SQLException {
        sql.editUsername(user.getUserID(), user.getUsername());
    }
    
    public void editPassword(User user) throws SQLException {
        sql.editPassword(user.getUserID(), user.getPassword());
    }
    
    public static boolean match(String val){
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher match = pattern.matcher(val);
        boolean valUser = match.find();
        return valUser;

    }

    public void resetCasual(String categorySelected, User currentUser) throws SQLException {
         int catID = Category.fetchCatInfo(categorySelected);
         sql.deleteAnQuestions(catID, currentUser.getUserID());
    }
   
    public void updateTotalScore(User currentUser, int score) throws SQLException{
        int quizNo = sql.getCompQuizNo(currentUser.getUserID());
        sql.addCompScore(currentUser.getUserID(), ++quizNo, score);
        sql.updateTotalCompScore(currentUser.getUserID(), score);
        currentUser.setUserScore(Integer.valueOf(currentUser.getUserScore()) + score);
    }
}
