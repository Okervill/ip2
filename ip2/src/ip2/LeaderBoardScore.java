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
public class LeaderBoardScore {
     private  String name;
    private  int score;
        
        public LeaderBoardScore(String name,int score) throws SQLException{
            this.name=name;
        this.score=score;
        }
       
       public String getName(){
         return this.name;
     }
       
        
       public int getScore(){
         return this.score;
     }
       
 
     
}
