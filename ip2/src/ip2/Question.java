/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author erino
 */
public class Question {

    private final int QuestionID;
    private final int CategoryID;
    private final String question;
    private final String answer;
    private final String wrongAns1;
    private final String wrongAns2;
    private final String wrongAns3;
    private final static SQLHandler sql = new SQLHandler();

    
    public Question(int catID, String quest, String correct, String wrong1, String wrong2, String wrong3) throws SQLException {
        this.QuestionID = 0;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;
    }

    public Question(int tempid, int catID, String quest, String correct, String wrong1, String wrong2, String wrong3) {

        this.QuestionID = tempid;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;

    }

    public Question(String quest) throws SQLException {
        List questioninfo = sql.searchQuestionTable(quest);
        QuestionID = (int) questioninfo.get(0);
        CategoryID = (int) questioninfo.get(1);
        question = (String) questioninfo.get(2);
        answer = (String) questioninfo.get(3);
        wrongAns1 = (String) questioninfo.get(4);
        wrongAns2 = (String) questioninfo.get(5);
        wrongAns3 = (String) questioninfo.get(6);

    }

    public void createQuestion(Question question) throws SQLException {
        sql.createQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
    }

    public void deleteQuestion(Question question) throws SQLException {
        sql.deleteQuestion(question.getUserQuestion());
    }

    public void editQuestion(Question question) throws SQLException {
        sql.editQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
    }

    public int getQuestionId() {
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
    public static Question search(String userquest) throws SQLException, IOException {
        List questionInfo = sql.searchQuestionTable(userquest);
        int QuestionID = (int) questionInfo.get(0);
        int CategoryID = (int) questionInfo.get(1);
        String quest = (String) questionInfo.get(2);
        String answer = (String) questionInfo.get(3);
        String wrongAns1 = (String) questionInfo.get(4);
        String wrongAns2 = (String) questionInfo.get(5);
        String wrongAns3 = (String) questionInfo.get(6);

        Question currentQuestion = new Question(QuestionID, CategoryID, quest, answer, wrongAns1, wrongAns2, wrongAns3);

        return currentQuestion;
    }
    
    public String[] getAnswers(Question q){
        String[] answers = {q.getCorrectAnswer(), q.getWrongAnswer1(), q.getWrongAnswer2(), q.getWrongAnswer3()};
        return answers;
    }
    
  
}
