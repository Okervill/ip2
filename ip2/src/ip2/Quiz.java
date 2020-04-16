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
 * @author stani
 */
public class Quiz {
    private static SQLHandler sql = new SQLHandler();
    
    public void endCompQuiz(User currentUser, int score) throws SQLException{
         currentUser.updateTotalScore(currentUser, score);
    }
    
    public boolean endCasualQuiz (String categorySelected, User currentUser, ArrayList<Question> answQuestions) throws SQLException{
        int catID = Category.fetchCatInfo(categorySelected);
        for (Question q : answQuestions) {
            sql.addAnsweredQuestions(currentUser.getUserID(), q.getQuestionId(), q.getCategoryId());
        }
            ArrayList<Question> allquest = sql.getQnAFromCategory(catID, currentUser.getUserID());
        if (allquest.size() < 1)  {
        currentUser.resetCasual(categorySelected,currentUser);
        return true;
    }
        else {
            return false;
        }
        
}

    public ArrayList<Question> getQuestions() throws SQLException {

        ArrayList<Question> allq = sql.getAllQandA();
        ArrayList<Question> questions = new ArrayList<>();
        if (allq.size() < 10) {
            System.out.println("Less than 10 questions found");
            return null;
        }

        for (int i = 0; i < 11; i++) {
            int x = Quiz.getRandom(allq.size());
            if (questions.contains(allq.get(x))) {
                i = i - 1;
            } else {
                questions.add(allq.get(x));
            }
        }

        return questions;
    }
    
     public ArrayList<Question> getQuestions(String categorySelected, User currentUser) throws SQLException {
       int catID = Category.fetchCatInfo(categorySelected);  
       ArrayList<Question> allq = sql.getQnAFromCategory(catID, currentUser.getUserID());
       ArrayList<Question> questions = new ArrayList<>();
        if (allq.size() < 1) {

            System.out.println("No questions found");
            return null;
        }

        for (int i = 0; i < allq.size(); i++) {
            int x = Quiz.getRandom(allq.size());
            if (questions.contains(allq.get(x))) {
                i = i - 1;

            } else {
                questions.add(allq.get(x));
            }
        }

        return questions;
    }
     
     public ArrayList nextQuestion(){
         ArrayList num = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int x = Quiz.getRandom(4);
                if (num.contains(x)) {
                    i = i - 1;
                } else {
                    num.add(x);
                }
            }
            return num;
    }
     private static int getRandom(int max) {
        int rnd = (int) (Math.random() * max);
        return rnd;
    }
}
