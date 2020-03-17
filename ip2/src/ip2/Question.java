/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author erino
 */
public class Question {

    private final String QuestionID;
    private final int CategoryID;
    private final String question;
    private final String answer;
    private final String wrongAns1;
    private final String wrongAns2;
    private final String wrongAns3;
    private String id = "0";

    public Question(int catID, String quest, String correct, String wrong1, String wrong2, String wrong3) throws SQLException {
        Stack stack = new Stack();
        SQLHandler sql = new SQLHandler();
        stack = sql.getAllQuestionIDs();

        int tempid = Integer.parseInt((String) stack.pop());

        tempid++;

        id = Integer.toString(tempid);

        this.QuestionID = id;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;
    }

    public Question(String questionid, int catID, String quest, String correct, String wrong1, String wrong2, String wrong3) throws SQLException {

        this.QuestionID = id;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;

    }

    public Question(String quest) throws SQLException {
        SQLHandler sql = new SQLHandler();

        List questioninfo = sql.searchQuestionTable(quest);

        QuestionID = (String) questioninfo.get(0);
        CategoryID = (int) questioninfo.get(1);
        question = (String) questioninfo.get(2);
        answer = (String) questioninfo.get(3);
        wrongAns1 = (String) questioninfo.get(4);
        wrongAns2 = (String) questioninfo.get(5);
        wrongAns3 = (String) questioninfo.get(6);

    }

    public void createQuestion(Question question) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.createQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
    }

    public void deleteQuestion(Question question) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.deleteQuestion(question.getUserQuestion());
    }

    public void editQuestion(Question question) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.editQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
    }

    public String getQuestionId() {
        return this.QuestionID;
    }

    public int getCategoryId() {
        return this.CategoryID;
    }

    public String getUserQuestion() {
        return this.question;
    }

    public String getCorrectAnswer() {
        return this.answer;
    }

    public String getWrongAnswer1() {
        return this.wrongAns1;
    }

    public String getWrongAnswer2() {
        return this.wrongAns2;
    }

    public String getWrongAnswer3() {
        return this.wrongAns3;
    }

}
