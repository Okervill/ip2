package ip2;

import SQL.SQLHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;

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

    private final int categoryId;
    private final String categoryName;
    private static final SQLHandler sql = new SQLHandler();

    public Category(String categoryName) throws SQLException {
        this.categoryId = 0;
        this.categoryName = categoryName;
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;

    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void createCategory(Category category) throws SQLException {
        sql.createCategory(category.getCategoryId(), category.getCategoryName());
    }

    public void editCategory(Category category) throws SQLException {
        sql.editCategory(category.getCategoryId(), category.getCategoryName());
    }

    /**
     *
     * @param category
     * @throws SQLException
     */
    public static int fetchCatInfo(String tempcat) throws SQLException {
        List categoryInfo = sql.searchCategoriesTable(tempcat);

        int tempcategoryId = (int) categoryInfo.get(0);

        return tempcategoryId;

    }
    public static String fetchCatName(int tempcat) throws SQLException {
        ArrayList<String> categoryInfo = sql.searchIDCategoriesTable(tempcat);

        String categoryName = categoryInfo.get(0);

        return categoryName;

    }
    public void deleteCategory(Category category) throws SQLException {
        sql.deleteCategory(category.getCategoryId());
    }
    
    public static Category search(String name) throws SQLException, IOException {
        List categoryInfo = sql.searchCategoriesTable(name);
        int categoryId = (int) categoryInfo.get(0);
        String categoryName = (String) categoryInfo.get(1);
        Category currentCategory = new Category(categoryId, categoryName);
        return currentCategory;

    }
    public static boolean match(String name){
         Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher match = pattern.matcher(name);
        boolean val = match.find();
        return val;
    }

}
