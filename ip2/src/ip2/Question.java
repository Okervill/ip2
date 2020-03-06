/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author erino
 */
public class Question {

    private final String QuestionID;
    private final String CategoryID;
    private final String question;
    private final String answer;
    private final String wrongAns1;
    private final String wrongAns2;
    private final String wrongAns3;

    public Question(String catID, String quest, String correct, String wrong1, String wrong2, String wrong3) throws SQLException {

        SQLHandler sql = new SQLHandler();
        int questionCount = sql.getAllQuestions().size();

        this.QuestionID = String.valueOf(questionCount++);
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;

    }

    public Question(String questionid, String catID, String quest, String correct, String wrong1, String wrong2, String wrong3) throws SQLException {

        this.QuestionID = questionid;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;

    }

    public Question(String quest) throws SQLException {
        SQLHandler sql = new SQLHandler();

        ArrayList<String> questioninfo = sql.searchQuestionTable(quest);

        QuestionID = questioninfo.get(0);
        CategoryID = questioninfo.get(1);
        question = questioninfo.get(2);
        answer = questioninfo.get(3);
        wrongAns1 = questioninfo.get(4);
        wrongAns2 = questioninfo.get(5);
        wrongAns3 = questioninfo.get(6);

    }

    public void createQuestion(Question question) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.createQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
    }

    public void deleteQuestion(Question question) throws SQLException {
        SQLHandler sql = new SQLHandler();
        sql.deleteQuestion(question.getUserQuestion());
    }

    public String getQuestionId() {
        return this.QuestionID;
    }

    public String getCategoryId() {
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
