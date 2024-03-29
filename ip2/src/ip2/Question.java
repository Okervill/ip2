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

    


    public Question(int tempid, int catID, String quest, String correct, String wrong1, String wrong2, String wrong3) {

        this.QuestionID = tempid;
        this.CategoryID = catID;
        this.question = quest;
        this.answer = correct;
        this.wrongAns1 = wrong1;
        this.wrongAns2 = wrong2;
        this.wrongAns3 = wrong3;

    }

   

    public static void createQuestion(int categoryId,String question,String answer, String wrong1, String wrong2, String wrong3) throws SQLException {
        sql.createQuestion(categoryId, question, answer, wrong1, wrong2, wrong3);
    }

    public void deleteQuestion(Question question) throws SQLException {
        sql.deleteQuestion(question.getQuestionId());
    }

    public void editQuestion(Question question) throws SQLException {
        sql.editQuestion(question.getQuestionId(), question.getCategoryId(), question.getUserQuestion(), 
                         question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2(), question.getWrongAnswer3());
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
