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
    private final int CompetitiveBankID;
    private final int CasualBankID;
    private final int CategoriesAnsweredID;
    private final String firstname;
    private final String surname;
    private final String username;
    private final String password;
    private final String admin;
    private String userscore;

    public User(String first, String sur, String user, String pass, String isAdmin, String usrscore) throws SQLException {

        SQLHandler sql = new SQLHandler();
        int usercount = sql.getAllUsers().size();

        userid = Integer.valueOf(usercount++);
        CompetitiveBankID = userid;
        CasualBankID = userid;
        CategoriesAnsweredID = userid;
        firstname = first;
        surname = sur;
        username = user;
        password = pass;
        admin = isAdmin;
        userscore = usrscore;
    }

    public User(String user) throws SQLException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> userInfo = sql.searchUsersTable(user);

        userid = parseInt(userInfo.get(0));
        CompetitiveBankID = parseInt(userInfo.get(1));
        CasualBankID = parseInt(userInfo.get(2));
        CategoriesAnsweredID = parseInt(userInfo.get(3));
        firstname = userInfo.get(4);
        surname = userInfo.get(5);
        username = userInfo.get(6);
        password = userInfo.get(7);
        admin = userInfo.get(8);
        userscore = userInfo.get(9);
    }

    public void createUser(User user) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.createUser(user.getUserID(), user.getCompetitiveBankID(), user.getCasualBankID(), user.getCategoriesAnsweredID(), user.getFirstname(), user.getSurname(), user.getUsername(), user.getPassword(), user.getUserType(), user.getUserScore());
    }

    public int getUserID() {
        return this.userid;
    }

    public int getCompetitiveBankID() {
        return this.CompetitiveBankID;
    }

    public int getCasualBankID() {
        return this.CasualBankID;
    }

    public int getCategoriesAnsweredID() {
        return this.CategoriesAnsweredID;
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
}
