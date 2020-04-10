/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import java.sql.SQLException;

/**
 *
 * @author user
 */
public class HighScore {
        private  int quizNo;
        private  String score;
        
        public HighScore(int quizNo,String score) throws SQLException{
            this.quizNo=quizNo;
            this.score=score;
        }

        
       public int getQuizNo(){
         return this.quizNo;
     }
       
     public String getScore(){
         return this.score;
     }
     
     
   
     
}
