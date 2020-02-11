/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Patrick
 */
public class User {
    
    private final String firstname;
    private final String surname;
    private final String username;
    private final String password;
    private final String userid;
    private final String admin;
    
    public User(String first, String sur, String user, String pass, String isAdmin) throws SQLException{
        
        SQLHandler sql = new SQLHandler();
        int usercount = sql.getAllUsers().size();
        
        userid = String.valueOf(usercount++);
        firstname = first;
        surname = sur;
        username = user;
        password = pass;
        admin = isAdmin;
    }
    
    public User(String user) throws SQLException{
        SQLHandler sql = new SQLHandler();
        ArrayList<String> userInfo = sql.searchUsersTable(user);
        
        userid = userInfo.get(0);
        firstname = userInfo.get(1);
        surname = userInfo.get(2);
        username = userInfo.get(3);
        password = userInfo.get(4);
        admin = userInfo.get(5);
    }
    
    public void createUser(User user) throws SQLException{
        SQLHandler sql = new SQLHandler();
        sql.createUser(user.getUserID(), user.getFirstname(), user.getSurname(), user.getUsername(), user.getPassword(), user.getUserType());
    }
    
    public String getFirstname(){
        return this.firstname;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUserID(){
        return this.userid;
    }
    public String getUserType(){
        return this.admin;
    }
    
}
