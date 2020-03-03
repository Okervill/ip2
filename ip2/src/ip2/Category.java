package ip2;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stani
 */
public class Category {
    private final String categoryId;
    private final String categoryName;
    
    public Category(String categoryId, String categoryName) throws SQLException {

        SQLHandler sql = new SQLHandler();
        int catcount = sql.getAllCategories().size();

        this.categoryId = String.valueOf(catcount++);
        this.categoryName=categoryName;
    }
    
     public Category(String category) throws SQLException {
        SQLHandler sql = new SQLHandler();
        ArrayList<String> categoryInfo = sql.searchUsersTable(category);

        categoryId = categoryInfo.get(0);
        categoryName=categoryInfo.get(1);
       
    }
    
     public String getCategoryId(){
         return this.categoryId;
     }
     
     public String getCategoryName(){
         return this.categoryName;
     }
     
     public void createCategory(Category category) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.createCategory(category.getCategoryId(), category.getCategoryName());
    }
}
