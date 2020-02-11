/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
/**
 *
 * @author Andy
 */
public class Shaker {
    private TranslateTransition translateTransition;
    
    public Shaker(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setByX(-10f);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);
        
    }
    
    public void shake() {
        translateTransition.playFromStart();
    }
    
}
