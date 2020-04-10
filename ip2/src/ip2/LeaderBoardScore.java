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
    private  String score;
        
        public LeaderBoardScore(String name,String score) throws SQLException{
            this.name=name;
        this.score=score;
        }
       
       public String getName(){
         return this.name;
     }
       
        
       public String getScore(){
         return this.score;
     }
       
 
     
}
