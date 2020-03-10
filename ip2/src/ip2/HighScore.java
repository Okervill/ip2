/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import SQL.SQLHandler;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class HighScore {
        private  String competitiveBankId;
        private  String quiz;
        
        public HighScore(String quiz) throws SQLException{
        
        SQLHandler sql = new SQLHandler();
        int catcount = sql.getAllHighscore().size();
        
        
        this.competitiveBankId = String.valueOf(catcount++);
        this.quiz=quiz;
        }
        
        public HighScore(String competitiveBankId, String quiz){
        this.competitiveBankId=competitiveBankId;
        this.quiz=quiz;
        }
        
       public String getCompetitiveBankId(){
         return this.competitiveBankId;
     }
       
     public String getQuiz(){
         return this.quiz;
     }
     
     
   
     
}
