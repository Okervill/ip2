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

    public User(String first, String sur, String user, String pass, String isAdmin, String usrscore) throws SQLException {

        SQLHandler sql = new SQLHandler();
        int usercount = sql.getAllUsers().size();

        userid = Integer.valueOf(++usercount);
        firstname = first;
        surname = sur;
        username = user;
        password = pass;
        admin = isAdmin;
        userscore = usrscore;
    }

    public User(int userid,String first,String sur, String user, String pass, String isAdmin, String usrscore){
        this.userid=userid;
        this.firstname=first;
        this.surname=sur;
        this.username=user;
        this.password=pass;
        this.admin=isAdmin;
        this.userscore=usrscore;        
    }
    
    public User(String user) throws SQLException {
        SQLHandler sql = new SQLHandler();
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
        SQLHandler sql = new SQLHandler();
        sql.createUser(user.getUserID(), user.getFirstname(), user.getSurname(), user.getUsername(), user.getPassword(), user.getUserType(), user.getUserScore());
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
        SQLHandler sql = new SQLHandler();
        sql.editUsername(user.getUserID(), user.getUsername());
    }
    public void editPassword(User user) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.editPassword(user.getUserID(), user.getPassword());
    }
}
